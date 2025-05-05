package net.smackplays.smacksutil.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.menus.BackpackMenu;
import org.jetbrains.annotations.NotNull;

import static net.smackplays.smacksutil.Constants.MOD_ID;

public class BackpackItem extends AbstractBackpackItem {

    public BackpackItem() {
        super(ArmorMaterials.LEATHER, ResourceKey.create(
                Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, Constants.C_BACKPACK_ITEM)));
    }

    @Override
    public MenuProvider createScreenHandlerFactory(ItemStack stack) {
        return new MenuProvider() {
            @Override
            public @NotNull AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
                return new BackpackMenu(SmacksUtil.BACKPACK_MENU.get(), syncId, playerInventory, new BackpackInventory(stack, player.registryAccess()));
            }

            @Override
            public @NotNull Component getDisplayName() {
                return stack.getHoverName();
            }
        };
    }
}