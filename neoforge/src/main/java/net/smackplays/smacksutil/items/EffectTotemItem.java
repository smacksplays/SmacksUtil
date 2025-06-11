package net.smackplays.smacksutil.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.menus.EffectTotemMenu;
import org.jetbrains.annotations.NotNull;

import static net.smackplays.smacksutil.SmacksUtil.EFFECT_TOTEM_MENU;

public class EffectTotemItem extends AbstractEffectTotemItem{
    public EffectTotemItem(Properties properties) {
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
                return new EffectTotemMenu(EFFECT_TOTEM_MENU.get(), syncId, playerInventory);
            }
        };
    }
}
