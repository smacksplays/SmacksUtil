package net.smackplays.smacksutil.platform.services;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public interface IClientPacketSender {
    void VeinMinerBreakPacket(BlockPos pos, boolean isCreative, boolean replaceSeeds);

    void EnchantPacket(Enchantment stack, boolean addRemove);

    void BackpackSortPacket(int slot);

    void BackpackOpenPacket(int slot);

    void ToggleMagnetItemPacket(int slot);

    void ToggleLightWandItemPacket(int slot);

    void SetBlockAirPacket(BlockPos pos);

    void TeleportPacket(ResourceKey<Level> levelKey, Vec3 pos, float xRot, float yRot);

    void TeleportNBTPacket(Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove);

    void InteractEntityPacket(UUID entityUUID, boolean hand);
}
