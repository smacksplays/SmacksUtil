package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonEnchantPacketHandler;

import java.util.Optional;
import java.util.Set;

public class C2SEnchantPacketHandler {
    public static void handle(C2SEnchantPacket data, ServerPlayNetworking.Context context) {
        C2SCommonEnchantPacketHandler.handle(context.player(), data.enchantment(), data.level(), data.remove());
    }
}
