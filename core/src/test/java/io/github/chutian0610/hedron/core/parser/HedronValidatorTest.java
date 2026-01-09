package io.github.chutian0610.hedron.core.parser;

import java.util.HashMap;
import java.util.Map;

import org.apache.calcite.adapter.jdbc.JdbcSchema;
import org.apache.calcite.jdbc.CalciteSchema;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.github.chutian0610.hedron.constant.EngineType;
import io.github.chutian0610.hedron.core.catalog.HedronSchema;

@TestInstance(Lifecycle.PER_CLASS)
public class HedronValidatorTest {
    protected CalciteSchema rootSchema;

    @BeforeAll
    public void setUp() {
        HedronSchema schema = HedronSchema.create(
                null,
                "schema",
                EngineType.MYSQL,
                "catalog",
                "schema");
    }
}
