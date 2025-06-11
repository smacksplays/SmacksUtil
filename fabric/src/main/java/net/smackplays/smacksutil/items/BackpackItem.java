package net.smackplays.smacksutil.items;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.menus.BackpackMenu;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import static net.smackplays.smacksutil.Constants.C_BACKPACK_COL_NUM;
import static net.smackplays.smacksutil.Constants.C_BACKPACK_ROW_NUM;
import static net.smackplays.smacksutil.SmacksUtil.BACKPACK_MENU;

/** class BackpackItem */
public class BackpackItem extends AbstractBackpackItem {
    /** Constructor
     * @param properties properties*/
    public BackpackItem(Properties properties) {
        super(properties);
    }

    /** CreateScreenHandlerFactory
     * @param stack ItemStack
     * @return MenuProvider*/
    @Override
    public MenuProvider createScreenHandlerFactory(ItemStack stack) {
        return new ExtendedScreenHandlerFactory<>() {
            @Override
            public Vector3f getScreenOpeningData(ServerPlayer player) {
                return new Vector3f();
            }

            @Override
            public @NotNull Component getDisplayName() {
                return stack.getHoverName();
            }

            @Override
            public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
                return new BackpackMenu(BACKPACK_MENU, syncId, playerInventory, new BackpackInventory(stack, player.registryAccess(),C_BACKPACK_ROW_NUM * C_BACKPACK_COL_NUM + 4));
            }
        };
    }
}