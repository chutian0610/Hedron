package io.github.chutian0610.hedron.core.parser;

import org.apache.calcite.sql.validate.SqlConformanceEnum;
import org.apache.calcite.sql.validate.SqlDelegatingConformance;

/**
 * Clickhouse 自定义 SQL 合规性配置
 * Clickhouse 兼容 MySQL 但有一些独特的语法特性
 */
public class ClickhouseConformance extends SqlDelegatingConformance {
    public static final ClickhouseConformance INSTANCE = new ClickhouseConformance();
    protected ClickhouseConformance() {
        super(SqlConformanceEnum.MYSQL_5);
    }

}