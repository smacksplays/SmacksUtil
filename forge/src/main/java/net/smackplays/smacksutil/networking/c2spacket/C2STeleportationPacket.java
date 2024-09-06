package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.Set;

public class C2STeleportationPacket {
    private final String levelKey;
    private final Vec3 pos;
    private final float xRot;
    private final float yRot;

    public C2STeleportationPacket(String levelKey, Vec3 pos, float xRot, float yRot) {
        this.levelKey = levelKey;
        this.pos = pos;
        this.xRot = xRot;
        this.yRot = yRot;
    }

    public C2STeleportationPacket(FriendlyByteBuf buffer) {
        levelKey = buffer.readUtf();
        pos = buffer.readVec3();
        xRot = buffer.readFloat();
        yRot = buffer.readFloat();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(levelKey);
        buffer.writeVec3(pos);
        buffer.writeFloat(xRot);
        buffer.writeFloat(yRot);
    }

    public void handle(CustomPayloadEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player == null) return;
        Level level = player.level();

        ResourceKey<?> tempKey = null;

        Set<ResourceKey<Level>> levelSet = player.registryAccess().registryOrThrow(Registries.DIMENSION).registryKeySet();
        for (ResourceKey<Level> key : levelSet){
            if (key.toString().equals(levelKey)){
                tempKey = key;
            }
        }
        if (tempKey != null){
            ResourceKey<Level> levelKey = ResourceKey.create(Registries.DIMENSION, tempKey.location());
            MinecraftServer server = level.getServer();
            ServerLevel serverLevel = server.getLevel(levelKey);
            //player.teleportTo(pos.x, pos.y, pos.z);
            if (serverLevel != null) {
                player.teleportTo(serverLevel, pos.x, pos.y, pos.z, Set.of(), yRot, xRot);
            }
        }
    }
}