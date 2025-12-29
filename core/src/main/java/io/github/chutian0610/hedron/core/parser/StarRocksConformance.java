package io.github.chutian0610.hedron.core.parser;

import org.apache.calcite.sql.validate.SqlConformanceEnum;
import org.apache.calcite.sql.validate.SqlDelegatingConformance;

/**
 * The conformance of StarRocks SQL.
 * <p>
 * The conformance is used to validate the SQL statement against the StarRocks
 * SQL syntax rules.
 */
public class StarRocksConformance extends SqlDelegatingConformance {

    public static final StarRocksConformance INSTANCE = new StarRocksConformance();

    protected StarRocksConformance() {
        super(SqlConformanceEnum.MYSQL_5);
    }

    @Override
    public boolean isMinusAllowed() {
        return true;
    }

    @Override
    public boolean isInsertSubsetColumnsAllowed() {
        return true;
    }
}
