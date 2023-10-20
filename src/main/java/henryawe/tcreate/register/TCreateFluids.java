package henryawe.tcreate.register;

import henryawe.tcreate.TCreate;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.ModelFluidAttributes;
import slimeknights.mantle.registration.object.FluidObject;

public class TCreateFluids {
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(TCreate.MODID);

    public static final FluidObject<ForgeFlowingFluid> MOLTEN_ANDESITE_ALLOY = FLUIDS.register("molten_andesite_alloy", hotBuilder().temperature(420), Material.LAVA, 10);

    private static FluidAttributes.Builder hotBuilder() {
        return ModelFluidAttributes.builder().density(2000).viscosity(10000).temperature(1000).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA);
    }

    public static void register(IEventBus bus) {
        FLUIDS.register(bus);
    }
}
