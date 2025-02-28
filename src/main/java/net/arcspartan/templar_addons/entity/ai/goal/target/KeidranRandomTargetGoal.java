package net.arcspartan.templar_addons.entity.ai.goal.target;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.jetbrains.annotations.Nullable;

public class KeidranRandomTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    public KeidranRandomTargetGoal(Mob pMob, Class<T> pTargetType, int pInterval, boolean pMustSee, boolean pMustReach, @Nullable TargetingConditions.Selector pSelector) {
        super(pMob, pTargetType, pInterval, pMustSee, pMustReach, pSelector);
    }
}
