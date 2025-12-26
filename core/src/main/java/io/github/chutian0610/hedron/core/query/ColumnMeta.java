package io.github.chutian0610.hedron.core.query;

import lombok.Data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The metadata of a column.
 */
@Data
public class ColumnMeta {
    @Nonnull
    private String columnName;
    @Nonnull
    private String columnType;
    @Nullable
    private Integer precision;
    @Nullable
    private Integer scale;
}
