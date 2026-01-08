package io.github.chutian0610.hedron.core.util;

import java.sql.JDBCType;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;

public class JDBCTypeUtil {
    public static final Map<String, JDBCType> UPPER_JDBC_TYPE_MAP = new ImmutableMap.Builder<String, JDBCType>()
            .put("TINYINT", JDBCType.TINYINT)
            .put("SMALLINT", JDBCType.SMALLINT)
            .put("INT", JDBCType.INTEGER)
            .put("BIGINT", JDBCType.BIGINT)
            .put("DECIMAL", JDBCType.DECIMAL)
            .put("NUMERIC", JDBCType.NUMERIC)

            .put("REAL", JDBCType.REAL)
            .put("FLOAT", JDBCType.FLOAT)
            .put("DOUBLE", JDBCType.DOUBLE)

            .put("BIT", JDBCType.BIT)
            .put("BOOLEAN", JDBCType.BOOLEAN)

            .put("CHAR", JDBCType.CHAR)
            .put("VARCHAR", JDBCType.VARCHAR)

            .put("BINARY", JDBCType.BINARY)
            .put("VARBINARY", JDBCType.VARBINARY)

            .put("DATE", JDBCType.DATE)
            .put("TIME", JDBCType.TIME)
            .put("TIMESTAMP", JDBCType.TIMESTAMP)
            // currently not support complex type.
            // .put("NULL", JDBCType.NULL)
            // .put("STRUCT", JDBCType.STRUCT)
            // .put("ARRAY", JDBCType.ARRAY)
            .build();

    public static Optional<JDBCType> findJdbcTypeByTypeName(String jdbcTypeName) {
        if (jdbcTypeName == null) {
            return Optional.empty();
        }
        String upperJdbcTypeName = jdbcTypeName.toUpperCase(Locale.ROOT);
        return Optional.ofNullable(UPPER_JDBC_TYPE_MAP.get(upperJdbcTypeName));
    }
}
