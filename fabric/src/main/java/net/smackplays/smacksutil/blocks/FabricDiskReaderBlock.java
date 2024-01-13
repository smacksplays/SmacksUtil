package net.smackplays.smacksutil.blocks;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.blockEntities.FabricDiskReaderBlockEntity;
import net.smackplays.smacksutil.menus.FabricDiskReaderMenu;
import org.jetbrains.annotations.Nullable;

public class FabricDiskReaderBlock extends AbstractDiskReaderBlock {
    public static final MapCodec<FabricDiskReaderBlock> CODEC = simpleCodec(FabricDiskReaderBlock::new);

    public FabricDiskReaderBlock(Properties props) {
        super(props);
    }

    @Override
    protected MapCodec<FabricDiskReaderBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FabricDiskReaderBlockEntity(blockPos, blockState);
    }

    @Override
    protected void openContainer(Level world, BlockPos pos, Player player) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof FabricDiskReaderBlockEntity) {
            player.openMenu(createScreenHandlerFactory(entity));
        }
    }

    public MenuProvider createScreenHandlerFactory(BlockEntity entity) {
        return new ExtendedScreenHandlerFactory() {
            @Override
            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
            }

            @Override
            public Component getDisplayName() {
                return entity.getBlockState().getBlock().getName();
            }

            @Override
            public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
                return new FabricDiskReaderMenu(syncId, playerInventory, (Container) entity);
            }
        };
    }
}