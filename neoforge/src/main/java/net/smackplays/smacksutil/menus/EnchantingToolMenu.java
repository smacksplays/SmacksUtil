package net.smackplays.smacksutil.menus;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.smackplays.smacksutil.inventories.EnchantmentToolInventory;

import static net.smackplays.smacksutil.SmacksUtil.ENCHANTING_TOOL_MENU;

public class EnchantingToolMenu extends AbstractEnchantingToolMenu {

    public EnchantingToolMenu(int syncId, Inventory playerInv, Container inv) {
        super(ENCHANTING_TOOL_MENU.get(), syncId, playerInv, inv);
    }

    public static EnchantingToolMenu create(int syncId, Inventory playerInventory) {
        return new EnchantingToolMenu(syncId, playerInventory,
                new EnchantmentToolInventory(playerInventory.getSelectedItem(), playerInventory.player.registryAccess(), 1));
    }
}