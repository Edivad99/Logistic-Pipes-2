package logisticspipes.world.level.block;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;

import org.jspecify.annotations.Nullable;

import logisticspipes.interfaces.IRotationProvider;
import logisticspipes.interfaces.ITickable;
import logisticspipes.world.level.block.entity.LogisticsCraftingTableBlockEntity;
import logisticspipes.world.level.block.entity.LogisticsSolidBlockEntity;

public abstract class LogisticsSolidBlock extends Block implements EntityBlock {

    public static final IntegerProperty rotationProperty = IntegerProperty.create("rotation", 0, 3);
    public static final BooleanProperty active = BooleanProperty.create("active");
    public static final Map<Direction, BooleanProperty> connectionProperties = Arrays.stream(Direction.values())
        .collect(Collectors.toMap(key -> key, key -> BooleanProperty.create("connection_" + key.ordinal())));

    protected LogisticsSolidBlock(Properties properties) {
        // noOcclusion() is required so the BER receives a non-zero packedLight value.
        // Without it Minecraft treats the block as fully opaque, stores sky-light = 0
        // at its own position, and the BER renders pitch-black regardless of ambient light.
        super(properties.strength(6.0F).requiresCorrectToolForDrops().noOcclusion());
    }

    /** The name under {@code textures/block/solid_block/} this block is drawn with. */
    public abstract String textureName();

    /** Whether an {@code _active} variant of that texture exists. */
    public boolean hasActiveTexture() {
        return false;
    }

    /** Whether the cover plates are drawn around the core. Only the frame goes without. */
    public boolean hasCoverPlates() {
        return true;
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block,
        @Nullable Orientation orientation, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, orientation, isMoving);
        if (level.getBlockEntity(pos) instanceof LogisticsSolidBlockEntity logisticsSolidBlockEntity) {
            logisticsSolidBlockEntity.notifyOfBlockChange();
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
        BlockHitResult hitResult) {
        if (!player.isCrouching()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof MenuProvider menuProvider) {
                if (player instanceof ServerPlayer serverPlayer) {
                    serverPlayer.openMenu(menuProvider);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer,
        ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof LogisticsCraftingTableBlockEntity craftingTableBlockEntity) {
            craftingTableBlockEntity.placedBy(placer);
        }
        if (placer != null && be instanceof IRotationProvider rotationProvider) {
            rotationProvider.setFacing(placer.getDirection().getOpposite());
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
        BlockEntityType<T> type) {
        // Tick all ITickable solid block entities
        return (lvl, pos, st, be) -> {
            if (be instanceof ITickable tickable) {
                tickable.update();
            }
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(rotationProperty);
        builder.add(active);
        connectionProperties.values().forEach(builder::add);
    }
}
