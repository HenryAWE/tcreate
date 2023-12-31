package henryawe.tcreate;

import com.mojang.logging.LogUtils;
import henryawe.tcreate.create.fans.processing.ProcessingTypes;
import henryawe.tcreate.register.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
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

    public TCreate() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        LOGGER.info("Registering Tinkers' Create");
        TCreateItems.register(bus);
        TCreateFluids.register(bus);
        TCreateRecipeTypes.clinit(bus);
        ProcessingTypes.clinit();
        TCreateEffects.clinit(bus);
        TCreatePotatoCannonProjectileTypes.clinit();

        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("Finish Register TCreate.");
    }

    public static boolean getBoolean(@NotNull LivingEntity le, String tag) {
        return le.getPersistentData().getBoolean(tag);
    }

    public static void putBoolean(@NotNull LivingEntity le, String tag) {
        le.getPersistentData().getBoolean(tag);
    }
}
