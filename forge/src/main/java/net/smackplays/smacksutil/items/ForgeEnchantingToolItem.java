package net.smackplays.smacksutil.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.inventories.EnchantmentToolInventory;
import net.smackplays.smacksutil.menus.EnchantingToolMenu;
import org.jetbrains.annotations.NotNull;

public class ForgeEnchantingToolItem extends AbstractEnchantingToolItem {
    public ForgeEnchantingToolItem() {
        super();
    }

    @Override
    MenuProvider createScreenHandlerFactory(ItemStack stack) {
        return new MenuProvider() {
            @Override
            public @NotNull Component getDisplayName() {
                return stack.getHoverName();
            }

            @Override
            public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
                return new EnchantingToolMenu(syncId, playerInventory, new EnchantmentToolInventory(stack));
            }
        };
    }
}
