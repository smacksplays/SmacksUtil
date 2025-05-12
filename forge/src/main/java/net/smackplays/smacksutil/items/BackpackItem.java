package net.smackplays.smacksutil.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.menus.BackpackMenu;
import org.jetbrains.annotations.NotNull;

import static net.smackplays.smacksutil.Constants.Backpack.*;

public class BackpackItem extends AbstractBackpackItem {

    public BackpackItem(Properties properties) {
        super(properties);
    }

    @Override
    public MenuProvider createScreenHandlerFactory(ItemStack stack) {
        return new MenuProvider() {
            @Override
            public @NotNull AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
                return new BackpackMenu(SmacksUtil.BACKPACK_MENU.get(), syncId, playerInventory,
                        new BackpackInventory(stack, player.registryAccess(),C_BACKPACK_ROW_NUM * C_BACKPACK_COL_NUM + 4));
            }

            @Override
            public @NotNull Component getDisplayName() {
                return stack.getHoverName();
            }
        };
    }
}