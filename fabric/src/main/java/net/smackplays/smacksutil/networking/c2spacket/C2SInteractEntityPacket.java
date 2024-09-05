package net.smackplays.smacksutil.networking.c2spacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import static net.smackplays.smacksutil.Constants.C_INTERACT_ENTITY_REQUEST_RL;

public record C2SInteractEntityPacket(String entityUUID, boolean hand) implements CustomPacketPayload {

    public static final Type<C2SInteractEntityPacket> TYPE
            = new Type<>(C_INTERACT_ENTITY_REQUEST_RL);

    public static final StreamCodec<ByteBuf, C2SInteractEntityPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            C2SInteractEntityPacket::entityUUID,
            ByteBufCodecs.BOOL,
            C2SInteractEntityPacket::hand,
            C2SInteractEntityPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}