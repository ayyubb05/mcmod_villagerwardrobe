package com.pak.villagerwardrobe.block.custom;

import com.mojang.serialization.MapCodec;
import com.pak.villagerwardrobe.block.entity.WardrobeBlockEntity;
import com.pak.villagerwardrobe.item.ModItems;
import com.pak.villagerwardrobe.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class WardrobeBlock extends BaseEntityBlock implements MenuProvider {
  public static final VoxelShape SHAPE = Block.box(2,0,2, 14,13,14);
  public static final MapCodec<WardrobeBlock> CODEC = simpleCodec(WardrobeBlock::new);

  public WardrobeBlock(Properties properties) {
      super(properties);
    }

  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }

  @Override
  protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }


  @Override
  protected RenderShape getRenderShape(BlockState state) {
    return RenderShape.MODEL;
  }

  @Override
  protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
    if(state.getBlock() != newState.getBlock()){
      if(level.getBlockEntity(pos) instanceof WardrobeBlockEntity wardrobeBlockEntity){
        wardrobeBlockEntity.drops();
        level.updateNeighbourForOutputSignal(pos, this);
      }
    }

    super.onRemove(state, level, pos, newState, movedByPiston);
  }

  @Override
  protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
    if(level.getBlockEntity(pos) instanceof WardrobeBlockEntity wardrobeBlockEntity){
      if(wardrobeBlockEntity.inventory.getStackInSlot(0).isEmpty() && !stack.isEmpty() && isValidItem(stack)){
        wardrobeBlockEntity.inventory.insertItem(0, stack.copy(),false);
        stack.shrink(1);
        level.playSound(player, pos, SoundEvents.CHISELED_BOOKSHELF_INSERT, SoundSource.BLOCKS, 1f, 1f);
      } else if (!level.isClientSide() && (stack.isEmpty() || player.isCrouching())){
        ((ServerPlayer) player).openMenu(new SimpleMenuProvider(wardrobeBlockEntity, Component.literal("Wardrobe")),
            pos);
      }
    }
    return ItemInteractionResult.SUCCESS;
  }


  private boolean isValidItem(ItemStack stack){
      return stack.is(ModTags.Items.TRANSFORMABLE_ITEMS);
  }


  @Override
  public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
    tooltipComponents.add(Component.translatable("tooltip.villagerwardrobe.wardrobe_block.tooltip"));
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
  }


  @Override
  public Component getDisplayName() {
    return Component.literal("Wardrobe");
  }


  @Override
  public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
    return null;
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new WardrobeBlockEntity(pos, state);
  }
}