package net.smackplays.smacksutil.networking.c2spacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import static net.smackplays.smacksutil.Constants.C_TOGGLE_LIGHT_WAND_REQUEST_RL;

public record C2SToggleLightWandItemPacket(int slot) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<C2SToggleLightWandItemPacket> TYPE
            = new CustomPacketPayload.Type<>(C_TOGGLE_LIGHT_WAND_REQUEST_RL);


    public static final StreamCodec<ByteBuf, C2SToggleLightWandItemPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            C2SToggleLightWandItemPacket::slot,
            C2SToggleLightWandItemPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}