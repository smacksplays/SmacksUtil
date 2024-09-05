package net.smackplays.smacksutil.networking.s2cpacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public record S2CBlockBreakPacket(Vector3f pos) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.tryBuild(Constants.MOD_ID, "break_block_packet");

    public static final CustomPacketPayload.Type<S2CBlockBreakPacket> TYPE
            = new CustomPacketPayload.Type<>(ID);


    public static final StreamCodec<ByteBuf, S2CBlockBreakPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F,
            S2CBlockBreakPacket::pos,
            S2CBlockBreakPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}