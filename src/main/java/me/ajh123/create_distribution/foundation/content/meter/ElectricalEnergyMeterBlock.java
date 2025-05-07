package me.ajh123.create_distribution.foundation.content.meter;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.foundation.block.IBE;
import me.ajh123.create_distribution.ModShapes;
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

public class ElectricalEnergyMeterBlock extends HorizontalDirectionalBlock implements IBE<ElectricalEnergyMeterBlockEntity> {
    public static final MapCodec<ElectricalEnergyMeterBlock> CODEC = simpleCodec(ElectricalEnergyMeterBlock::new);

    public ElectricalEnergyMeterBlock(Properties properties) {
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
    public Class<ElectricalEnergyMeterBlockEntity> getBlockEntityClass() {
        return ElectricalEnergyMeterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ElectricalEnergyMeterBlockEntity> getBlockEntityType() {
        return ModBlocks.ENERGY_METER_BLOCK_ENTITY.get();
    }

    @Override
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return ModShapes.ENERGY_METER.get(state.getValue(FACING));
    }
}
