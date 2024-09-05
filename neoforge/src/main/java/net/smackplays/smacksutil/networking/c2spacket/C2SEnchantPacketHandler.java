package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Optional;
import java.util.Set;

public class C2SEnchantPacketHandler {
    public static void handle(C2SEnchantPacket customPacketPayload, IPayloadContext context) {
        // Do something with the data, on the main thread
        context.enqueueWork(()  -> {
            Player player = context.player();

            Optional<HolderSet.Named<Enchantment>> optional = player.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.TOOLTIP_ORDER);
            var l = optional.get().stream().toList();
            for (Holder<Enchantment> entry : l){
                Enchantment e = entry.value();
                if(e.description().getString().equals(customPacketPayload.enchantment())){
                    AbstractContainerMenu containerMenu = player.containerMenu;
                    ItemStack stack = containerMenu.slots.get(0).getItem();
                    if (customPacketPayload.remove()){
                        stack.enchant(entry, customPacketPayload.level());
                    }else{
                        stack.enchant(entry, 0);
                        ItemEnchantments ench = stack.getTagEnchantments();
                        Set<Holder<Enchantment>> set = ench.keySet();
                        Holder<Enchantment> toRemove = null;
                        for (Holder<Enchantment> enchantmentHolder : set){
                            if (enchantmentHolder.value().description().getString().equals(customPacketPayload.enchantment())){
                                toRemove = enchantmentHolder;
                            }
                        }
                        // TODO FIX
                        stack.set(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
                    }
                }
            }
        });
    }
}
