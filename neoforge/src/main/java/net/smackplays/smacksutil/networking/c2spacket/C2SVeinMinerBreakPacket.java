package net.smackplays.smacksutil.networking.c2spacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import static net.smackplays.smacksutil.Constants.C_VEINMINER_BREAK_REQUEST_RL;

public record C2SVeinMinerBreakPacket(Vector3f pos, boolean isCreative, boolean replaceSeeds) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<C2SVeinMinerBreakPacket> TYPE
            = new CustomPacketPayload.Type<>(C_VEINMINER_BREAK_REQUEST_RL);


    public static final StreamCodec<ByteBuf, C2SVeinMinerBreakPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F,
            C2SVeinMinerBreakPacket::pos,
            ByteBufCodecs.BOOL,
            C2SVeinMinerBreakPacket::isCreative,
            ByteBufCodecs.BOOL,
            C2SVeinMinerBreakPacket::replaceSeeds,
            C2SVeinMinerBreakPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
