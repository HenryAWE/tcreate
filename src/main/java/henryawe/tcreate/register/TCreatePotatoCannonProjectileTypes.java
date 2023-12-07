package henryawe.tcreate.register;

import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonProjectileType;
import henryawe.tcreate.SharedSecrets;
import henryawe.tcreate.create.potatocannon.EflnEffects;
import org.jetbrains.annotations.NotNull;

import static com.simibubi.create.content.equipment.potatoCannon.PotatoCannonProjectileType.Builder;
import static slimeknights.tconstruct.gadgets.TinkerGadgets.efln;

public final class TCreatePotatoCannonProjectileTypes {
    @NotNull
    private static Builder create(@NotNull String name) {
        return new Builder(SharedSecrets.asResource(name));
    }

    public static final PotatoCannonProjectileType
            EFLN_TYPE = create("efln").damage(10)
            .onBlockHit(EflnEffects::onBlockHit)
            .registerAndAssign(efln);

    public static void clinit() {
    }
}
