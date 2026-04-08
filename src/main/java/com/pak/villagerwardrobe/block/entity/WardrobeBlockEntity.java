package com.pak.villagerwardrobe.block.entity;

import com.pak.villagerwardrobe.screen.custom.WardrobeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class WardrobeBlockEntity extends BlockEntity implements MenuProvider {
  public final ItemStackHandler inventory = new ItemStackHandler(1) {
    @Override
    protected int getStackLimit(int slot, ItemStack stack) {
      return 1;
    }

    @Override
    protected void onContentsChanged(int slot) {
      setChanged();
      if(!level.isClientSide()){
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
      }
    }
  };


  public WardrobeBlockEntity(BlockPos pos, BlockState blockState) {
    super(ModBlockEntities.WARDROBE_BE.get(), pos, blockState);
  }


  public void clearContents(){
    inventory.setStackInSlot(0, ItemStack.EMPTY);
  }

  public void drops() {
    SimpleContainer inv = new SimpleContainer(inventory.getSlots());
    for (int i = 0; i < inventory.getSlots(); i++) {
      inv.setItem(i, inventory.getStackInSlot(i));
    }
    Containers.dropContents(this.level, this.worldPosition, inv);
  }

  @Override
  protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
    super.saveAdditional(tag, registries);
    tag.put("inventory", inventory.serializeNBT(registries));
  }

  @Override
  protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
    super.loadAdditional(tag, registries);
    inventory.deserializeNBT(registries, tag.getCompound("inventory "));
  }


  @Override
  public Component getDisplayName() {
    return Component.literal("Wardrobe");
  }

  @Override
  public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
    return new WardrobeMenu(containerId, playerInventory, this);
  }


  @Override
  public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this);
  }

  @Override
  public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
    return saveWithoutMetadata(registries);
  }


}
