package net.smackplays.smacksutil.networking.c2spacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import static net.smackplays.smacksutil.Constants.C_BACKPACK_OPEN_REQUEST_RL;

public record C2SBackpackOpenPacket(int slot) implements CustomPacketPayload {

    public static final Type<C2SBackpackOpenPacket> TYPE
            = new Type<>(C_BACKPACK_OPEN_REQUEST_RL);


    public static final StreamCodec<ByteBuf, C2SBackpackOpenPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            C2SBackpackOpenPacket::slot,
            C2SBackpackOpenPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}