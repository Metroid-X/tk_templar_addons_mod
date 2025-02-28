package net.arcspartan.templar_addons.core;

//import net.arcspartan.templar_addons.entity.npc.unused.KeidranCanineType;
import net.arcspartan.templar_addons.entity.npc.KeidranFelineType;
//import net.arcspartan.templar_addons.entity.npc.unused.KeidranVulpineType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class TemplarRegistries extends Registries {

//    public static final ResourceKey<Registry<KeidranProfession>> KEIDRAN_PROFESSION = createRegistryKey("villager_profession");
//    public static final ResourceKey<Registry<KeidranType>> KEIDRAN_TYPE = createRegistryKey("keidran_type");
//    public static final ResourceKey<Registry<KeidranCanineType>> KEIDRAN_CANINE_TYPE = createRegistryKey("keidran_canine_type");
    public static final ResourceKey<Registry<KeidranFelineType>> KEIDRAN_FELINE_TYPE = createRegistryKey("keidran_feline_type");
//    public static final ResourceKey<Registry<KeidranVulpineType>> KEIDRAN_VULPINE_TYPE = createRegistryKey("keidran_vulpine_type");

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String pName) {
        return ResourceKey.createRegistryKey(ResourceLocation.withDefaultNamespace(pName));
    }
}
