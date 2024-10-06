package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonTeleportationNBTPacketHandler;

import java.nio.charset.StandardCharsets;

public class C2STeleportationNBTPacket {
    private final Vec3 pos;
    private final float xRot;
    private final float yRot;
    private final String name;
    private final String dim;
    private final boolean addRemove;

    public C2STeleportationNBTPacket(Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove) {
        this.pos = pos;
        this.xRot = xRot;
        this.yRot = yRot;
        this.name = name;
        this.dim = dim;
        this.addRemove = remove;
    }

    public C2STeleportationNBTPacket(FriendlyByteBuf buffer) {
        pos = buffer.readVec3();
        xRot = buffer.readFloat();
        yRot = buffer.readFloat();
        name = buffer.readUtf();
        dim = buffer.readUtf();
        addRemove = buffer.readBoolean();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVec3(pos);
        buffer.writeFloat(xRot);
        buffer.writeFloat(yRot);
        buffer.writeByteArray(name.getBytes(StandardCharsets.UTF_8));
        buffer.writeByteArray(dim.getBytes(StandardCharsets.UTF_8));
        buffer.writeBoolean(addRemove);
    }

    public void handle(CustomPayloadEvent.Context context) {
        if (context.getSender() != null) {
            C2SCommonTeleportationNBTPacketHandler.handle(context.getSender(), pos, xRot, yRot, name, dim, addRemove);
        }
    }
}