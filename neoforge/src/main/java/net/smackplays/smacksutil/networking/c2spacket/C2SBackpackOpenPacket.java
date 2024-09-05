package net.smackplays.smacksutil.networking.c2spacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;

public record C2SBackpackOpenPacket(int slot) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.tryBuild(Constants.MOD_ID, Constants.C_BACKPACK_OPEN_REQUEST);

    public static final CustomPacketPayload.Type<C2SBackpackOpenPacket> TYPE
            = new CustomPacketPayload.Type<>(ID);


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