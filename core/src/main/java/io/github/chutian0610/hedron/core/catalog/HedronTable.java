package io.github.chutian0610.hedron.core.catalog;

import java.lang.reflect.Type;
import java.sql.JDBCType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.calcite.adapter.java.AbstractQueryableTable;
import org.apache.calcite.linq4j.QueryProvider;
import org.apache.calcite.linq4j.Queryable;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.plan.RelOptTable.ToRelContext;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rel.type.RelDataTypeImpl;
import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.rel.type.RelProtoDataType;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.TranslatableTable;
import org.apache.calcite.sql.type.SqlTypeFactoryImpl;
import org.apache.calcite.sql.type.SqlTypeName;

import com.google.common.base.Preconditions;

import io.github.chutian0610.hedron.core.plan.HedronTableScan;
import io.github.chutian0610.hedron.core.query.ColumnMeta;
import io.github.chutian0610.hedron.core.util.JDBCTypeUtil;
import lombok.Getter;

/**
 * The table in Hedron.
 */
public class HedronTable extends AbstractQueryableTable implements TranslatableTable, RelDataTypeConverter {

    private final HedronSchema hedronSchema;
    /**
     * The column metas of the table.
     */
    private final List<ColumnMeta> columnMetas;
    /**
     * The name of the table.
     */
    @Getter
    private final String name;

    public HedronTable(Type elementType, String name, List<ColumnMeta> columnMetas, HedronSchema hedronSchema) {
        // HedronTable actually is a jdbc table/view, so the element is Object[]
        super(Object[].class);
        this.columnMetas = columnMetas;
        this.name = Objects.requireNonNull(name, "name");
        this.hedronSchema = Objects.requireNonNull(hedronSchema, "hedronSchema");
        hedronSchema.addTable(this);
    }

    /* ======================== Table ======================== */
    @Override
    public <T> Queryable<T> asQueryable(QueryProvider queryProvider, SchemaPlus schema, String tableName) {
        // Because we only use it for syntactic analysis, we don't need to implement
        // this function
        throw new UnsupportedOperationException("Unimplemented method 'asQueryable'");
    }

    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        return convert(typeFactory, columnMetas);
    }

    @Override
    public RelNode toRel(ToRelContext context, RelOptTable relOptTable) {
        return new HedronTableScan(context.getCluster(), context.getTableHints(), relOptTable, this,
                hedronSchema.getConvention());
    }

    /* ======================== RelDataTypeConverter ======================== */
    @Override
    public RelDataType convert(RelDataTypeFactory typeFactory, List<ColumnMeta> columnMetas) {
        Preconditions.checkNotNull(typeFactory);
        Preconditions.checkArgument(columnMetas != null && !columnMetas.isEmpty(), "columnMetas must not be empty");
        final RelDataTypeFactory.Builder fieldInfo = typeFactory.builder();
        buildFieldInfo(columnMetas, fieldInfo);
        RelProtoDataType relProtoDataType = RelDataTypeImpl.proto(fieldInfo.build());
        return relProtoDataType.apply(typeFactory);
    }

    private void buildFieldInfo(List<ColumnMeta> columnMetas, RelDataTypeFactory.Builder fieldInfo) {
        final RelDataTypeFactory tmpTypeFactory = new SqlTypeFactoryImpl(RelDataTypeSystem.DEFAULT);
        columnMetas.stream().forEach(c -> {
            fieldInfo.add(c.getName(), convertColumn2DataType(tmpTypeFactory, c)).nullable(c.isNullable());
        });
    }

    private RelDataType convertColumn2DataType(RelDataTypeFactory typeFactory, ColumnMeta columnMeta) {
        Preconditions.checkNotNull(columnMeta);
        Optional<JDBCType> jdbcTypeOpt = JDBCTypeUtil.findJdbcTypeByTypeName(columnMeta.getType());
        Preconditions.checkArgument(jdbcTypeOpt.isPresent(),
                "columnType %s is not a Supported Column JDBC Type", columnMeta.getType());

        final SqlTypeName sqlTypeName = !columnMeta.isSigned()
                ? SqlTypeName.getNameForUnsignedJdbcType(jdbcTypeOpt.get().getVendorTypeNumber())
                : SqlTypeName.getNameForJdbcType(jdbcTypeOpt.get().getVendorTypeNumber());
        @SuppressWarnings("null") // here unboxing is safe
        int precision = columnMeta.getPrecision() != null ? columnMeta.getPrecision() : -1;
        @SuppressWarnings("null") // here unboxing is safe
        int scale = columnMeta.getScale() != null ? columnMeta.getScale() : -1;
        if (precision >= 0 && scale >= 0
                && sqlTypeName.allowsPrecScale(true, true)) {
            return typeFactory.createSqlType(sqlTypeName, precision, scale);
        } else if (precision >= 0 && sqlTypeName.allowsPrecNoScale()) {
            return typeFactory.createSqlType(sqlTypeName, precision);
        } else {
            Preconditions.checkArgument(sqlTypeName.allowsNoPrecNoScale());
            return typeFactory.createSqlType(sqlTypeName);
        }
    }

}
