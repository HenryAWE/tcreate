package henryawe.tcreate.create.potatocannon;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.gadgets.entity.EFLNExplosion;

public final class EflnEffects {
    public static boolean onBlockHit(@NotNull LevelAccessor l, @NotNull BlockHitResult result) {
        if (!l.isClientSide() && l instanceof Level worldIn) {
            final BlockPos pos = result.getBlockPos();
            final var explosion = new EFLNExplosion(
                    worldIn,
                    null, null, null,
                    pos.getX(), pos.getY(), pos.getZ(),
                    6f, true, Explosion.BlockInteraction.DESTROY
            );
            if (!ForgeEventFactory.onExplosionStart(worldIn, explosion)) {
                explosion.explode();
                explosion.finalizeExplosion(true);
            }
        }
        return true;
    }
}
