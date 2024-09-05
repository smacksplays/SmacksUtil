package net.smackplays.smacksutil.networking.c2spacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;

public record C2SEnchantPacket(String enchantment,int level, boolean remove) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.tryBuild(Constants.MOD_ID, "enchant_packet");

    public static final CustomPacketPayload.Type<C2SEnchantPacket> TYPE
            = new CustomPacketPayload.Type<>(ID);

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