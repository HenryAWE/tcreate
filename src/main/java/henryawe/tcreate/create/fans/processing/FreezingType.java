package henryawe.tcreate.create.fans.processing;

import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import henryawe.tcreate.TCreateRecipeTypes;
import henryawe.tcreate.create.fans.recipes.FreezingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class FreezingType implements FanProcessingType {
    static final FreezingRecipe.Wrapper WRAPPER = new FreezingRecipe.Wrapper();

    @Override
    public boolean isValidAt (Level level, BlockPos pos) {
        final var block = level.getBlockState(pos);
        return block.is(Blocks.SNOW);
    }

    @Override
    public int getPriority () {
        return 600;
    }

    @Override
    public boolean canProcess (ItemStack stack, Level level) {
        WRAPPER.setItem(0, stack);
        final var optional = TCreateRecipeTypes.FREEZING.filiter(WRAPPER, level);
        return optional.isPresent();
    }

    @Override
    @Nullable
    public List<ItemStack> process (ItemStack stack, Level level) {
        WRAPPER.setItem(0, stack);
        final var optional = TCreateRecipeTypes.FREEZING.filiter(WRAPPER, level);
        return optional.map(wrapperRecipe -> RecipeApplier.applyRecipeOn(stack, wrapperRecipe)).orElse(null);
    }

    @Override
    public void spawnProcessingParticles (Level level, Vec3 pos) {
        if (level.random.nextInt(8) != 0)
            return;
        level.addParticle(ParticleTypes.DRIPPING_WATER, pos.x + (level.random.nextFloat() - .5f) * .5f,
                pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
        level.addParticle(ParticleTypes.POOF, pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
    }

    @Override
    public void morphAirFlow (AirFlowParticleAccess particleAccess, Random random) {
        if (random.nextFloat() < 1 / 32f)
            particleAccess.spawnExtraParticle(ParticleTypes.DRIPPING_WATER, .125f);
        if (random.nextFloat() < 1 / 32f)
            particleAccess.spawnExtraParticle(ParticleTypes.POOF, .125f);
    }

    @Override
    public void affectEntity (Entity entity, Level level) {
        if (!entity.isAlive())
            return;
        if (entity instanceof LivingEntity le) {
            le.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
            le.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 20, 1));
            le.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 1));
        }
    }
}