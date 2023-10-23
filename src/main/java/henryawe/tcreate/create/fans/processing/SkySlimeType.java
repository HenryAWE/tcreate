package henryawe.tcreate.create.fans.processing;

import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import henryawe.tcreate.TCreate;
import henryawe.tcreate.TCreateTasks;
import henryawe.tcreate.pattern.PatternMatcher;
import henryawe.tcreate.pattern.TCreatePattern;
import henryawe.tcreate.register.TCreateRecipeTypes;
import henryawe.tcreate.create.fans.recipes.SkySlimeRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static net.minecraft.world.entity.EntityType.STRAY;
import static slimeknights.tconstruct.fluids.TinkerFluids.skySlime;

public class SkySlimeType implements FanProcessingType, PatternMatcher<FluidState> {
    static final SkySlimeRecipe.Wrapper WRAPPER = new SkySlimeRecipe.Wrapper();

    public static final String CONVERT_FROM_SKYSLIME_TAG = "TCreate_from_skyslime_type";

    private final Collection<TCreatePattern<FluidState>> patterns = new ArrayDeque<>();

    public SkySlimeType () {
        // TODO: add patterns to the function defaultPattern
        register(SkySlimeType::defaultPattern);
    }

    @Override
    public boolean isValidAt (Level level, BlockPos pos) {
        final var fluid = level.getFluidState(pos);
        return matches(fluid);
    }

    @Override
    public int getPriority () {
        return 600;
    }

    @Override
    public boolean canProcess (ItemStack stack, Level level) {
        WRAPPER.setItem(0, stack);
        final var optional = TCreateRecipeTypes.SKYSLIME_PROCESSING.filiter(WRAPPER, level);
        return optional.isPresent();
    }

    @Override
    @Nullable
    public List<ItemStack> process (ItemStack stack, Level level) {
        WRAPPER.setItem(0, stack);
        final var optional = TCreateRecipeTypes.SKYSLIME_PROCESSING.filiter(WRAPPER, level);
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
            if (!TCreate.getBoolean(le, CONVERT_FROM_SKYSLIME_TAG)) {
                le.addEffect(new MobEffectInstance(MobEffects.JUMP, 20, 2));
                //le.addEffect(new MobEffectInstance(TCreateEffects.FREEZING.get(), 20, 0));
            }

            if (le instanceof Skeleton skeleton) {
                TCreateTasks.mobConvertTask(
                        20 * 5,
                        skeleton,
                        STRAY,
                        true,
                        (stray) ->
                                TCreate.putBoolean(stray, CONVERT_FROM_SKYSLIME_TAG)
                );
            }
        }
    }

    @Override
    @NotNull
    public Stream<? extends TCreatePattern<FluidState>> patterns () {
        synchronized (patterns) {
            return patterns.stream();
        }
    }

    @NotNull
    public PatternMatcher<FluidState> register (@NotNull TCreatePattern<FluidState> pattern) {
        synchronized (patterns) {
            patterns.add(pattern);
        }
        return this;
    }

    private static boolean defaultPattern (FluidState state) {
        return state.is(skySlime.get());
    }
}