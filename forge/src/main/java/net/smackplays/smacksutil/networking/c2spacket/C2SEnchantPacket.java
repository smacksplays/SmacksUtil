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
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonBackpackOpenPacketHandler;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonEnchantPacketHandler;

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
        if (context.getSender() != null) {
            C2SCommonEnchantPacketHandler.handle(context.getSender(), enchantment, level, addRemove);
        }
    }
}