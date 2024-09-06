package net.smackplays.smacksutil.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.networking.PacketHandler;
import net.smackplays.smacksutil.networking.c2spacket.*;
import net.smackplays.smacksutil.platform.services.IClientPacketSender;

import java.util.UUID;

public class ForgeClientPacketSender implements IClientPacketSender {
    @Override
    public void VeinMinerBreakPacket(BlockPos pos, boolean isCreative, boolean replaceSeeds) {
        PacketHandler.sendToServer(new C2SVeinMinerBreakPacket(pos, isCreative, replaceSeeds));
    }

    @Override
    public void EnchantPacket(Enchantment enchantment, boolean addRemove) {
        String ench = enchantment.description().getString();
        int level = enchantment.getMaxLevel();
        PacketHandler.sendToServer(new C2SEnchantPacket(ench, level, addRemove));
    }

    @Override
    public void BackpackSortPacket(int slot) {
        PacketHandler.sendToServer(new C2SBackpackSortPacket(slot));
    }

    @Override
    public void BackpackOpenPacket(int slot) {
        PacketHandler.sendToServer(new C2SBackpackOpenPacket(slot));
    }

    @Override
    public void ToggleMagnetItemPacket(int slot) {
        PacketHandler.sendToServer(new C2SToggleMagnetItemPacket(slot));
    }

    @Override
    public void ToggleLightWandItemPacket(int slot) {
        PacketHandler.sendToServer(new C2SToggleLightWandItemPacket(slot));
    }

    @Override
    public void SetBlockAirPacket(BlockPos pos) {
        PacketHandler.sendToServer(new C2SSetBlockAirPacket(pos));
    }

    @Override
    public void TeleportPacket(ResourceKey<Level> levelKey, Vec3 pos, float xRot, float yRot) {
        PacketHandler.sendToServer(new C2STeleportationPacket(levelKey.toString(), pos, xRot, yRot));
    }

    @Override
    public void TeleportNBTPacket(Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove) {
        PacketHandler.sendToServer(new C2STeleportationNBTPacket(pos, xRot, yRot, name, dim, remove));
    }

    @Override
    public void InteractEntityPacket(UUID entityUUID, boolean hand) {
        PacketHandler.sendToServer(new C2SInteractEntityPacket(entityUUID.toString(), hand));
    }
}
