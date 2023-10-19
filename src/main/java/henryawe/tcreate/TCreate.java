package henryawe.tcreate;

import com.mojang.logging.LogUtils;
import henryawe.tcreate.create.fans.processing.ProcessingTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("tcreate")
public class TCreate {
    public static final String MODID = "tcreate";

    private static final Logger LOGGER = LogUtils.getLogger();

    public TCreate() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        LOGGER.info("Registering Tinkers' Create");
        TCreateItems.register(bus);
        TCreateFluids.register(bus);
        TCreateRecipeTypes.clinit(bus);
        ProcessingTypes.clinit();

        MinecraftForge.EVENT_BUS.register(this);
    }
}
