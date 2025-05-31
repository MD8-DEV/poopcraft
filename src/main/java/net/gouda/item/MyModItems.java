package net.gouda.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.gouda.PoopCraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MyModItems {

    public static final Item POOP = registerItem("poop", new PoopItem(new Item.Settings()));
    public static final Item MEGA_POOP = registerItem("mega_poop", new GlintItem(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries. ITEM, Identifier.of(PoopCraft.MOD_ID, name), item);
    }
    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(POOP);
            entries.add(MEGA_POOP);
        });
    }
}
