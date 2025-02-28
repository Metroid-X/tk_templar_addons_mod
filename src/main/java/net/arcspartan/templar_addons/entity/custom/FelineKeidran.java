package net.arcspartan.templar_addons.entity.custom;

import net.arcspartan.templar_addons.entity.KeidranBustSizes;
import net.arcspartan.templar_addons.entity.KeidranFelineVariant;
import net.arcspartan.templar_addons.entity.KeidranGender;
import net.arcspartan.templar_addons.entity.ModEntities;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.Map;

public class FelineKeidran extends KeidranEntity{

    public static final int BREEDING_MEAT_THRESHOLD = KeidranEntity.BREEDING_MEAT_THRESHOLD;
    public static final Map<Item, Integer> MEAT_POINTS = KeidranEntity.MEAT_POINTS;

    private int meatLevel;

    private static final EntityDataAccessor<Integer> KIND =
            SynchedEntityData.defineId(FelineKeidran.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> GENDER =
            SynchedEntityData.defineId(FelineKeidran.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> BUST =
            SynchedEntityData.defineId(FelineKeidran.class, EntityDataSerializers.INT);



    public FelineKeidran(EntityType<? extends FelineKeidran> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    private int getTypeKind() {
        return this.entityData.get(KIND);
    }

    private int getTypeGender() {
        return this.entityData.get(GENDER);
    }

    private int getTypeBust() {
        return this.entityData.get(BUST);
    }


    public KeidranFelineVariant getKind() {
        return KeidranFelineVariant.byId(this.getTypeKind() & 255);
    }

    public KeidranGender getGender() {
        return KeidranGender.byId(this.getTypeGender() & 255);
    }

    public KeidranBustSizes getBust() {
        return KeidranBustSizes.byId(this.getTypeBust() & 255);
    }




    private void setKind(KeidranFelineVariant pVariant) {
        this.entityData.set(KIND, pVariant.getId() & 255);
    }

    private void setGender(KeidranGender pGender) {
        this.entityData.set(GENDER, pGender.getId() & 255);
    }

    private void setBust(KeidranBustSizes pBust) {
        this.entityData.set(BUST, pBust.getId() & 255);
    }




    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);

        pBuilder.define(GENDER, 0);
        pBuilder.define(BUST, 0);
        pBuilder.define(KIND, 0);
    }




    @Nullable
    @Override
    public FelineKeidran getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        double d0 = this.random.nextDouble();

        if(!pOtherParent.getEntityData().get(GENDER).equals(this.entityData.get(GENDER))) {

            return ModEntities.KEIDRAN_TIGER.get().create(pLevel,EntitySpawnReason.BREEDING);
        } else {
            return null;
        }
    }



    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Gender", this.getTypeGender());
        pCompound.putInt("Bust", this.getTypeBust());
        pCompound.putInt("Kind", this.getTypeKind());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        if (pCompound.contains("MeatLevel", 1)) {
            this.meatLevel = pCompound.getByte("MeatLevel");
        }

        this.entityData.set(GENDER, pCompound.getInt("Gender"));
        this.entityData.set(BUST, pCompound.getInt("Bust"));
        this.entityData.set(KIND, pCompound.getInt("Kind"));
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
