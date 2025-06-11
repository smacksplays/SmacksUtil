package net.smackplays.smacksutil.networking.c2spacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import static net.smackplays.smacksutil.Constants.C_SET_BLOCK_AIR_REQUEST_RL;

/**
 * Record C2SSetBlockAirPacket
 * @param pos pos */
public record C2SSetBlockAirPacket(Vector3f pos) implements CustomPacketPayload {
    public static final Type<C2SSetBlockAirPacket> TYPE
            = new Type<>(C_SET_BLOCK_AIR_REQUEST_RL);


    public static final StreamCodec<ByteBuf, C2SSetBlockAirPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F,
            C2SSetBlockAirPacket::pos,
            C2SSetBlockAirPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}