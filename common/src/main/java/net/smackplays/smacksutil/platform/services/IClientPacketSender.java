package net.smackplays.smacksutil.platform.services;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

/**
 * Interface IClientPacketSender */
public interface IClientPacketSender {
    /** Interface method VeinMinerBreakPacket
     * @param pos pos
     * @param isCreative isCreative
     * @param replaceSeeds replaceSeeds*/
    void VeinMinerBreakPacket(BlockPos pos, boolean isCreative, boolean replaceSeeds);

    /** Interface method EnchantPacket
     * @param stack stack
     * @param addRemove addRemove*/
    void EnchantPacket(Enchantment stack, boolean addRemove);

    /** Interface method BackpackSortPacket
     * @param slot slot*/
    void BackpackSortPacket(int slot);

    /** Interface method BackpackOpenPacket
     * @param slot slot*/
    void BackpackOpenPacket(int slot);

    /** Interface method ToggleMagnetItemPacket
     * @param slot slot*/
    void ToggleMagnetItemPacket(int slot);

    /** Interface method ToggleLightWandItemPacket
     * @param slot slot*/
    void ToggleLightWandItemPacket(int slot);

    /** Interface method SetBlockAirPacket
     * @param pos pos*/
    void SetBlockAirPacket(BlockPos pos);

    /** Interface method TeleportPacket
     * @param levelKey levelKey
     * @param pos pos
     * @param xRot xRot
     * @param yRot yRot*/
    void TeleportPacket(ResourceKey<Level> levelKey, Vec3 pos, float xRot, float yRot);

    /** Interface method TeleportNBTPacket
     * @param pos pos
     * @param xRot xRot
     * @param yRot yRot
     * @param name name
     * @param dim dim
     * @param remove remove*/
    void TeleportNBTPacket(Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove);

    /** Interface method InteractEntityPacket
     * @param entityUUID entityUUID
     * @param hand hand*/
    void InteractEntityPacket(UUID entityUUID, boolean hand);
}
