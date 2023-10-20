package henryawe.tcreate;

import com.mojang.logging.LogUtils;
import henryawe.tcreate.create.fans.processing.ProcessingTypes;
import henryawe.tcreate.register.TCreateFluids;
import henryawe.tcreate.register.TCreateItems;
import henryawe.tcreate.register.TCreateRecipeTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * The entry point class of the mod.
 *
 * @author HenryAWE
 * @author KKoishi_
 */
@Mod("tcreate")
public final class TCreate {
    /**
     * The mod id.
     */
    public static final String MODID = "tcreate";

    /**
     * Logger of this class.
     */
    private static final Logger LOGGER = LogUtils.getLogger();

    public TCreate () {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        LOGGER.info("Registering Tinkers' Create");
        TCreateItems.register(bus);
        TCreateFluids.register(bus);
        TCreateRecipeTypes.clinit(bus);
        ProcessingTypes.clinit();

        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("Finish Register TCreate.");
    }
}
