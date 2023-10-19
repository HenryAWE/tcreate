package henryawe.tcreate.create.fans.processing;

import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingTypeRegistry;
import henryawe.tcreate.SharedSecrets;

public class ProcessingTypes {
    public static final FreezingType FROZEN = register("frozen", new FreezingType());

    @SuppressWarnings("SameParameterValue")
    private static <T extends FanProcessingType> T register(String id, T type) {
        FanProcessingTypeRegistry.register(SharedSecrets.asResource(id), type);
        return type;
    }

    public static void clinit() {
    }
}
