package henryawe.tcreate.effect;

import henryawe.tcreate.TCreate;
import henryawe.tcreate.create.fans.processing.SkySlimeType;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.UUID;

import static net.minecraft.world.damagesource.DamageSource.FREEZE;

public class ColdEffect extends BaseEffect {
    protected static final Collection<EntityFiliter> FILTERS = new ArrayDeque<>(4);

    public ColdEffect () {
        super(MobEffectCategory.HARMFUL, new Color(118, 136, 238));
    }

    @Override
    void initMethod () {
        addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                UUID.randomUUID().toString(),
                -0.15F,
                AttributeModifier.Operation.MULTIPLY_TOTAL
        );
        addAttributeModifier(
                Attributes.ATTACK_SPEED,
                UUID.randomUUID().toString(),
                -0.1F,
                AttributeModifier.Operation.MULTIPLY_TOTAL
        );
    }

    @Override
    boolean isEffectTick (int tick, int amplifier) {
        return tick > 0 && tick % ((20 * 6) / amplifier + (20 * 2)) == 0;
    }

    @Override
    void applyEffect (@NotNull LivingEntity entity, int amplifier) {
        entity.hurt(FREEZE, 0.5f);
    }

    @Override
    boolean filiter (@NotNull LivingEntity entity, int amplifier) {
        synchronized (FILTERS) {
            if (FILTERS.stream().anyMatch((filiter) -> filiter.filiter(entity, amplifier)))
                return true;
        }
        return !TCreate.getBoolean(entity, SkySlimeType.CONVERT_FROM_SKYSLIME_TAG);
    }

    @Override
    public double getAttributeModifierValue (int amplifier, @NotNull AttributeModifier attributeModifier) {
        return amplifier * attributeModifier.getAmount();
    }

    public static void registerFilier (EntityFiliter filiter) {
        synchronized (FILTERS) {
            FILTERS.add(filiter);
        }
    }
}
