package com.pak.villagerwardrobe.block.custom;

import com.pak.villagerwardrobe.item.ModItems;
import com.pak.villagerwardrobe.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;


public class WardrobeBlock extends Block {
    public WardrobeBlock(Properties properties) {
      super(properties);
    }

  @Override
  protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
    level.playSound(player, pos, SoundEvents.WOODEN_DOOR_OPEN, SoundSource.BLOCKS, 1f, 1f);
    return InteractionResult.SUCCESS;
  }

  @Override
  protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
    if (!level.isClientSide) {
      if(stack.getItem() == ModItems.WOODEN_HANGER.get()){
        // Replace the item in hand with the new one
        ItemStack newStack = new ItemStack(ModItems.METAL_HANGER.get(), stack.getCount());
        player.setItemInHand(hand, newStack);

        level.playSound(player, pos, SoundEvents.DYE_USE, SoundSource.BLOCKS, 1f, 1f);
        return ItemInteractionResult.SUCCESS;
      }

      if(stack.getItem() == ModItems.METAL_HANGER.get()){
        // Replace the item in hand with the new one
        ItemStack newStack = new ItemStack(ModItems.WOODEN_HANGER.get(), stack.getCount());
        player.setItemInHand(hand, newStack);

        level.playSound(player, pos, SoundEvents.ARMOR_UNEQUIP_WOLF, SoundSource.BLOCKS, 1f, 1f);
        return ItemInteractionResult.SUCCESS;
      }
    }

    return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
  }

  @Override
  public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
      if(entity instanceof ItemEntity itemEntity){
        if(isValidItem(itemEntity.getItem())){
          itemEntity.setItem(new ItemStack(Items.DIAMOND, itemEntity.getItem().getCount()));
        }
      }
  }

  private boolean isValidItem(ItemStack stack){
      return stack.is(ModTags.Items.TRANSFORMABLE_ITEMS);
  }


  @Override
  public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
    tooltipComponents.add(Component.translatable("tooltip.villagerwardrobe.wardrobe_block.tooltip"));
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
  }
}
//    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
//    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
//
//    public WardrobeBlock(Properties properties) {
//      super(properties);
//      this.registerDefaultState(
//          this.stateDefinition.any()
//              .setValue(FACING, Direction.NORTH)
//              .setValue(HALF, DoubleBlockHalf.LOWER)
//      );
//    }
//
//    @Override
//    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
//      builder.add(FACING, HALF);
//    }
//
//    @Override
//    public BlockState getStateForPlacement(BlockPlaceContext context) {
//      BlockPos pos = context.getClickedPos();
//      Level level = context.getLevel();
//
//      if (pos.getY() < level.getMaxBuildHeight() - 1 &&
//          level.getBlockState(pos.above()).canBeReplaced(context)) {
//
//        return this.defaultBlockState()
//            .setValue(FACING, context.getHorizontalDirection().getOpposite())
//            .setValue(HALF, DoubleBlockHalf.LOWER);
//      }
//
//      return null;
//    }
//
//    @Override
//    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
//      level.setBlock(pos.above(),
//          state.setValue(HALF, DoubleBlockHalf.UPPER),
//          3);
//    }
//
//  @Override
//  public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
//    if (!level.isClientSide) {
//      if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
//        level.destroyBlock(pos.above(), false);
//      } else {
//        level.destroyBlock(pos.below(), false);
//      }
//    }
//    return super.playerWillDestroy(level, pos, state, player);
//  }
//
//    @Override
//    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
//                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
//
//      DoubleBlockHalf half = state.getValue(HALF);
//
//      if (direction == Direction.UP && half == DoubleBlockHalf.LOWER) {
//        if (!neighborState.is(this)) return Blocks.AIR.defaultBlockState();
//      }
//
//      if (direction == Direction.DOWN && half == DoubleBlockHalf.UPPER) {
//        if (!neighborState.is(this)) return Blocks.AIR.defaultBlockState();
//      }
//
//      return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
//    }
//  }