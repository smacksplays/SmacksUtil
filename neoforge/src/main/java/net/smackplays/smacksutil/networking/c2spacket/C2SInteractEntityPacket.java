package net.smackplays.smacksutil.networking.c2spacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;

public record C2SInteractEntityPacket(String stack, int entityUUID, boolean hand) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.tryBuild(Constants.MOD_ID, "interact_entity_packet");

    public static final CustomPacketPayload.Type<C2SInteractEntityPacket> TYPE
            = new CustomPacketPayload.Type<>(ID);

    public static final StreamCodec<ByteBuf, C2SInteractEntityPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            C2SInteractEntityPacket::stack,
            ByteBufCodecs.INT,
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