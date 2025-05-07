package net.smackplays.smacksutil.networking.c2shandlers;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.smackplays.smacksutil.inventories.EnchantmentToolInventory;
import net.smackplays.smacksutil.menus.AbstractEnchantingToolMenu;

import java.util.Optional;
import java.util.Set;

public class C2SCommonEnchantPacketHandler {
    public static void handle(ServerPlayer player, String enchantment, int level, boolean addRemove) {
        Optional<HolderSet.Named<Enchantment>> optional = player.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(EnchantmentTags.TOOLTIP_ORDER);
        if (optional.isPresent()){
            var l = optional.get().stream().toList();
            AbstractEnchantingToolMenu containerMenu = (AbstractEnchantingToolMenu) player.containerMenu;
            for (Holder<Enchantment> entry : l){
                Enchantment e = entry.value();
                if(e.description().getString().equals(enchantment)){
                    ItemStack stack = containerMenu.slots.getFirst().getItem();
                    if (addRemove){
                        stack.enchant(entry, level);
                    }else{
                        ItemEnchantments ench = stack.getEnchantments();
                        Set<Holder<Enchantment>> set = ench.keySet();
                        Holder<Enchantment> toRemove = null;
                        for (Holder<Enchantment> enchantmentHolder : set){
                            if (enchantmentHolder.value().description().getString().equals(enchantment)){
                                toRemove = enchantmentHolder;
                            }
                        }
                        if (toRemove != null){
                            stack.set(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
                            for (Holder<Enchantment> en : set) {
                                if (!en.value().description().getString().equals(toRemove.value().description().getString())){
                                    stack.enchant(en, en.value().getMaxLevel());
                                }
                            }
                        }
                    }
                }
            }
            containerMenu.inventory.setChanged();
        }
    }
}
