package net.smackplays.smacksutil.items;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.LargeBackpackInventory;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class LargeBackpackItem extends AbstractBackpackItem {

    public LargeBackpackItem() {
        super();
    }

    @Override
    public MenuProvider createScreenHandlerFactory(ItemStack stack) {
        return new ExtendedScreenHandlerFactory() {
            @Override
            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
            }

            @Override
            public @NotNull Component getDisplayName() {
                return stack.getHoverName();
            }

            @Override
            public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
                return new LargeBackpackMenu(SmacksUtil.LARGE_BACKPACK_MENU, syncId, playerInventory, new LargeBackpackInventory(stack));
            }
        };
    }
}