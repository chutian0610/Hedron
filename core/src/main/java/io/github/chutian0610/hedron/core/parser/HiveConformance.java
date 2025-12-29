package io.github.chutian0610.hedron.core.parser;

import org.apache.calcite.sql.validate.SqlAbstractConformance;

/**
 * The conformance of Hive SQL.
 * <p>
 * The conformance is used to validate the SQL statement against the Hive SQL
 * syntax rules.
 */
public class HiveConformance extends SqlAbstractConformance {
    public static final HiveConformance INSTANCE = new HiveConformance();

    private HiveConformance() {
    }

    @Override
    public boolean isLiberal() {
        return true;
    }

    @Override
    public boolean isGroupByAlias() {
        return true;
    }

    @Override
    public boolean isGroupByOrdinal() {
        return true;
    }

    @Override
    public boolean isHavingAlias() {
        return true;
    }

    @Override
    public boolean isSortByOrdinal() {
        return true;
    }

    @Override
    public boolean isSortByAlias() {
        return true;
    }

    @Override
    public boolean isFromRequired() {
        return false;
    }

    @Override
    public boolean isBangEqualAllowed() {
        return true;
    }

    @Override
    public boolean isMinusAllowed() {
        return true;
    }

    @Override
    public boolean isPercentRemainderAllowed() {
        return true;
    }

    @Override
    public boolean allowNiladicParentheses() {
        return true;
    }

    @Override
    public boolean allowExplicitRowValueConstructor() {
        return false;
    }

    @Override
    public boolean allowExtend() {
        return false;
    }

    @Override
    public boolean allowGeometry() {
        return false;
    }

    @Override
    public boolean allowPluralTimeUnits() {
        return true;
    }

    @Override
    public boolean allowQualifyingCommonColumn() {
        return true;
    }

    @Override
    public boolean allowAliasUnnestItems() {
        return true;
    }

    @Override
    public boolean isValueAllowed() {
        return true;
    }
}
