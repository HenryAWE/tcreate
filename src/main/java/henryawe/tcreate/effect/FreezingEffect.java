package henryawe.tcreate.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static net.minecraft.world.damagesource.DamageSource.FREEZE;

public class FreezingEffect extends BaseEffect {
    protected FreezingEffect (MobEffectCategory category, Color color) {
        super(category, color);
    }

    @Override
    void initMethod () {
    }

    @Override
    boolean isEffectTick (int tick, int amplifier) {
        return tick > 0 && tick % (20 * 2) == 0;
    }

    @Override
    void applyEffect (@NotNull LivingEntity entity, int amplifier) {
        entity.hurt(FREEZE, 0.5f);
    }
}
