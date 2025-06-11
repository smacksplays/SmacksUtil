package net.smackplays.smacksutil.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.menus.TeleportationTabletMenu;
import org.jetbrains.annotations.NotNull;

import static net.smackplays.smacksutil.SmacksUtil.TELEPORTATION_TABLET_MENU;

public class TeleportationTablet extends AbstractTeleportationTablet{
    public TeleportationTablet(Properties properties) {
        super(properties);
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
                return new TeleportationTabletMenu(TELEPORTATION_TABLET_MENU.get(), syncId, playerInventory);
            }
        };
    }
}
