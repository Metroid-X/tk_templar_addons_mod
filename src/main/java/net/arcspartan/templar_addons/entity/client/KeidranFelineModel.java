package net.arcspartan.templar_addons.entity.client;

import net.arcspartan.templar_addons.TemplarAddonsMod;
import net.arcspartan.templar_addons.entity.client.state.KeidranRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class KeidranFelineModel extends EntityModel<KeidranRenderState> {

    private static final float MAX_WALK_ANIMATION_SPEED = 2.0F;
    private static final float WALK_ANIMATION_SCALE_FACTOR = 2.5F;
    public static final MeshTransformer BABY_TRANSFORMER = MeshTransformer.scaling(0.45F);

    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation KEIDRAN =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(TemplarAddonsMod.MOD_ID, "keidran"), "main");




    private final ModelPart body;
    private final ModelPart head;

    public KeidranFelineModel(ModelPart pRoot) {
        super(pRoot);
        this.body = pRoot.getChild("body");
        this.head =
                body.getChild("lower")
                .getChild("middle")
                .getChild("upper")
                .getChild("neck")
                .getChild("head")
        ;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition lower = body.addOrReplaceChild("lower", CubeListBuilder.create().texOffs(29, 17).addBox(-4.0F, -3.75F, -2.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(-0.125F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition middle = lower.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(29, 25).addBox(-3.5F, -4.375F, -2.0F, 7.0F, 5.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, -3.5F, 0.0F));

        PartDefinition upper = middle.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(55, 33).addBox(2.875F, -4.575F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(-0.125F))
                .texOffs(55, 33).mirror().addBox(-3.875F, -4.575F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(-0.125F)).mirror(false)
                .texOffs(0, 32).addBox(-3.5F, -4.475F, -2.0F, 7.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition neck = upper.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(38, 44).addBox(-2.0F, -1.75F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, -4.5F, 0.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 17).addBox(-3.5F, -7.0F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -7.5F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition muzzleC_r1 = head.addOrReplaceChild("muzzleC_r1", CubeListBuilder.create().texOffs(20, 66).addBox(-1.5F, -0.0758F, 0.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.6992F, -4.8471F, 0.1745F, 0.0F, 0.0F));

        PartDefinition muzzleR_r1 = head.addOrReplaceChild("muzzleR_r1", CubeListBuilder.create().texOffs(40, 67).addBox(-2.0F, -0.0658F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -1.6992F, -4.8471F, 0.2444F, 0.7703F, 0.1719F));

        PartDefinition muzzleL_r1 = head.addOrReplaceChild("muzzleL_r1", CubeListBuilder.create().texOffs(40, 67).mirror().addBox(0.0F, -0.0658F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, -1.6992F, -4.8471F, 0.2444F, -0.7703F, -0.1719F));

        PartDefinition earL = head.addOrReplaceChild("earL", CubeListBuilder.create().texOffs(68, 46).addBox(0.0F, -4.0F, -0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.005F))
                .texOffs(17, 42).addBox(1.0F, -2.0F, 0.75F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.005F))
                .texOffs(67, 30).addBox(-1.0F, 1.0F, 0.75F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.005F))
                .texOffs(55, 49).addBox(0.0F, -3.0F, 0.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.005F))
                .texOffs(62, 54).addBox(-1.0F, -2.0F, 0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.005F))
                .texOffs(23, 32).addBox(-1.0F, -3.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.005F))
                .texOffs(68, 40).addBox(-2.0F, -2.0F, -0.25F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.005F))
                .texOffs(26, 56).addBox(-2.0F, -1.0F, 0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.005F))
                .texOffs(56, 70).addBox(-2.0F, -1.0F, 1.75F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.005F))
                .texOffs(67, 63).addBox(2.0F, -4.0F, -0.25F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.005F))
                .texOffs(68, 70).addBox(1.0F, 1.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.005F))
                .texOffs(54, 8).addBox(-2.0F, 2.0F, -0.25F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.005F))
                .texOffs(32, 47).addBox(0.0F, -2.0F, 1.75F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(4.2463F, -5.0F, 0.5071F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r1 = earL.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(49, 67).addBox(-3.0F, -4.0F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(-0.9926F, 1.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition earR = head.addOrReplaceChild("earR", CubeListBuilder.create().texOffs(68, 46).mirror().addBox(-2.0F, -4.0F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false)
                .texOffs(17, 42).mirror().addBox(-2.0F, -2.0F, 0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false)
                .texOffs(67, 30).mirror().addBox(-2.0F, 1.0F, 0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false)
                .texOffs(55, 49).mirror().addBox(-2.0F, -3.0F, 0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false)
                .texOffs(62, 54).mirror().addBox(0.0F, -2.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false)
                .texOffs(23, 32).mirror().addBox(0.0F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false)
                .texOffs(49, 67).addBox(-2.0F, -3.0F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.005F))
                .texOffs(68, 40).mirror().addBox(1.0F, -2.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false)
                .texOffs(26, 56).mirror().addBox(1.0F, -1.0F, 0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false)
                .texOffs(56, 70).mirror().addBox(0.0F, -1.0F, 1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false)
                .texOffs(32, 47).mirror().addBox(-1.0F, -2.0F, 1.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false)
                .texOffs(67, 63).mirror().addBox(-3.0F, -4.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false)
                .texOffs(68, 70).mirror().addBox(-2.0F, 1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false)
                .texOffs(54, 8).mirror().addBox(-1.0F, 2.0F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-3.9963F, -5.0F, 0.5071F, 0.0F, 1.5708F, 0.0F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition type1 = hair.addOrReplaceChild("type1", CubeListBuilder.create().texOffs(33, 11).addBox(-3.5F, 0.0F, -0.5F, 7.0F, 4.0F, 1.0F, new CubeDeformation(0.125F))
                .texOffs(47, 52).addBox(-3.0F, 4.0F, -0.5F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.25F));

        PartDefinition type2 = hair.addOrReplaceChild("type2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.25F));

        PartDefinition cube_r2 = type2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 69).addBox(-0.5F, 1.1F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, 1.0F, -0.25F, 0.2444F, -0.7703F, -0.1719F));

        PartDefinition cube_r3 = type2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(66, 33).addBox(-1.0F, 1.475F, -0.6F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.5F))
                .texOffs(67, 20).addBox(-1.0F, 1.875F, -1.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(66, 33).addBox(-1.0F, 1.6F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.375F)), PartPose.offsetAndRotation(0.0F, 1.0F, -0.25F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r4 = type2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 69).addBox(-0.5F, 1.1F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, 1.0F, -0.25F, 1.5708F, 1.3963F, 1.5708F));

        PartDefinition cube_r5 = type2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 69).addBox(-0.5F, 1.1F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, 1.0F, -0.25F, 0.2444F, 0.7703F, 0.1719F));

        PartDefinition cube_r6 = type2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 69).addBox(-0.5F, 1.1F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.125F))
                .texOffs(0, 58).addBox(-2.0F, -1.775F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(50, 11).addBox(-3.0F, -2.25F, -0.75F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -0.25F, 0.1745F, 0.0F, 0.0F));

        PartDefinition armL = upper.addOrReplaceChild("armL", CubeListBuilder.create().texOffs(17, 47).addBox(-0.25F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(-0.25F))
                .texOffs(23, 35).addBox(0.05F, -1.625F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(-0.375F)), PartPose.offset(3.75F, -2.5F, 0.0F));

        PartDefinition elbowL_r1 = armL.addOrReplaceChild("elbowL_r1", CubeListBuilder.create().texOffs(67, 54).addBox(-1.2F, -2.175F, 0.6F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 4.05F, -2.2F, -0.9817F, 0.0F, 0.0F));

        PartDefinition forearmL = armL.addOrReplaceChild("forearmL", CubeListBuilder.create().texOffs(32, 52).addBox(-1.5F, -0.1F, -1.5F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.25F))
                .texOffs(52, 25).addBox(-1.5F, 1.5F, -1.5F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(1.55F, 4.0F, -0.5F, 0.0F, 0.0F, 0.0F));

        PartDefinition wristL = forearmL.addOrReplaceChild("wristL", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.5F));

        PartDefinition thumbL_r1 = wristL.addOrReplaceChild("thumbL_r1", CubeListBuilder.create().texOffs(54, 0).addBox(-1.575F, -0.625F, -2.25F, 2.0F, 3.0F, 4.0F, new CubeDeformation(-0.625F)), PartPose.offsetAndRotation(-0.2F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition handL = wristL.addOrReplaceChild("handL", CubeListBuilder.create().texOffs(0, 50).addBox(-1.15F, -0.5F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.5F)), PartPose.offset(-0.2F, 0.0F, 0.0F));

        PartDefinition clawsL = wristL.addOrReplaceChild("clawsL", CubeListBuilder.create().texOffs(33, 0).addBox(-2.8F, -2.0F, -2.375F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-2.0F))
                .texOffs(33, 0).addBox(-2.8F, -2.0F, -1.625F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-2.0F))
                .texOffs(33, 0).addBox(-2.8F, -2.0F, -0.875F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-2.0F))
                .texOffs(33, 0).addBox(-2.8F, -2.0F, -3.125F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-2.0F)), PartPose.offset(-0.05F, 0.0F, 0.25F));

        PartDefinition cube_r7 = clawsL.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(33, 0).addBox(-2.5F, -2.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-2.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, -0.375F, 0.0F, 1.5708F, 0.0F));

        PartDefinition armR = upper.addOrReplaceChild("armR", CubeListBuilder.create().texOffs(17, 47).mirror().addBox(-2.75F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offset(-3.75F, -2.5F, 0.0F));

        PartDefinition elbowR_r1 = armR.addOrReplaceChild("elbowR_r1", CubeListBuilder.create().texOffs(67, 54).addBox(-1.2F, -2.175F, 0.6F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.35F, 4.05F, -2.2F, -0.9817F, 0.0F, 0.0F));

        PartDefinition armR3_r1 = armR.addOrReplaceChild("armR3_r1", CubeListBuilder.create().texOffs(23, 35).mirror().addBox(-2.8F, -1.625F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(-0.375F)).mirror(false), PartPose.offsetAndRotation(-0.25F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition forearmR2 = armR.addOrReplaceChild("forearmR2", CubeListBuilder.create().texOffs(32, 52).mirror().addBox(-1.5F, -0.1F, -1.5F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.25F)).mirror(false)
                .texOffs(52, 25).mirror().addBox(-1.5F, 1.5F, -1.5F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offsetAndRotation(-1.55F, 4.0F, -0.5F, 0.0F, 0.0F, 0.0F));

        PartDefinition wristR = forearmR2.addOrReplaceChild("wristR", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.5F));

        PartDefinition thumbR_r1 = wristR.addOrReplaceChild("thumbR_r1", CubeListBuilder.create().texOffs(54, 0).mirror().addBox(-0.025F, -0.625F, -2.25F, 2.0F, 3.0F, 4.0F, new CubeDeformation(-0.625F)).mirror(false), PartPose.offsetAndRotation(-0.2F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition handR = wristR.addOrReplaceChild("handR", CubeListBuilder.create(), PartPose.offset(0.3F, 0.25F, 0.0F));

        PartDefinition handR_r1 = handR.addOrReplaceChild("handR_r1", CubeListBuilder.create().texOffs(0, 50).mirror().addBox(-1.45F, -0.5F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offsetAndRotation(-0.5F, -0.25F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition clawsR = wristR.addOrReplaceChild("clawsR", CubeListBuilder.create(), PartPose.offset(0.55F, 0.0F, 0.25F));

        PartDefinition cube_r8 = clawsR.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(33, 0).addBox(-2.5F, -2.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-2.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.375F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r9 = clawsR.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(33, 0).addBox(-2.5F, -2.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-2.0F)), PartPose.offsetAndRotation(-0.2F, 0.0F, -1.125F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r10 = clawsR.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(33, 0).addBox(-2.5F, -2.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-2.0F)), PartPose.offsetAndRotation(-0.2F, 0.0F, 1.125F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r11 = clawsR.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(33, 0).addBox(-2.5F, -2.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-2.0F)), PartPose.offsetAndRotation(-0.2F, 0.0F, 0.375F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r12 = clawsR.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(33, 0).addBox(-2.5F, -2.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-2.0F)), PartPose.offsetAndRotation(-0.2F, 0.0F, -0.375F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bust = upper.addOrReplaceChild("bust", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.0F, -1.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition cube_r13 = bust.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(67, 12).addBox(0.0231F, -1.6237F, -2.4347F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.9F, -1.0F, -0.3572F, 0.9719F, -0.0082F));

        PartDefinition cube_r14 = bust.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(67, 12).addBox(-0.0231F, -1.6237F, -2.4347F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.9F, -1.0F, -0.3572F, -0.9719F, 0.0082F));

        PartDefinition cube_r15 = bust.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(55, 42).mirror().addBox(0.1F, 0.0F, -0.975F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.2F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0436F));

        PartDefinition cube_r16 = bust.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(55, 42).addBox(-3.1F, 0.0F, -0.975F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0436F));

        PartDefinition legL = lower.addOrReplaceChild("legL", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -0.5F, 0.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition thighL5_r1 = legL.addOrReplaceChild("thighL5_r1", CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-1.5F, -1.0F, -1.875F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.13F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.65F, 0.4F, 1.2392F, 0.0F, 0.0F));

        PartDefinition thighL4_r1 = legL.addOrReplaceChild("thighL4_r1", CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-1.5F, -1.0F, -1.875F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.135F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.6F, 0.35F, 0.3491F, 0.0F, 0.0F));

        PartDefinition thighL2_r1 = legL.addOrReplaceChild("thighL2_r1", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-2.0F, -1.15F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(-0.375F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.425F, -0.125F, 0.0F, 0.0F, 0.0F));

        PartDefinition thighL1_r1 = legL.addOrReplaceChild("thighL1_r1", CubeListBuilder.create().texOffs(38, 35).mirror().addBox(-0.5F, -4.75F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 3.4F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition shinL = legL.addOrReplaceChild("shinL", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.65F, 0.4F, 1.2217F, 0.0F, 0.0F));

        PartDefinition shinL4_r1 = shinL.addOrReplaceChild("shinL4_r1", CubeListBuilder.create().texOffs(56, 58).mirror().addBox(-1.5F, -0.5F, -1.5F, 3.0F, 4.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.2358F, 0.9009F, -0.3054F, 0.0F, 0.0F));

        PartDefinition shinL3_r1 = shinL.addOrReplaceChild("shinL3_r1", CubeListBuilder.create().texOffs(67, 0).mirror().addBox(-0.5F, -2.625F, -0.375F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.375F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 5.05F, -1.525F, 0.0873F, 0.0F, 0.0F));

        PartDefinition shinL2_r1 = shinL.addOrReplaceChild("shinL2_r1", CubeListBuilder.create().texOffs(26, 60).mirror().addBox(-1.5F, 2.375F, -1.625F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.375F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.025F, -0.25F, 0.0087F, 0.0F, 0.0F));

        PartDefinition shinL1_r1 = shinL.addOrReplaceChild("shinL1_r1", CubeListBuilder.create().texOffs(54, 16).mirror().addBox(-1.5F, -0.125F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.125F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.05F, -0.6F, 0.0873F, 0.0F, 0.0F));

        PartDefinition footL = shinL.addOrReplaceChild("footL", CubeListBuilder.create().texOffs(15, 56).mirror().addBox(-1.5F, -1.125F, -1.0F, 3.0F, 6.0F, 2.0F, new CubeDeformation(-0.13F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.575F, -0.75F, -1.309F, 0.0F, 0.0F));

        PartDefinition pawL_bp = footL.addOrReplaceChild("pawL_bp", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.75F, 0.875F, 0.48F, 0.0F, 0.0F));

        PartDefinition pawL_tp = pawL_bp.addOrReplaceChild("pawL_tp", CubeListBuilder.create().texOffs(62, 49).mirror().addBox(-1.5F, -0.13F, -1.875F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.125F)).mirror(false)
                .texOffs(56, 65).mirror().addBox(-1.5F, -0.155F, -1.85F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offset(0.0F, -1.75F, 0.0F));

        PartDefinition toebeanL4_r1 = pawL_tp.addOrReplaceChild("toebeanL4_r1", CubeListBuilder.create().texOffs(63, 70).mirror().addBox(0.525F, -0.65F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.725F, -1.425F, 0.0F, -0.1745F, 0.0F));

        PartDefinition toebeanL3_r1 = pawL_tp.addOrReplaceChild("toebeanL3_r1", CubeListBuilder.create().texOffs(63, 70).mirror().addBox(-0.2F, -0.65F, -1.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.725F, -1.425F, 0.0F, -0.0873F, 0.0F));

        PartDefinition toebeanL2_r1 = pawL_tp.addOrReplaceChild("toebeanL2_r1", CubeListBuilder.create().texOffs(63, 70).mirror().addBox(-0.8F, -0.65F, -1.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.725F, -1.425F, 0.0F, 0.0873F, 0.0F));

        PartDefinition toebeanL1_r1 = pawL_tp.addOrReplaceChild("toebeanL1_r1", CubeListBuilder.create().texOffs(63, 70).mirror().addBox(-1.525F, -0.65F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.725F, -1.425F, 0.0F, 0.1745F, 0.0F));

        PartDefinition pawpadL3_r1 = pawL_tp.addOrReplaceChild("pawpadL3_r1", CubeListBuilder.create().texOffs(63, 70).mirror().addBox(-0.5F, -0.925F, -0.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.7F, -1.425F, 0.0698F, 0.0F, 0.0F));

        PartDefinition pawpadL2_r1 = pawL_tp.addOrReplaceChild("pawpadL2_r1", CubeListBuilder.create().texOffs(67, 59).mirror().addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.5F, 1.55F, -1.025F, 0.0F, -0.1745F, 0.0436F));

        PartDefinition pawpadL1_r1 = pawL_tp.addOrReplaceChild("pawpadL1_r1", CubeListBuilder.create().texOffs(67, 59).mirror().addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 1.55F, -1.025F, 0.0F, 0.1745F, -0.0436F));

        PartDefinition pawL2_r1 = pawL_tp.addOrReplaceChild("pawL2_r1", CubeListBuilder.create().texOffs(31, 67).mirror().addBox(-0.125F, -1.8775F, -0.125F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.125F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.75F, -2.925F, 0.0F, -0.24F, 0.0F));

        PartDefinition pawL1_r1 = pawL_tp.addOrReplaceChild("pawL1_r1", CubeListBuilder.create().texOffs(31, 67).mirror().addBox(-1.875F, -1.875F, -0.125F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.125F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.75F, -2.925F, 0.0F, 0.24F, 0.0F));

        PartDefinition legR = lower.addOrReplaceChild("legR", CubeListBuilder.create().texOffs(0, 42).addBox(-2.0F, 1.275F, -2.125F, 4.0F, 3.0F, 4.0F, new CubeDeformation(-0.375F)), PartPose.offsetAndRotation(-2.0F, -0.5F, 0.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition thighR5_r1 = legR.addOrReplaceChild("thighR5_r1", CubeListBuilder.create().texOffs(0, 64).addBox(-1.5F, -1.0F, -1.875F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.13F)), PartPose.offsetAndRotation(0.0F, 3.65F, 0.4F, 1.2392F, 0.0F, 0.0F));

        PartDefinition thighR4_r1 = legR.addOrReplaceChild("thighR4_r1", CubeListBuilder.create().texOffs(0, 64).addBox(-1.5F, -1.0F, -1.875F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.135F)), PartPose.offsetAndRotation(0.0F, 3.6F, 0.35F, 0.3491F, 0.0F, 0.0F));

        PartDefinition thighR1_r1 = legR.addOrReplaceChild("thighR1_r1", CubeListBuilder.create().texOffs(38, 35).addBox(-0.5F, -4.75F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.5F, 3.4F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition shinR = legR.addOrReplaceChild("shinR", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.65F, 0.4F, 1.2217F, 0.0F, 0.0F));

        PartDefinition shinR4_r1 = shinR.addOrReplaceChild("shinR4_r1", CubeListBuilder.create().texOffs(56, 58).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 4.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, 2.2358F, 0.9009F, -0.3054F, 0.0F, 0.0F));

        PartDefinition shinR3_r1 = shinR.addOrReplaceChild("shinR3_r1", CubeListBuilder.create().texOffs(67, 0).addBox(-0.5F, -2.625F, -0.375F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.375F)), PartPose.offsetAndRotation(-0.5F, 5.05F, -1.525F, 0.0873F, 0.0F, 0.0F));

        PartDefinition shinR2_r1 = shinR.addOrReplaceChild("shinR2_r1", CubeListBuilder.create().texOffs(26, 60).addBox(-1.5F, 2.375F, -1.625F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.375F)), PartPose.offsetAndRotation(0.0F, 0.025F, -0.25F, 0.0087F, 0.0F, 0.0F));

        PartDefinition shinR1_r1 = shinR.addOrReplaceChild("shinR1_r1", CubeListBuilder.create().texOffs(54, 16).addBox(-1.5F, -0.125F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, 0.05F, -0.6F, 0.0873F, 0.0F, 0.0F));

        PartDefinition footR = shinR.addOrReplaceChild("footR", CubeListBuilder.create().texOffs(15, 56).addBox(-1.5F, -1.125F, -1.0F, 3.0F, 6.0F, 2.0F, new CubeDeformation(-0.13F)), PartPose.offsetAndRotation(0.0F, 5.575F, -0.75F, -1.309F, 0.0F, 0.0F));

        PartDefinition pawR_bp = footR.addOrReplaceChild("pawR_bp", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.75F, 0.875F, 0.48F, 0.0F, 0.0F));

        PartDefinition pawR_tp = pawR_bp.addOrReplaceChild("pawR_tp", CubeListBuilder.create().texOffs(62, 49).addBox(-1.5F, -0.13F, -1.875F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.125F))
                .texOffs(56, 65).addBox(-1.5F, -0.155F, -1.85F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)), PartPose.offset(0.0F, -1.75F, 0.0F));

        PartDefinition toebeanR4_r1 = pawR_tp.addOrReplaceChild("toebeanR4_r1", CubeListBuilder.create().texOffs(63, 70).addBox(0.525F, -0.65F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 1.725F, -1.425F, 0.0F, -0.1745F, 0.0F));

        PartDefinition toebeanR3_r1 = pawR_tp.addOrReplaceChild("toebeanR3_r1", CubeListBuilder.create().texOffs(63, 70).addBox(-0.2F, -0.65F, -1.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 1.725F, -1.425F, 0.0F, -0.0873F, 0.0F));

        PartDefinition toebeanR2_r1 = pawR_tp.addOrReplaceChild("toebeanR2_r1", CubeListBuilder.create().texOffs(63, 70).addBox(-0.8F, -0.65F, -1.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 1.725F, -1.425F, 0.0F, 0.0873F, 0.0F));

        PartDefinition toebeanR1_r1 = pawR_tp.addOrReplaceChild("toebeanR1_r1", CubeListBuilder.create().texOffs(63, 70).addBox(-1.525F, -0.65F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 1.725F, -1.425F, 0.0F, 0.1745F, 0.0F));

        PartDefinition pawpadR3_r1 = pawR_tp.addOrReplaceChild("pawpadR3_r1", CubeListBuilder.create().texOffs(63, 70).addBox(-0.5F, -0.925F, -0.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, -1.425F, 0.0698F, 0.0F, 0.0F));

        PartDefinition pawpadR2_r1 = pawR_tp.addOrReplaceChild("pawpadR2_r1", CubeListBuilder.create().texOffs(67, 59).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.5F, 1.55F, -1.025F, 0.0F, -0.1745F, 0.0436F));

        PartDefinition pawpadR1_r1 = pawR_tp.addOrReplaceChild("pawpadR1_r1", CubeListBuilder.create().texOffs(67, 59).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-0.5F, 1.55F, -1.025F, 0.0F, 0.1745F, -0.0436F));

        PartDefinition pawR_r1 = pawR_tp.addOrReplaceChild("pawR_r1", CubeListBuilder.create().texOffs(31, 67).addBox(-0.125F, -1.8775F, -0.125F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, 1.75F, -2.925F, 0.0F, -0.24F, 0.0F));

        PartDefinition pawR_r2 = pawR_tp.addOrReplaceChild("pawR_r2", CubeListBuilder.create().texOffs(31, 67).addBox(-1.875F, -1.875F, -0.125F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, 1.75F, -2.925F, 0.0F, 0.24F, 0.0F));

        PartDefinition tail = lower.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 2.0F));

        PartDefinition tailBase_r1 = tail.addOrReplaceChild("tailBase_r1", CubeListBuilder.create().texOffs(47, 58).addBox(-0.25F, -1.25F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 1.5708F));

        PartDefinition tailMid1 = tail.addOrReplaceChild("tailMid1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.75F, 4.375F));

        PartDefinition tailMid2_r1 = tailMid1.addOrReplaceChild("tailMid2_r1", CubeListBuilder.create().texOffs(11, 65).addBox(-1.0F, -0.125F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 1.5708F));

        PartDefinition tailMid2 = tailMid1.addOrReplaceChild("tailMid2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.0F));

        PartDefinition tailMid3_r1 = tailMid2.addOrReplaceChild("tailMid3_r1", CubeListBuilder.create().texOffs(37, 60).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 1.5708F));

        PartDefinition tailEnd = tailMid2.addOrReplaceChild("tailEnd", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.75F));

        PartDefinition tailEnd_r1 = tailEnd.addOrReplaceChild("tailEnd_r1", CubeListBuilder.create().texOffs(67, 25).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(67, 6).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 1.5708F));

        return LayerDefinition.create(meshdefinition, 80, 80);
    }

    public void setupAnim(KeidranRenderState pRenderState) {
        super.setupAnim(pRenderState);
        this.applyHeadRotation(pRenderState, pRenderState.yRot, pRenderState.xRot);

        this.animateWalk(KeidranAnimations.ANIM_KEIDRAN_WALKING, pRenderState.walkAnimationPos, pRenderState.walkAnimationSpeed, 2.0F, 2.5F);

        this.animate(pRenderState.idleAnimationState, KeidranAnimations.ANIM_KEIDRAN_IDLE, pRenderState.ageInTicks, 1.0F);
        this.animate(pRenderState.attackAnimationState, KeidranAnimations.ANIM_KEIDRAN_ATTACK, pRenderState.ageInTicks, 1.0F);
        this.animate(pRenderState.swimAnimationState, KeidranAnimations.ANIM_KEIDRAN_SWIM, pRenderState.ageInTicks, 1.0F);
    }

    private void applyHeadRotation(KeidranRenderState pRenderState, float pYRot, float pXRot) {
        pYRot = Mth.clamp(pYRot, -30.0F, 30.0F);
        pXRot = Mth.clamp(pXRot, -25.0F, 45.0F);
        if (pRenderState.jumpCooldown > 0.0F) {
            float f = 45.0F * pRenderState.jumpCooldown / 55.0F;
            pXRot = Mth.clamp(pXRot + f, -25.0F, 70.0F);
        }

        this.head.yRot = pYRot * (float) (Math.PI / 180.0);
        this.head.xRot = pXRot * (float) (Math.PI / 180.0);
    }

}
