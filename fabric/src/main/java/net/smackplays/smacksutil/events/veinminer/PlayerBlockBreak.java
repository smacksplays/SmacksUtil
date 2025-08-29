package net.smackplays.smacksutil.events.veinminer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.platform.Services;
import org.jetbrains.annotations.Nullable;

/**
 * Class PlayerBlockBreak */
public class PlayerBlockBreak implements PlayerBlockBreakEvents.Before {
    /** Constructor*/
    public PlayerBlockBreak() {

    }
    /** Event
     * @param world world
     * @param player player
     * @param pos pos
     * @param state state
     * @param blockEntity blockEntity
     * @return true if nothing was done*/
    @Override
    public boolean beforeBlockBreak(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (Services.S2C_PACKET_SENDER != null
                && Services.KEY_HANDLER != null) {
            Services.S2C_PACKET_SENDER.sendToPlayerBlockBreakPacket((ServerPlayer) player, pos);
            return true;
        }
        return true;
    }
}
