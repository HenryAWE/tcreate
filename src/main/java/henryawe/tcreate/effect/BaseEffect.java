package henryawe.tcreate.effect;

import henryawe.tcreate.SharedSecrets;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class BaseEffect extends MobEffect {
    protected BaseEffect (MobEffectCategory category, Color color) {
        super(category, SharedSecrets.getColor(color));
        initMethod();
    }

    abstract void initMethod ();

    abstract boolean isEffectTick(int tick, int amplifier);

    abstract void applyEffect(@NotNull LivingEntity entity, int amplifier);

    @Override
    public boolean isDurationEffectTick (int duration, int amplifier) {
        return isEffectTick(duration, amplifier);
    }

    @Override
    public void applyEffectTick (@NotNull LivingEntity entity, int amplifier) {
        applyEffect(entity, amplifier);
    }
}
