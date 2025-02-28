package net.arcspartan.templar_addons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.arcspartan.templar_addons.TemplarAddonsMod;
import net.arcspartan.templar_addons.entity.client.state.KeidranRenderState;
import net.arcspartan.templar_addons.entity.custom.KeidranEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KeidranRenderer extends MobRenderer<KeidranEntity, KeidranRenderState, KeidranFelineModel> {


    public KeidranRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new KeidranFelineModel(pContext.bakeLayer(KeidranFelineModel.KEIDRAN)), 0.5F);
    }

    public KeidranRenderState createRenderState() {
        return new KeidranRenderState();
    }

    public void extractRenderState(KeidranEntity entity, KeidranRenderState renderState, float v, PoseStack pPoseStack) {
        super.extractRenderState(entity, renderState, v);
        renderState.jumpCooldown = Math.max((float)entity.getJumpCooldown() - v, 0.0F);
        renderState.idleAnimationState.copyFrom(entity.idleAnimationState);
        renderState.attackAnimationState.copyFrom(entity.attackAnimationState);
        renderState.swimAnimationState.copyFrom(entity.swimAnimationState);
        if(entity.isBaby()) {
            pPoseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            pPoseStack.scale(1.0f, 1.0f, 1.0f);
        }
    }




    @Override
    public ResourceLocation getTextureLocation(KeidranRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath(TemplarAddonsMod.MOD_ID, "textures/entity/keidran_mob/keidran_tiger.png");
    }

}
