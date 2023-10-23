package henryawe.tcreate.register;

import henryawe.tcreate.effect.ColdEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static henryawe.tcreate.TCreate.MODID;

public final class TCreateEffects {
    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);

    public static final RegistryObject<ColdEffect> COLD = MOB_EFFECTS.register("cold", ColdEffect::new);

    public static void clinit (IEventBus bus) {
        MOB_EFFECTS.register(bus);
    }
}
