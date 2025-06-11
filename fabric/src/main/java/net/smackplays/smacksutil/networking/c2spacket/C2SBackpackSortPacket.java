package net.smackplays.smacksutil.networking.c2spacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import static net.smackplays.smacksutil.Constants.C_BACKPACK_SORT_REQUEST_RL;

/**
 * Record C2SBackpackSortPacket
 * @param slot slot */
public record C2SBackpackSortPacket(int slot) implements CustomPacketPayload {
    public static final Type<C2SBackpackSortPacket> TYPE
            = new Type<>(C_BACKPACK_SORT_REQUEST_RL);


    public static final StreamCodec<ByteBuf, C2SBackpackSortPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            C2SBackpackSortPacket::slot,
            C2SBackpackSortPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}