package net.arcspartan.templar_addons.entity;

import net.arcspartan.templar_addons.TemplarAddonsMod;
import net.arcspartan.templar_addons.entity.custom.FelineKeidran;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TemplarAddonsMod.MOD_ID);


    public static final RegistryObject<EntityType<FelineKeidran>> KEIDRAN_TIGER =
            ENTITY_TYPES.register("tiger_keidran", () -> EntityType.Builder.of(FelineKeidran::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.8F)
                    .eyeHeight(1.72F)
                    .build(ResourceKey
                            .create(Registries
                                    .ENTITY_TYPE, ResourceLocation
                                    .fromNamespaceAndPath(TemplarAddonsMod.MOD_ID, "tiger_keidran")
                            )
                    )
            );


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
