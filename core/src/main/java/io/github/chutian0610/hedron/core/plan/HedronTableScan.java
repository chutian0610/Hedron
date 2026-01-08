package io.github.chutian0610.hedron.core.plan;

import java.util.List;
import java.util.Objects;

import org.apache.calcite.adapter.jdbc.JdbcConvention;
import org.apache.calcite.linq4j.Nullness;
import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.TableScan;
import org.apache.calcite.rel.hint.RelHint;

import io.github.chutian0610.hedron.core.catalog.HedronTable;

public class HedronTableScan extends TableScan {
    public final HedronTable hedronTable;

    public HedronTableScan(RelOptCluster cluster, List<RelHint> hints, RelOptTable table, HedronTable hedronTable,
            JdbcConvention jdbcConvention) {
        super(cluster, cluster.traitSetOf(jdbcConvention), hints, table);
        this.hedronTable = Objects.requireNonNull(hedronTable, "hedronTable");
    }

    @Override
    public RelNode copy(RelTraitSet traitSet, List<RelNode> inputs) {
        assert inputs.isEmpty();
        return new HedronTableScan(
                getCluster(), getHints(), table, hedronTable,
                (JdbcConvention) Nullness.castNonNull(getConvention()));
    }

    @Override
    public RelNode withHints(List<RelHint> hintList) {
        Convention convention = Objects.requireNonNull(getConvention(), "getConvention()");
        return new HedronTableScan(getCluster(), hintList, getTable(), hedronTable,
                (JdbcConvention) convention);
    }
}
