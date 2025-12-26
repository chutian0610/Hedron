package io.github.chutian0610.hedron.core.parser;

import org.apache.calcite.sql.validate.SqlConformanceEnum;
import org.apache.calcite.sql.validate.SqlDelegatingConformance;

/**
 * StarRocks 自定义 SQL 合规性配置
 * StarRocks 兼容 MySQL 但有一些自己的语法扩展
 */
public class StarRocksConformance extends SqlDelegatingConformance {

    public static final StarRocksConformance INSTANCE = new StarRocksConformance();

    protected StarRocksConformance() {
        super(SqlConformanceEnum.MYSQL_5);
    }

    @Override
    public boolean isMinusAllowed() {
        return true;  // 允许 MINUS 操作符
    }

    @Override
    public boolean isInsertSubsetColumnsAllowed() {
        return true;  // INSERT 可以只插入部分列
    }
}
