package henryawe.tcreate;

import henryawe.tcreate.create.fans.processing.FreezingType;
import henryawe.tcreate.create.fans.processing.ProcessingTypes;
import henryawe.tcreate.create.fans.recipes.FreezingRecipe;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.List;

import static henryawe.tcreate.TCreate.MODID;

public final class SharedSecrets {
    private SharedSecrets () {
    }

    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    private static final HashSet<Class<?>> PERMITTED_CLASSES = new HashSet<>(List.of(
            SharedSecrets.class,
            TCreate.class,
            TCreateFluids.class,
            TCreateItems.class,
            TCreateTabs.class,
            TCreateRecipeTypes.class,
            ProcessingTypes.class,
            FreezingType.class,
            FreezingRecipe.class
    ));

    public static ResourceLocation asResource (String name) {
        final var caller = STACK_WALKER.getCallerClass();
        if (!PERMITTED_CLASSES.contains(caller))
            throw new SecurityException("Caller " + caller + " is not permitted to access SharedSecrets.");
        return new ResourceLocation(MODID, name);
    }
}
