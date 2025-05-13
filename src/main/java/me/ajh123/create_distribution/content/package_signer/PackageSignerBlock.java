package me.ajh123.create_distribution.content.package_signer;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.foundation.block.IBE;
import me.ajh123.create_distribution.ModShapes;
import me.ajh123.create_distribution.content.meter.ElectricalEnergyMeterBlockEntity;
import me.ajh123.create_distribution.foundation.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class PackageSignerBlock extends HorizontalDirectionalBlock implements IBE<PackageSignerBlockEntity> {
    public static final MapCodec<PackageSignerBlock> CODEC = simpleCodec(PackageSignerBlock::new);

    public PackageSignerBlock(Properties properties) {
        super(properties);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(FACING));
    }

    public BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
        BlockState state = this.defaultBlockState();
        Direction horizontalDirection = pContext.getHorizontalDirection();
        Player player = pContext.getPlayer();
        state = state.setValue(FACING, horizontalDirection.getOpposite());
        if (player != null && player.isShiftKeyDown()) {
            state = state.setValue(FACING, horizontalDirection);
        }

        return state;
    }

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public Class<PackageSignerBlockEntity> getBlockEntityClass() {
        return PackageSignerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends PackageSignerBlockEntity> getBlockEntityType() {
        return ModBlocks.PACKAGE_SIGNER_BLOCK_ENTITY.get();
    }
}
