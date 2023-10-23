package henryawe.tcreate.create.fans.recipes;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import henryawe.tcreate.register.TCreateRecipeTypes;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

public class SkySlimeRecipe extends ProcessingRecipe<SkySlimeRecipe.Wrapper> {

    public SkySlimeRecipe (ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(TCreateRecipeTypes.SKYSLIME_PROCESSING, params);
    }

    @Override
    protected int getMaxInputCount () {
        return 1;
    }

    @Override
    protected int getMaxOutputCount () {
        return 9;
    }

    @Override
    public boolean matches (@NotNull Wrapper inv, @NotNull Level worldIn) {
        if (inv.isEmpty()) {
            return false;
        }
        return ingredients.get(0)
                .test(inv.getItem(0));
    }

    public static class Wrapper extends RecipeWrapper {
        public Wrapper() {
            super(new ItemStackHandler(1));
        }
    }
}

