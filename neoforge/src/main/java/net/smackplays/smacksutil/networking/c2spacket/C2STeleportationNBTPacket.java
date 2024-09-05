package net.smackplays.smacksutil.networking.c2spacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public record C2STeleportationNBTPacket(String stack, Vector3f pos, float xRot, float yRot, String name_dim, boolean remove) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.tryBuild(Constants.MOD_ID, "teleportation_nbt_packet");
    public static final CustomPacketPayload.Type<C2STeleportationNBTPacket> TYPE
            = new CustomPacketPayload.Type<>(ID);


    public static final StreamCodec<ByteBuf, C2STeleportationNBTPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            C2STeleportationNBTPacket::stack,
            ByteBufCodecs.VECTOR3F,
            C2STeleportationNBTPacket::pos,
            ByteBufCodecs.FLOAT,
            C2STeleportationNBTPacket::xRot,
            ByteBufCodecs.FLOAT,
            C2STeleportationNBTPacket::yRot,
            ByteBufCodecs.STRING_UTF8,
            C2STeleportationNBTPacket::name_dim,
            ByteBufCodecs.BOOL,
            C2STeleportationNBTPacket::remove,
            C2STeleportationNBTPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}