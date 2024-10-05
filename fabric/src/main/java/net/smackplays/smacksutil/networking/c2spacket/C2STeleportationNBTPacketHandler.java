package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonTeleportationNBTPacketHandler;
import org.joml.Vector3f;

public class C2STeleportationNBTPacketHandler {
    public static void handle(final C2STeleportationNBTPacket data, ServerPlayNetworking.Context context) {
        Vec3 pos = new Vec3(data.pos().x, data.pos().y, data.pos().z);
        C2SCommonTeleportationNBTPacketHandler.handle(context.player(), pos, data.xRot(), data.yRot(), data.name(), data.dim(), data.remove());
    }
}
