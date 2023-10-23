package henryawe.tcreate;

import henryawe.tcreate.create.fans.processing.FreezingType;
import henryawe.tcreate.create.fans.processing.ProcessingTypes;
import henryawe.tcreate.create.fans.recipes.FreezingRecipe;
import henryawe.tcreate.effect.BaseEffect;
import henryawe.tcreate.effect.FreezingEffect;
import henryawe.tcreate.register.TCreateFluids;
import henryawe.tcreate.register.TCreateItems;
import henryawe.tcreate.register.TCreateRecipeTypes;
import henryawe.tcreate.register.TCreateTabs;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashSet;
import java.util.List;

import static henryawe.tcreate.TCreate.MODID;

/**
 * The caller sensitive Util class.
 *
 * @author KKoishi_
 */
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
            FreezingRecipe.class,
            BaseEffect.class,
            FreezingEffect.class
    ));

    public static ResourceLocation asResource (String name) {
        final var caller = STACK_WALKER.getCallerClass();
        if (!PERMITTED_CLASSES.contains(caller))
            throw new SecurityException("Caller " + caller + " is not permitted to access SharedSecrets.");
        return new ResourceLocation(MODID, name);
    }

    /**
     * Convert the color to int.
     * <p/>
     * result = r << 16 | g << 8 | b
     * @param color the color.
     * @return int represents the color.
     */
    public static int getColor (@NotNull Color color) {
        final var caller = STACK_WALKER.getCallerClass();
        if (!PERMITTED_CLASSES.contains(caller))
            throw new SecurityException("Caller " + caller + " is not permitted to access SharedSecrets.");
        return color.getRed() << 16 | color.getGreen() << 8 | color.getBlue();
    }
}
