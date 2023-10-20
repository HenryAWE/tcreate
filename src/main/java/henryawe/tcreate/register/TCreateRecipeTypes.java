package henryawe.tcreate.register;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import henryawe.tcreate.create.fans.recipes.FreezingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;

import static henryawe.tcreate.SharedSecrets.asResource;
import static henryawe.tcreate.TCreate.MODID;

public enum TCreateRecipeTypes implements IRecipeTypeInfo {
    FREEZING(FreezingRecipe::new);

    TCreateRecipeTypes (Supplier<RecipeSerializer<?>> serializerObject) {
        String name = name().toLowerCase(Locale.ROOT);
        id = asResource(name);
        this.serializerObject = Registers.SERIALIZER_REGISTER.register(name, serializerObject);
        typeObject = Registers.TYPE_REGISTER.register(name, () -> new RecipeType<>() {
            @Override
            public String toString () {
                return id.toString();
            }
        });
        this.type = typeObject;
    }

    TCreateRecipeTypes (ProcessingRecipeBuilder.ProcessingRecipeFactory<?> processingFactory) {
        this(() -> new ProcessingRecipeSerializer<>(processingFactory));
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    private final ResourceLocation id;
    private final RegistryObject<RecipeSerializer<?>> serializerObject;
    @Nullable
    private final RegistryObject<RecipeType<?>> typeObject;
    private final Supplier<RecipeType<?>> type;

    @Override
    public ResourceLocation getId () {
        return id;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends RecipeSerializer<?>> T getSerializer () {
        return (T) serializerObject.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends RecipeType<?>> T getType () {
        return (T) type.get();
    }

    public <C extends Container, T extends Recipe<C>> Optional<T> filiter (C inv, Level worldIn) {
        return worldIn.getRecipeManager()
                .getRecipeFor(getType(), inv, worldIn);
    }

    public static void clinit(IEventBus bus) {
        Registers.SERIALIZER_REGISTER.register(bus);
        Registers.TYPE_REGISTER.register(bus);
        // debug
        LOGGER.debug("");
    }

    static class Registers {
        private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER
                = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);

        private static final DeferredRegister<RecipeType<?>> TYPE_REGISTER
                = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, MODID);
    }
}
