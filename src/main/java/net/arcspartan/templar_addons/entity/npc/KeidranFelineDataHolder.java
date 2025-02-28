package net.arcspartan.templar_addons.entity.npc;

import net.arcspartan.templar_addons.entity.npc.KeidranFelineType;
import net.minecraft.world.entity.VariantHolder;

public interface KeidranFelineDataHolder extends VariantHolder<KeidranFelineType> {
    KeidranFelineData getKeidranFelineData();

    void setKeidranFelineData(KeidranFelineData pData);

    default KeidranFelineType getVariant() {
        return this.getKeidranFelineData().getType();
    }

    default void setVariant(KeidranFelineType p_262647_) {
        this.setKeidranFelineData(this.getKeidranFelineData().setType(p_262647_));
    }

}
