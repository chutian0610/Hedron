package io.github.chutian0610.hedron.core.catalog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import org.apache.calcite.adapter.jdbc.JdbcConvention;
import org.apache.calcite.linq4j.tree.Expression;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.Schemas;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.apache.calcite.sql.SqlDialect;
import org.apache.calcite.sql.dialect.ClickHouseSqlDialect;
import org.apache.calcite.sql.dialect.HiveSqlDialect;
import org.apache.calcite.sql.dialect.MysqlSqlDialect;
import org.apache.calcite.sql.dialect.StarRocksSqlDialect;
import com.google.common.collect.ImmutableMap;

import io.github.chutian0610.hedron.constant.EngineType;
import lombok.Getter;

public class HedronSchema extends AbstractSchema {
    @Getter
    private final JdbcConvention convention;
    @Getter
    private final String catalog;
    @Getter
    private final String schema;
    @Getter
    private final SqlDialect dialect;

    @Nonnull
    private final Map<String, Table> tableMap = new ConcurrentHashMap<>();

    public HedronSchema(SqlDialect dialect, JdbcConvention convention, String catalog, String schema) {
        this.convention = convention;
        this.catalog = catalog;
        this.schema = schema;
        this.dialect = dialect;
    }

    public static HedronSchema create(
            SchemaPlus parentSchema,
            String name,
            EngineType engineType,
            String catalog,
            String schema) {
        final Expression expression = Schemas.subSchemaExpression(parentSchema, name, HedronSchema.class);
        final SqlDialect dialect = createDialect(engineType);
        final JdbcConvention convention = JdbcConvention.of(dialect, expression, name);
        return new HedronSchema(dialect, convention, catalog, schema);
    }

    public static final SqlDialect MYSQL_DIALECT = new MysqlSqlDialect(MysqlSqlDialect.DEFAULT_CONTEXT
            .withDatabaseMajorVersion(5)
            .withDataTypeSystem(MysqlSqlDialect.MYSQL_TYPE_SYSTEM));
    public static final SqlDialect HIVE_DIALECT = new HiveSqlDialect(HiveSqlDialect.DEFAULT_CONTEXT
            .withDatabaseMajorVersion(2)
            .withDatabaseMinorVersion(1));

    private static SqlDialect createDialect(EngineType engineType) {
        switch (engineType) {
            case STARROCKS:
                return StarRocksSqlDialect.DEFAULT;
            case CLICKHOUSE:
                return ClickHouseSqlDialect.DEFAULT;
            case HIVE:
                return HIVE_DIALECT;
            case MYSQL:
                return MYSQL_DIALECT;
            default:
                throw new IllegalArgumentException("UnSupported backend engine type: " + engineType);
        }
    }

    void addTable(HedronTable table) {
        tableMap.put(table.getName(), table);
    }

    @Override
    protected Map<String, Table> getTableMap() {
        return ImmutableMap.<String, Table>builder()
                .putAll(tableMap)
                .build();
    }
}
