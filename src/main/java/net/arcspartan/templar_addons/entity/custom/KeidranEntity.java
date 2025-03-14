package net.arcspartan.templar_addons.entity.custom;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.arcspartan.templar_addons.entity.KeidranBustSizes;
import net.arcspartan.templar_addons.entity.KeidranGender;
import net.arcspartan.templar_addons.entity.ModEntities;
import net.arcspartan.templar_addons.util.ModTags;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;

import javax.annotation.Nullable;

import java.util.Map;
import java.util.UUID;

public class KeidranEntity extends AgeableMob implements NeutralMob, InventoryCarrier {



    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(KeidranEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> GENDER =
            SynchedEntityData.defineId(KeidranEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> BUST =
            SynchedEntityData.defineId(KeidranEntity.class, EntityDataSerializers.INT);

    public static final int BREEDING_MEAT_THRESHOLD = 15;
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(KeidranEntity.class, EntityDataSerializers.INT);
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


    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.HOME,
            MemoryModuleType.JOB_SITE,
            MemoryModuleType.POTENTIAL_JOB_SITE,
            MemoryModuleType.MEETING_POINT,
            MemoryModuleType.NEAREST_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_PLAYERS,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
            MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.INTERACTION_TARGET,
            MemoryModuleType.BREED_TARGET,
            MemoryModuleType.PATH,
            MemoryModuleType.DOORS_TO_CLOSE,
            MemoryModuleType.NEAREST_BED,
            MemoryModuleType.LAST_WORKED_AT_POI
    );
    private static final ImmutableList<SensorType<? extends Sensor<? super KeidranEntity>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_ITEMS,
            SensorType.NEAREST_BED,
            SensorType.HURT_BY
    );

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







    public KeidranEntity(EntityType<? extends KeidranEntity> pEntityType, Level pLevel) {
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

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }





    @Override
    public boolean canBreed() {
        return this.meatLevel + this.countMeatPointsInInventory() >= 12 && !this.isSleeping() && this.getAge() == 0 ;
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
    public KeidranEntity getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        double d0 = this.random.nextDouble();

        return ModEntities.KEIDRAN_TIGER.get().create(pLevel,EntitySpawnReason.BREEDING);
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
    public Brain<KeidranEntity> getBrain() {
        return (Brain<KeidranEntity>)super.getBrain();
    }
    @Override
    protected Brain.Provider<KeidranEntity> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> pDynamic) {
        Brain<KeidranEntity> brain = this.brainProvider().makeBrain(pDynamic);
        this.registerBrainGoals(brain);
        return brain;
    }

    public void refreshBrain(ServerLevel pServerLevel) {
        Brain<KeidranEntity> brain = this.getBrain();
        brain.stopAll(pServerLevel, this);
        this.brain = brain.copyWithoutBehaviors();
        this.registerBrainGoals(this.getBrain());
    }


    private void registerBrainGoals(Brain<KeidranEntity> pVillagerBrain) {
        if (this.isBaby()) {
            pVillagerBrain.setSchedule(Schedule.VILLAGER_BABY);
        } else {
            pVillagerBrain.setSchedule(Schedule.VILLAGER_DEFAULT);
        }
    }




//  Variants


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);

        pBuilder.define(GENDER, 0);
        pBuilder.define(BUST, 0);
        pBuilder.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    private int getTypeGender() {
        return this.entityData.get(GENDER);
    }

    public KeidranGender getGender() {
        return KeidranGender.byId(this.getTypeGender() & 255);
    }

    private void setGender(KeidranGender pGender) {
        this.entityData.set(GENDER, pGender.getId() & 255);
    }

    private int getTypeBust() {
        return this.entityData.get(BUST);
    }

    public KeidranBustSizes getBust() {
        return KeidranBustSizes.byId(this.getTypeBust() & 255);
    }

    private void setBust(KeidranBustSizes pBust) {
        this.entityData.set(BUST, pBust.getId() & 255);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Gender", this.getTypeGender());
        pCompound.putInt("Bust", this.getTypeBust());
        pCompound.putByte("MeatLevel", (byte)this.meatLevel);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        if (pCompound.contains("MeatLevel", 1)) {
            this.meatLevel = pCompound.getByte("MeatLevel");
        }

        this.entityData.set(GENDER, pCompound.getInt("Gender"));
        this.entityData.set(BUST, pCompound.getInt("Bust"));
    }


    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
                                        EntitySpawnReason pSpawnReason, @Nullable SpawnGroupData pSpawnGroupData) {
        KeidranGender gender = Util.getRandom(KeidranGender.values(), this.random);
        this.setGender(gender);
        if(this.getGender() != KeidranGender.MALE) {
            KeidranBustSizes bustSize = Util.getRandom(KeidranBustSizes.values(), this.random);
            this.setBust(bustSize);
        } else {
            this.setBust(KeidranBustSizes.FLAT);
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnReason, pSpawnGroupData);
    }

}
