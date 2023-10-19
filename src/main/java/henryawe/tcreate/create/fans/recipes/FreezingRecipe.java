package henryawe.tcreate.create.fans.recipes;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import henryawe.tcreate.TCreateRecipeTypes;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

public class FreezingRecipe extends ProcessingRecipe<FreezingRecipe.Wrapper> {

    public FreezingRecipe (ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(TCreateRecipeTypes.FREEZING, params);
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

