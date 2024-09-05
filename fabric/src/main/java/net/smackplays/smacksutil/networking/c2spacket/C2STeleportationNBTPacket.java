package net.smackplays.smacksutil.networking.c2spacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import static net.smackplays.smacksutil.Constants.C_TELEPORT_NBT_REQUEST_RL;

public record C2STeleportationNBTPacket(Vector3f pos, float xRot, float yRot, String name, String dim, boolean remove) implements CustomPacketPayload {
    public static final Type<C2STeleportationNBTPacket> TYPE
            = new Type<>(C_TELEPORT_NBT_REQUEST_RL);


    public static final StreamCodec<ByteBuf, C2STeleportationNBTPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F,
            C2STeleportationNBTPacket::pos,
            ByteBufCodecs.FLOAT,
            C2STeleportationNBTPacket::xRot,
            ByteBufCodecs.FLOAT,
            C2STeleportationNBTPacket::yRot,
            ByteBufCodecs.STRING_UTF8,
            C2STeleportationNBTPacket::name,
            ByteBufCodecs.STRING_UTF8,
            C2STeleportationNBTPacket::dim,
            ByteBufCodecs.BOOL,
            C2STeleportationNBTPacket::remove,
            C2STeleportationNBTPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}