package io.github.chutian0610.hedron.core.catalog;

import java.util.List;

import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;

import io.github.chutian0610.hedron.core.query.ColumnMeta;

/**
 * The converter to convert the column metas to the rel data type.
 */
@FunctionalInterface
public interface RelDataTypeConverter {
    RelDataType convert(RelDataTypeFactory typeFactory, List<ColumnMeta> columnMetas);
}
