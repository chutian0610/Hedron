package io.github.chutian0610.hedron.core.parser;

import org.apache.calcite.sql.validate.SqlConformanceEnum;
import org.apache.calcite.sql.validate.SqlDelegatingConformance;

public class GenericConformance extends SqlDelegatingConformance {
    public static final GenericConformance INSTANCE = new GenericConformance();
    protected GenericConformance() {
        super(SqlConformanceEnum.PRESTO);
    }
}