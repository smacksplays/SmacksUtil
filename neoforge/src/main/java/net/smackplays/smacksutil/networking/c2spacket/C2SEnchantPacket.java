package net.smackplays.smacksutil.networking.c2spacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import static net.smackplays.smacksutil.Constants.C_ENCHANT_REQUEST_RL;

public record C2SEnchantPacket(String enchantment,int level, boolean remove) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<C2SEnchantPacket> TYPE
            = new CustomPacketPayload.Type<>(C_ENCHANT_REQUEST_RL);

    public static final StreamCodec<ByteBuf, C2SEnchantPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            C2SEnchantPacket::enchantment,
            ByteBufCodecs.INT,
            C2SEnchantPacket::level,
            ByteBufCodecs.BOOL,
            C2SEnchantPacket::remove,
            C2SEnchantPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}