package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.Optional;
import java.util.Set;

public class C2SEnchantPacket {
    private final String enchantment;
    private final int level;
    private final boolean addRemove;

    public C2SEnchantPacket(String enchantment, int level, boolean addRemove) {
        this.enchantment = enchantment;
        this.level = level;
        this.addRemove = addRemove;
    }

    public C2SEnchantPacket(FriendlyByteBuf buffer) {
        enchantment = buffer.readUtf();
        level = buffer.readInt();
        addRemove = buffer.readBoolean();
    }

    public void encode(FriendlyByteBuf buffer) {
       buffer.writeUtf(enchantment);
       buffer.writeInt(level);
       buffer.writeBoolean(addRemove);
    }

    public void handle(CustomPayloadEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player == null)
            return;

        Optional<HolderSet.Named<Enchantment>> optional = player.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.TOOLTIP_ORDER);
        if (optional.isPresent()){
            var l = optional.get().stream().toList();
            for (Holder<Enchantment> entry : l){
                Enchantment e = entry.value();
                if(e.description().getString().equals(enchantment)){
                    AbstractContainerMenu containerMenu = player.containerMenu;
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
        }
    }
}