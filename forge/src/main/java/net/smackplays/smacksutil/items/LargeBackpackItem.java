package net.smackplays.smacksutil.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import org.jetbrains.annotations.NotNull;

import static net.smackplays.smacksutil.Constants.Backpack.*;

public class LargeBackpackItem extends AbstractBackpackItem {

    public LargeBackpackItem(Properties properties) {
        super(properties);
    }

    @Override
    public MenuProvider createScreenHandlerFactory(ItemStack stack) {
        return new MenuProvider() {
            @Override
            public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
                return new LargeBackpackMenu(SmacksUtil.LARGE_BACKPACK_MENU.get(), syncId, playerInventory,
                        new BackpackInventory(stack, player.registryAccess(), C_LARGE_BACKPACK_ROW_NUM * C_LARGE_BACKPACK_COL_NUM + 4));
            }

            @Override
            public @NotNull Component getDisplayName() {
                return stack.getHoverName();
            }
        };
    }
}