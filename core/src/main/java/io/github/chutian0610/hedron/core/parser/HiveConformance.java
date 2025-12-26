package io.github.chutian0610.hedron.core.parser;

import org.apache.calcite.sql.validate.SqlAbstractConformance;

public class HiveConformance extends SqlAbstractConformance {
    public static final HiveConformance INSTANCE = new HiveConformance();

    private HiveConformance() {}

    @Override
    public boolean isLiberal() {
        return true; // Hive SQL 比较宽松
    }

    @Override
    public boolean isGroupByAlias() {
        return true; // Hive 允许 GROUP BY 别名: SELECT a AS b FROM t GROUP BY b
    }

    @Override
    public boolean isGroupByOrdinal() {
        return true; // Hive 允许 GROUP BY 序号: SELECT a, b FROM t GROUP BY 1, 2
    }

    @Override
    public boolean isHavingAlias() {
        return true; // Hive 允许 HAVING 中使用别名
    }

    @Override
    public boolean isSortByOrdinal() {
        return true; // Hive 允许 ORDER BY 序号
    }

    @Override
    public boolean isSortByAlias() {
        return true; // Hive 允许 ORDER BY 别名
    }

    @Override
    public boolean isFromRequired() {
        return false; // Hive 允许没有 FROM 的子句: SELECT 1, 'test'
    }

    @Override
    public boolean isBangEqualAllowed() {
        return true; // Hive 支持 !=
    }

    @Override
    public boolean isMinusAllowed() {
        return true; // Hive 支持负号
    }

    @Override
    public boolean isPercentRemainderAllowed() {
        return true; // Hive 支持 % 取模
    }

    @Override
    public boolean allowNiladicParentheses() {
        return true; // 允许无参函数带括号: current_date()
    }

    @Override
    public boolean allowExplicitRowValueConstructor() {
        return false; // Hive 不支持 ROW 构造函数
    }

    @Override
    public boolean allowExtend() {
        return false; // Hive 不支持 EXTEND
    }

    @Override
    public boolean allowGeometry() {
        return false; // Hive 不支持几何类型
    }

    @Override
    public boolean allowPluralTimeUnits() {
        return true; // 允许复数时间单位: INTERVAL 2 DAYS
    }

    @Override
    public boolean allowQualifyingCommonColumn() {
        return true; // 允许限定公共列
    }

    // Hive 的 UNION 语法
    @Override
    public boolean allowAliasUnnestItems() {
        return true; // 允许 UNNEST 项有别名
    }

    @Override
    public boolean isValueAllowed() {
        return true; // 允许 VALUES 子句
    }
}
