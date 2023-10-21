package henryawe.tcreate.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class FreezingEffect extends BaseEffect {
    protected FreezingEffect (MobEffectCategory category, Color color) {
        super(category, color);
    }

    @Override
    void initMethod () {
    }

    @Override
    boolean isEffectTick (int duration, int amplifier) {
        return false;
    }

    @Override
    void applyEffect (@NotNull LivingEntity entity, int amplifier) {
    }
}
