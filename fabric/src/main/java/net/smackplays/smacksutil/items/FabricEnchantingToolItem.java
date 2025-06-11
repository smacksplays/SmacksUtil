package net.smackplays.smacksutil.items;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.inventories.EnchantmentToolInventory;
import net.smackplays.smacksutil.menus.EnchantingToolMenu;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class FabricEnchantingToolItem extends AbstractEnchantingToolItem {
    public FabricEnchantingToolItem(Properties properties) {
        super(properties);
    }

    @Override
    MenuProvider createScreenHandlerFactory(ItemStack stack) {
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
                return new EnchantingToolMenu(syncId, playerInventory,
                        new EnchantmentToolInventory(stack, player.registryAccess(), 1));
            }
        };
    }
}
