package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonTeleportationPacketHandler;

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
        if (context.getSender() != null) {
            C2SCommonTeleportationPacketHandler.handle(context.getSender(), context.getSender().level(), levelKey, pos, xRot, yRot);
        }
    }
}