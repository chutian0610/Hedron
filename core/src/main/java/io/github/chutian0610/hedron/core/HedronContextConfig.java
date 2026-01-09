package io.github.chutian0610.hedron.core;

import org.apache.calcite.config.CalciteConnectionConfig;
import org.apache.calcite.jdbc.JavaTypeFactoryImpl;
import org.apache.calcite.rel.type.RelDataTypeFactory;

import io.github.chutian0610.hedron.constant.EngineType;
import lombok.Builder;
import lombok.Data;

/**
 * The initial configuration of the Hedron context.
 */
@Data
@Builder
public class HedronContextConfig {
    @Builder.Default
    private boolean addMetadataSchema = false;
    @Builder.Default
    private EngineType engineType = EngineType.GENERIC;
    @Builder.Default
    private RelDataTypeFactory typeFactory = new JavaTypeFactoryImpl();
}
