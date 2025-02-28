package net.arcspartan.templar_addons.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.arcspartan.templar_addons.entity.Breeder;
import net.arcspartan.templar_addons.entity.ModEntities;
import net.arcspartan.templar_addons.entity.custom.KeidranEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;

import java.util.Map;
import java.util.Optional;

import static net.arcspartan.templar_addons.entity.ModEntities.ENTITY_TYPES;

public class KeidranMakeLove<T extends KeidranEntity> extends Behavior<T> {
    private long birthTimestamp;


    public KeidranMakeLove() {
        super(ImmutableMap.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT), 350, 350);
    }

    protected boolean checkExtraStartConditions(ServerLevel pLevel, T pOwner) {
        return this.isBreedingPossible(pOwner);
    }



    private boolean isBreedingPossible(T pKeidranEntity) {
        Brain<KeidranEntity> brain = pKeidranEntity.getBrain();
        Optional<AgeableMob> optional = brain.getMemory(MemoryModuleType.BREED_TARGET).filter(ageableMob -> ageableMob.getType() == ModEntities.KEIDRAN_TIGER.get() && ageableMob.entityData.get(GENDER));
        return optional.isEmpty()
                ? false
                : BehaviorUtils.targetIsValid(brain, MemoryModuleType.BREED_TARGET, ModEntities.KEIDRAN_TIGER.get()) && pKeidranEntity.canBreed() && optional.get().canBreed();
    }

    protected void start(ServerLevel pLevel, T pEntity, long pGameTime) {
        AgeableMob ageablemob = pEntity.getBrain().getMemory(MemoryModuleType.BREED_TARGET).get();
        BehaviorUtils.lockGazeAndWalkToEachOther(pEntity, ageablemob, 0.5F, 2);
        pLevel.broadcastEntityEvent(ageablemob, (byte)18);
        pLevel.broadcastEntityEvent(pEntity, (byte)18);
        int i = 275 + pEntity.getRandom().nextInt(50);
        this.birthTimestamp = pGameTime + (long)i;
    }


    public KeidranMakeLove(Map<MemoryModuleType<?>, MemoryStatus> pEntryCondition) {
        super(pEntryCondition);
    }
}
