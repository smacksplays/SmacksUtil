package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonBackpackSortPacketHandler;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonEnchantPacketHandler;

import java.util.Optional;
import java.util.Set;

public class C2SEnchantPacketHandler {
    public static void handle(C2SEnchantPacket data, IPayloadContext context) {
        C2SCommonEnchantPacketHandler.handle((ServerPlayer) context.player(), data.enchantment(), data.level(), data.remove());
    }
}
