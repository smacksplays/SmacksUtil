package net.smackplays.smacksutil.platform;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.networking.c2spacket.*;
import net.smackplays.smacksutil.platform.services.IClientPacketSender;
import org.joml.Vector3f;

import java.util.Objects;
import java.util.UUID;

public class NeoForgeClientPacketSender implements IClientPacketSender {
    @Override
    public void VeinMinerBreakPacket(BlockPos pos, boolean isCreative, boolean replaceSeeds) {
        Minecraft minecraft = Minecraft.getInstance();
        Objects.requireNonNull(minecraft.getConnection()).send(new C2SVeinMinerBreakPacket(new Vector3f(pos.getX(), pos.getY(), pos.getZ()), isCreative, replaceSeeds));
        //PacketHandler.sendToServer(new VeinMinerBreakPacket(mainHandStack, pos, isCreative, replaceSeeds));
    }

    @Override
    public void EnchantPacket(Enchantment enchantment, boolean addRemove) {
        Minecraft minecraft = Minecraft.getInstance();
        var enchantName = enchantment.description().getString();
        var lvl = enchantment.getMaxLevel();

        Objects.requireNonNull(minecraft.getConnection()).send(new C2SEnchantPacket(enchantName, lvl, addRemove));
        //PacketHandler.sendToServer(new C2SEnchantPacket(stack));
    }

    @Override
    public void BackpackSortPacket(int slot) {
        Minecraft minecraft = Minecraft.getInstance();
        Objects.requireNonNull(minecraft.getConnection()).send(new C2SBackpackSortPacket(slot));
        //PacketHandler.sendToServer(new C2SSortPacket(stack));
    }

    @Override
    public void BackpackOpenPacket(int slot) {
        Minecraft minecraft = Minecraft.getInstance();
        Objects.requireNonNull(minecraft.getConnection()).send(new C2SBackpackOpenPacket(slot));
    }

    @Override
    public void ToggleMagnetItemPacket(int slot) {
        Minecraft minecraft = Minecraft.getInstance();
        Objects.requireNonNull(minecraft.getConnection()).send(new C2SToggleMagnetItemPacket(slot));
    }

    @Override
    public void ToggleLightWandItemPacket(int slot) {
        Minecraft minecraft = Minecraft.getInstance();
        Objects.requireNonNull(minecraft.getConnection()).send(new C2SToggleLightWandItemPacket(slot));
    }

    @Override
    public void SetBlockAirPacket(BlockPos pos) {
        Minecraft minecraft = Minecraft.getInstance();
        Vector3f pos3f = new Vector3f(pos.getX(), pos.getY(), pos.getZ());
        Objects.requireNonNull(minecraft.getConnection()).send(new C2SSetBlockAirPacket(pos3f));
        //PacketHandler.sendToServer(new C2SSetBlockAirPacket(pos));
    }

    @Override
    public void TeleportPacket(ResourceKey<Level> levelKey, Vec3 pos, float xRot, float yRot) {
        Minecraft minecraft = Minecraft.getInstance();
        Vector3f pos3f = new Vector3f((float)pos.x, (float)pos.y, (float)pos.z);
        Objects.requireNonNull(minecraft.getConnection()).send(new C2STeleportationPacket(levelKey.toString(), pos3f, xRot, yRot));
        //PacketHandler.sendToServer(new C2STeleportationPacket(levelKey, pos, xRot, yRot));
    }

    @Override
    public void TeleportNBTPacket(Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove) {
        Minecraft minecraft = Minecraft.getInstance();
        Vector3f pos3f = new Vector3f((float)pos.x, (float)pos.y, (float)pos.z);
        Objects.requireNonNull(minecraft.getConnection()).send(new C2STeleportationNBTPacket(pos3f, xRot, yRot, name, dim, remove));
        //PacketHandler.sendToServer(new C2STeleportationNBTPacket(stack, pos, xRot, yRot, name, dim, addRemove));
    }

    @Override
    public void InteractEntityPacket(UUID entityUUID, boolean hand) {
        Minecraft minecraft = Minecraft.getInstance();
        Objects.requireNonNull(minecraft.getConnection()).send(new C2SInteractEntityPacket(entityUUID.toString(), hand));
        //PacketHandler.sendToServer(new C2SInteractEntityPacket(stack, entityUUID, isMainHand));
    }
}
