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
    private String name;
    /**
     * The type String of the column.
     * 
     * @see java.sql.JDBCType
     */
    @Nonnull
    private String type;
    /**
     * Whether the column is signed.
     * 
     * @see java.sql.ResultSetMetaData#isSigned(int)
     */
    private boolean signed;
    /**
     * Whether the column is nullable.
     * 
     * @see java.sql.ResultSetMetaData#isNullable(int)
     */
    private boolean nullable;
    /**
     * The precision of the column.
     * 
     * @see java.sql.ResultSetMetaData#getPrecision(int)
     */
    @Nullable
    private Integer precision;
    /**
     * The scale of the column.
     * 
     * @see java.sql.ResultSetMetaData#getScale(int)
     */
    @Nullable
    private Integer scale;
}
