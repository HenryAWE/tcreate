package henryawe.tcreate.register;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import henryawe.tcreate.TCreate;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;

import static com.simibubi.create.AllTags.AllItemTags.CRUSHED_RAW_MATERIALS;

public final class TCreateItems {
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(TCreate.MODID);

    public static final ItemEntry<Item> CRUSHED_COBALT = REGISTRATE.item("crushed_raw_cobalt", Item::new)
            .tag(CRUSHED_RAW_MATERIALS.tag)
            .tab(() -> TCreateTabs.MAIN)
            .register();

    public static final void register(IEventBus bus) {
        REGISTRATE.registerEventListeners(bus);
    }
}
