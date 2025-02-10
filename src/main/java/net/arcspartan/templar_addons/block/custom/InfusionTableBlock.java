package net.arcspartan.templar_addons.block.custom;

import com.mojang.serialization.MapCodec;
import net.arcspartan.templar_addons.block.ModBlocks;
import net.arcspartan.templar_addons.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.coremod.api.ASMAPI;
import net.minecraftforge.unsafe.UnsafeFieldAccess;

import javax.annotation.Nullable;
import java.util.Map;


public class InfusionTableBlock extends Block {







    public static final VoxelShape SHAPE_BASE = Shapes.or(
            Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),

            Block.box(0.0, 0.10, 0.0, 16.0, 13.0, 16.0),

            Block.box(0.0, 0.2, 0.0, 7.0, 10.0, 7.0),
            Block.box(9.0, 0.2, 0.0, 16.0, 10.0, 7.0),
            Block.box(0.0, 0.2, 9.0, 7.0, 10.0, 16.0),
            Block.box(9.0, 0.2, 9.0, 16.0, 10.0, 16.0)
    );
    public static final VoxelShape SHAPE_X_SLOPE_LOW = Block.box(1.0, 13.0, 3.0, 15.0, 14.0, 13.0);
    public static final VoxelShape SHAPE_Z_SLOPE_LOW = Block.box(3.0, 13.0, 1.0, 13.0, 14.0, 15.0);
    public static final VoxelShape SHAPE_X_SLOPE_MID = Block.box(2.0, 14.0, 3.0, 14.0, 15.0, 13.0);
    public static final VoxelShape SHAPE_Z_SLOPE_MID = Block.box(3.0, 14.0, 2.0, 13.0, 15.0, 14.0);
    public static final VoxelShape SHAPE_SLOPE_TOP = Block.box(3.0, 13.0, 3.0, 13.0, 16.0, 13.0);
    public static final VoxelShape SHAPE_TOP_PLATE = Shapes.or(
            SHAPE_X_SLOPE_LOW,
            SHAPE_Z_SLOPE_LOW,
            SHAPE_X_SLOPE_MID,
            SHAPE_Z_SLOPE_MID,
            SHAPE_SLOPE_TOP
            );

    public static final VoxelShape SHAPE_COMMON = Shapes.or(
            SHAPE_BASE,
            SHAPE_SLOPE_TOP
    );


    public static final VoxelShape SHAPE_COLLISION = Shapes.or(
            SHAPE_BASE,
            SHAPE_TOP_PLATE
    );

    public static final int MIN_CHARGES = 0;
    public static final int MAX_CHARGES = 64;
    public static final IntegerProperty CHARGE = ModBlockStateProperties.INFUSION_TABLE_CHARGES;


    @Override
    public MapCodec<InfusionTableBlock> codec() {
        return CODEC;
    }

    public static final MapCodec<InfusionTableBlock> CODEC = simpleCodec(InfusionTableBlock::new);


    public InfusionTableBlock(BlockBehaviour.Properties properties) {

        super(properties);
    }





    @Override
    protected VoxelShape getOcclusionShape(BlockState pState) {
        return SHAPE_COMMON;
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter blockGetter, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_COLLISION;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_COLLISION;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {

        if(canBeCharged(pState) && isAnyMana(pStack)) {
            if(isRawMana(pStack)) {
                chargeRawMana(pPlayer, pLevel, pPos, pState);
            } else if (isCrudeMana(pStack)) {
                chargeCrudeMana(pPlayer, pLevel, pPos, pState);
            } else if (isRefinedMana(pStack)) {
                chargeRefinedMana(pPlayer, pLevel, pPos, pState);
            }
            pStack.consume(1, pPlayer);
            return InteractionResult.SUCCESS;
        } else {
            return (InteractionResult) (pHand == InteractionHand.MAIN_HAND  && isAnyMana(pPlayer.getItemInHand(InteractionHand.OFF_HAND)) && canBeCharged(pState)
                    ? InteractionResult.PASS
                    : InteractionResult.TRY_WITH_EMPTY_HAND);
        }


    }

    private static boolean isRawMana(ItemStack pStack) {
        return pStack.is(ModItems.RAW_MANA_CRYSTAL.get());
    }

    private static boolean isCrudeMana(ItemStack pStack) {
        return pStack.is(ModItems.CRUDE_MANA_CRYSTAL.get());
    }

    private static boolean isRefinedMana(ItemStack pStack) {
        return pStack.is(ModItems.MANA_CRYSTAL.get());
    }

    private static boolean  isAnyMana(ItemStack pStack) {
        return (isRawMana(pStack)||isCrudeMana(pStack)||isRefinedMana(pStack));
    }


    private static boolean canBeCharged(BlockState pState) {
        return pState.getValue(CHARGE) < 64;
    }

    public static void chargeRawMana(@Nullable Entity pEntity, Level pLevel, BlockPos pPos, BlockState pState) {
        BlockState blockstate = pState.setValue(CHARGE, Integer.valueOf(pState.getValue(CHARGE) + 1));
        pLevel.setBlock(pPos, blockstate, 3);
        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pEntity, blockstate));
        pLevel.playSound(
                null,
                (double)pPos.getX() + 0.5,
                (double)pPos.getY() + 0.5,
                (double)pPos.getZ() + 0.5,
                SoundEvents.END_PORTAL_FRAME_FILL,
                SoundSource.BLOCKS,
                1.0F,
                1.0F
        );
    }

    public static void chargeCrudeMana(@Nullable Entity pEntity, Level pLevel, BlockPos pPos, BlockState pState) {

        if(pState.getValue(CHARGE)+4 > 64) {
            BlockState blockstate = pState.setValue(CHARGE, 64);
            pLevel.setBlock(pPos, blockstate, 3);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pEntity, blockstate));
        } else {
            BlockState blockstate = pState.setValue(CHARGE, Integer.valueOf(pState.getValue(CHARGE) + 4));
            pLevel.setBlock(pPos, blockstate, 3);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pEntity, blockstate));
        }

        pLevel.playSound(
                null,
                (double)pPos.getX() + 0.5,
                (double)pPos.getY() + 0.5,
                (double)pPos.getZ() + 0.5,
                SoundEvents.END_PORTAL_FRAME_FILL,
                SoundSource.BLOCKS,
                1.0F,
                1.0F
        );
    }

    public static void chargeRefinedMana(@Nullable Entity pEntity, Level pLevel, BlockPos pPos, BlockState pState) {

        if(pState.getValue(CHARGE)+8 > 64) {
            BlockState blockstate = pState.setValue(CHARGE, 64);
            pLevel.setBlock(pPos, blockstate, 3);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pEntity, blockstate));
        } else {
            BlockState blockstate = pState.setValue(CHARGE, Integer.valueOf(pState.getValue(CHARGE) + 8));
            pLevel.setBlock(pPos, blockstate, 3);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pEntity, blockstate));
        }


        pLevel.playSound(
                null,
                (double)pPos.getX() + 0.5,
                (double)pPos.getY() + 0.5,
                (double)pPos.getZ() + 0.5,
                SoundEvents.END_PORTAL_FRAME_FILL,
                SoundSource.BLOCKS,
                1.0F,
                1.0F
        );
    }




    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(CHARGE) != 0) {
            if (pRandom.nextInt(100) == 0) {
                pLevel.playLocalSound(pPos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            double d0 = (double)pPos.getX() + 0.5 + (0.5 - pRandom.nextDouble());
            double d1 = (double)pPos.getY() + 1.0;
            double d2 = (double)pPos.getZ() + 0.5 + (0.5 - pRandom.nextDouble());
            double d3 = (double)pRandom.nextFloat() * 0.04;
            pLevel.addParticle(ParticleTypes.GLOW, d0, d1, d2, 0.0, d3, 0.0);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(CHARGE);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    public static int getScaledChargeLevel(BlockState pState, int pScale) {
        return Mth.floor((float)(pState.getValue(CHARGE) - 1) / 64.0F * (float)pScale + 1.0F);
    }

    @Override
    protected int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        return getScaledChargeLevel(pBlockState, 15);
    }



}
