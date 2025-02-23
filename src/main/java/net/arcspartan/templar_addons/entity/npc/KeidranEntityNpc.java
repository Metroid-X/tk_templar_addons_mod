package net.arcspartan.templar_addons.entity.npc;

import com.google.common.collect.ImmutableMap;
import net.arcspartan.templar_addons.entity.ModEntities;
import net.arcspartan.templar_addons.util.ModTags;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

public class KeidranEntityNpc extends AbstractVillager implements ReputationEventHandler, VillagerDataHolder, NeutralMob {
    public static final int BREEDING_MEAT_THRESHOLD = 15;
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(KeidranEntityNpc.class, EntityDataSerializers.INT);
    public static final Map<Item, Integer> MEAT_POINTS =
            ImmutableMap.of(
                    Items.PORKCHOP, 4,
                    Items.BEEF, 4,
                    Items.RABBIT, 3,
                    Items.MUTTON, 2,
                    Items.COOKED_PORKCHOP, 8,
                    Items.COOKED_BEEF, 10,
                    Items.COOKED_CHICKEN, 6,
                    Items.COOKED_RABBIT, 5,
                    Items.COOKED_MUTTON, 7
            );

    private int meatLevel;



    public final TargetingConditions.Selector HUNT = (pEntity, pLevel) -> {
        EntityType<?> type = pEntity.getType();
        return type == EntityType.COW || type == EntityType.PIG || type == EntityType.SHEEP;
    };


    private int jumpCooldown = 0;

    private boolean isAttacking;
    private int attackAnimationTick;
    private float attackAnim;

    private boolean isSwimming;
    private int swimAnimationTick;



    private final SimpleContainer inventory = new SimpleContainer(8);


    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder p_332186_) {
        super.defineSynchedData(p_332186_);
        RegistryAccess registryaccess = this.registryAccess();
        Registry<WolfVariant> registry = registryaccess.lookupOrThrow(Registries.WOLF_VARIANT);

        p_332186_.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    @Override
    protected void rewardTradeXp(MerchantOffer pOffer) {

    }


    public KeidranEntityNpc(EntityType<? extends KeidranEntityNpc> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setCanPickUpLoot(true);
        this.applyOpenDoorsAbility();
        this.setPathfindingMalus(PathType.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, -1.0F);
    }

    private void applyOpenDoorsAbility() {
        if (GoalUtils.hasGroundPathNavigation(this)) {
            ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        }
    }


    public boolean isAdult() {
        return !this.isBaby();
    }



    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

//        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
//
//        this.goalSelector.addGoal(3, new FollowParentGoal(this, 0.7D));

        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.5, true));

        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Animal.class, 10, false, false, HUNT));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

    }



    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.FOLLOW_RANGE, 35.0)
                ;
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putByte("MeatLevel", (byte)this.meatLevel);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        if (pCompound.contains("MeatLevel", 1)) {
            this.meatLevel = pCompound.getByte("MeatLevel");
        }

    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }





    @Override
    public boolean canBreed() {
        return this.meatLevel + this.countMeatPointsInInventory() >= 12 && !this.isSleeping() && this.getAge() == 0;
    }

    private boolean hungry() {
        return this.meatLevel < 12;
    }

    private void eatUntilFull() {
        if (this.hungry() && this.countMeatPointsInInventory() != 0) {
            for (int i = 0; i < this.getInventory().getContainerSize(); i++) {
                ItemStack itemstack = this.getInventory().getItem(i);
                if (!itemstack.isEmpty()) {
                    Integer integer = MEAT_POINTS.get(itemstack.getItem());
                    if (integer != null) {
                        int j = itemstack.getCount();

                        for (int k = j; k > 0; k--) {
                            this.meatLevel = this.meatLevel + integer;
                            this.getInventory().removeItem(i, 1);
                            if (!this.hungry()) {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private void digestMeat(int pQty) {
        this.meatLevel -= pQty;
    }

    public void eatAndDigestMeat() {
        this.eatUntilFull();
        this.digestMeat(12);
    }

    @Override
    protected void pickUpItem(ServerLevel pLevel, ItemEntity pEntity) {
        InventoryCarrier.pickUpItem(pLevel, this, this, pEntity);
    }

    @Override
    public boolean wantsToPickUp(ServerLevel pLevel, ItemStack pStack) {
        Item item = pStack.getItem();
        return (pStack.is(ModTags.Items.KEIDRAN_CAN_EAT) || this.getInventory().canAddItem(pStack));
    }

    public boolean hasExcessMeat() {
        return this.countMeatPointsInInventory() >= 24;
    }

    public boolean wantsMoreMeat() {
        return this.countMeatPointsInInventory() < 12;
    }

    private int countMeatPointsInInventory() {
        SimpleContainer simplecontainer = this.getInventory();
        return MEAT_POINTS.entrySet().stream().mapToInt(itemIntegerEntry -> simplecontainer.countItem(itemIntegerEntry.getKey()) * itemIntegerEntry.getValue()).sum();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.CAT_STRAY_AMBIENT;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 300;
    }

    public void hiss() {
        this.makeSound(SoundEvents.CAT_HISS);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.CAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.CAT_DEATH;
    }

    @Nullable
    @Override
    public KeidranEntityNpc getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        double d0 = this.random.nextDouble();
        KeidranType keidrantype;
        if (d0 < 0.5) {
            keidrantype = KeidranType.byBiome(pLevel.getBiome(this.blockPosition()));
        } else if (d0 < 0.75) {
            keidrantype = this.getKeidranData().getType();
        } else {
            keidrantype = ((KeidranEntityNpc)pOtherParent).getKeidranData().getType();
        }

        KeidranEntityNpc keidran = new KeidranEntityNpc(ModEntities.KEIDRAN, pLevel, keidrantype);
        keidran.finalizeSpawn(pLevel, pLevel.getCurrentDifficultyAt(keidran.blockPosition()), EntitySpawnReason.BREEDING, null);
        return keidran;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 8;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int pTime) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, pTime);
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID pTarget) {
        this.persistentAngerTarget = pTarget;
    }

    @Override
    public SimpleContainer getInventory() {
        return this.inventory;
    }

    @Override
    protected void updateTrades() {

    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.attackAnimationTick > 0) {
            this.attackAnimationTick--;
        }

        if (!this.level().isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level(), true);
            if(this.isAlive() && this.getHealth() < this.getMaxHealth()) {
                this.eatAndDigestMeat();
                this.heal(12);
            }
        }
    }

    @Override
    public void handleEntityEvent(byte b) {
        if (b == 4) {
            this.attackAnimationTick = 3;
            this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
        } else {
            super.handleEntityEvent(b);
        }
    }

    public int getAttackAnimationTick() {
        return this.attackAnimationTick;
    }


    @Override
    public boolean canAttackType(EntityType<?> pType) {

        return pType == EntityType.CREEPER ? false : super.canAttackType(pType);

    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            this.idleAnimationTimeout--;
        }

        if(this.isInLiquid()) {
            this.swimAnimationState.start(this.tickCount);
        }


    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    public Object getJumpCooldown() {
        return this.jumpCooldown;
    }

    @Override
    public void onReputationEventFrom(ReputationEventType pType, Entity pTarget) {

    }

    @Override
    public VillagerData getVillagerData() {
        return null;
    }

    @Override
    public void setVillagerData(VillagerData pData) {

    }
}
