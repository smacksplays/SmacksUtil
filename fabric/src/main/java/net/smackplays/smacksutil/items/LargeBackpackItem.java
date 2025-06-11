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
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import static net.smackplays.smacksutil.Constants.C_LARGE_BACKPACK_COL_NUM;
import static net.smackplays.smacksutil.Constants.C_LARGE_BACKPACK_ROW_NUM;
import static net.smackplays.smacksutil.SmacksUtil.LARGE_BACKPACK_MENU;

public class LargeBackpackItem extends AbstractBackpackItem {

    public LargeBackpackItem(Properties properties) {
        super(properties);
    }

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
                return new LargeBackpackMenu(LARGE_BACKPACK_MENU, syncId, playerInventory,
                        new BackpackInventory(stack, player.registryAccess(), C_LARGE_BACKPACK_ROW_NUM * C_LARGE_BACKPACK_COL_NUM + 4));
            }
        };
    }
}