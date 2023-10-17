package henryawe.tcreate;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TCreateTabs {
    public static CreativeModeTab MAIN = new CreativeModeTab(TCreate.MODID) {
        @NotNull
        @Override
        public ItemStack makeIcon() {
            return TCreateItems.CRUSHED_COBALT.get().getDefaultInstance();
        }
    };
}
