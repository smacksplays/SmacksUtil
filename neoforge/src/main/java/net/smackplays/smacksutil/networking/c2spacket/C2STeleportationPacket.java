package net.smackplays.smacksutil.networking.c2spacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public record C2STeleportationPacket(String levelKey, Vector3f pos, float xRot, float yRot) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.tryBuild(Constants.MOD_ID, "teleportation_data");
    public static final CustomPacketPayload.Type<C2STeleportationPacket> TYPE
            = new CustomPacketPayload.Type<>(ID);


    public static final StreamCodec<ByteBuf, C2STeleportationPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            C2STeleportationPacket::levelKey,
            ByteBufCodecs.VECTOR3F,
            C2STeleportationPacket::pos,
            ByteBufCodecs.FLOAT,
            C2STeleportationPacket::xRot,
            ByteBufCodecs.FLOAT,
            C2STeleportationPacket::yRot,
            C2STeleportationPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}