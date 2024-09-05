package net.smackplays.smacksutil.networking.c2spacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public record C2SSetBlockAirPacket(Vector3f pos) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.tryBuild(Constants.MOD_ID, "set_block_air_packet");
    public static final CustomPacketPayload.Type<C2SSetBlockAirPacket> TYPE
            = new CustomPacketPayload.Type<>(ID);


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