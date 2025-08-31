package net.smackplays.smacksutil.platform;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.networking.c2spacket.*;
import net.smackplays.smacksutil.platform.services.IClientPacketSender;
import org.joml.Vector3f;

import java.util.UUID;

import static net.smackplays.smacksutil.Constants.*;

/**
 * Class FabricClientPacketSender */
public class FabricClientPacketSender implements IClientPacketSender {
    /** Constructor*/
    public FabricClientPacketSender() {

    }
    /** VeinMinerBreakPacket
     * @param sourcePos sourcePos
     * @param curr curr
     * @param isCreative isCreative
     * @param replaceSeeds replaceSeeds*/
    @Override
    public void VeinMinerBreakPacket(BlockPos sourcePos, BlockPos curr, boolean isCreative, boolean replaceSeeds) {
        Vector3f sourcePos3f = new Vector3f(sourcePos.getX(), sourcePos.getY(), sourcePos.getZ());
        Vector3f curr3f = new Vector3f(curr.getX(), curr.getY(), curr.getZ());
        if (ClientPlayNetworking.canSend(C_VEINMINER_BREAK_REQUEST_RL)){
            ClientPlayNetworking.send(new C2SVeinMinerBreakPacket(sourcePos3f, curr3f, isCreative, replaceSeeds));
        }
    }

    @Override
    public void EnchantPacket(Enchantment ench, boolean addRemove) {
        if (ClientPlayNetworking.canSend(C_ENCHANT_REQUEST_RL)){
            ClientPlayNetworking.send(new C2SEnchantPacket(ench.description().getString(),ench.getMaxLevel(), addRemove));
        }
    }

    @Override
    public void BackpackSortPacket(int slot) {
        if (ClientPlayNetworking.canSend(C_BACKPACK_SORT_REQUEST_RL)){
            ClientPlayNetworking.send(new C2SBackpackSortPacket(slot));
        }
    }

    @Override
    public void BackpackOpenPacket(int slot) {
        if (ClientPlayNetworking.canSend(C_BACKPACK_OPEN_REQUEST_RL)){
            ClientPlayNetworking.send(new C2SBackpackOpenPacket(slot));
        }
    }

    @Override
    public void ToggleMagnetItemPacket(int slot) {
        if (ClientPlayNetworking.canSend(C_TOGGLE_MAGNET_ITEM_REQUEST_RL)){
            ClientPlayNetworking.send(new C2SToggleMagnetItemPacket(slot));
        }
    }

    @Override
    public void ToggleLightWandItemPacket(int slot) {
        if (ClientPlayNetworking.canSend(C_TOGGLE_LIGHT_WAND_REQUEST_RL)){
            ClientPlayNetworking.send(new C2SToggleLightWandItemPacket(slot));
        }
    }

    /** SetBlockAirPacket
     * @param pos pos*/
    @Override
    public void SetBlockAirPacket(BlockPos pos) {
        Vector3f pos3f = new Vector3f(pos.getX(), pos.getY(), pos.getZ());
        if (ClientPlayNetworking.canSend(C_SET_BLOCK_AIR_REQUEST_RL)){
            ClientPlayNetworking.send(new C2SSetBlockAirPacket(pos3f));
        }
    }

    @Override
    public void TeleportPacket(ResourceKey<Level> levelKey, Vec3 pos, float xRot, float yRot) {
        Vector3f pos3f = new Vector3f((float)pos.x, (float)pos.y, (float)pos.z);
        if (ClientPlayNetworking.canSend(C_TELEPORT_REQUEST_RL)){
            ClientPlayNetworking.send(new C2STeleportationPacket(levelKey.toString(), pos3f, xRot, yRot));
        }
    }

    @Override
    public void TeleportNBTPacket(Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove) {
        Vector3f pos3f = new Vector3f((float)pos.x, (float)pos.y, (float)pos.z);
        if (ClientPlayNetworking.canSend(C_TELEPORT_NBT_REQUEST_RL)){
            ClientPlayNetworking.send(new C2STeleportationNBTPacket(pos3f, xRot, yRot, name, dim, remove));
        }
    }

    // hand true => main hand, false => off_hand
    @Override
    public void InteractEntityPacket(UUID entityUUID, boolean hand) {
        if (ClientPlayNetworking.canSend(C_INTERACT_ENTITY_REQUEST_RL)){
            ClientPlayNetworking.send(new C2SInteractEntityPacket(entityUUID.toString(), hand));
        }
    }
}
