package net.arcspartan.templar_addons.core;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Lifecycle;
import net.minecraft.Util;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
//import net.arcspartan.templar_addons.entity.npc.unused.KeidranCanineType;
import net.arcspartan.templar_addons.entity.npc.KeidranFelineType;
//import net.arcspartan.templar_addons.entity.npc.unused.KeidranVulpineType;

import java.util.Map;
import java.util.function.Supplier;

public class BuiltInTemplarRegistries extends BuiltInRegistries {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<ResourceLocation, Supplier<?>> LOADERS = Maps.newLinkedHashMap();
    private static final WritableRegistry<WritableRegistry<?>> WRITABLE_REGISTRY = new MappedRegistry<>(ResourceKey.createRegistryKey(Registries.ROOT_REGISTRY_NAME), Lifecycle.stable());

//    public static final DefaultedRegistry<KeidranCanineType> KEIDRAN_CANINE_TYPE =
//            registerDefaulted(
//                    TemplarRegistries.KEIDRAN_CANINE_TYPE, "forest_wolf",
//                    keidranCanineTypes -> KeidranCanineType.FOREST_WOLF
//            );
    public static final DefaultedRegistry<KeidranFelineType> KEIDRAN_FELINE_TYPE =
            registerDefaulted(
                    TemplarRegistries.KEIDRAN_FELINE_TYPE, "tiger",
                    keidranBigCatTypes -> KeidranFelineType.TIGER
            );
//    public static final DefaultedRegistry<KeidranVulpineType> KEIDRAN_VULPINE_TYPE =
//            registerDefaulted(
//                    TemplarRegistries.KEIDRAN_VULPINE_TYPE, "red_fox",
//                    keidranVulpineTypes -> KeidranVulpineType.RED_FOX
//            );




    private static <T> Registry<T> registerSimple(ResourceKey<? extends Registry<T>> pKey, BuiltInTemplarRegistries.TemplarRegistryBootstrap<T> pBootstrap) {
        return internalRegister(pKey, new MappedRegistry<>(pKey, Lifecycle.stable(), false), pBootstrap);
    }

    private static <T> Registry<T> registerSimpleWithIntrusiveHolders(ResourceKey<? extends Registry<T>> pKey, BuiltInTemplarRegistries.TemplarRegistryBootstrap<T> pBootstrap) {
        return internalRegister(pKey, new MappedRegistry<>(pKey, Lifecycle.stable(), true), pBootstrap);
    }

    private static <T> DefaultedRegistry<T> registerDefaulted(
            ResourceKey<? extends Registry<T>> pKey, String pDefaultKey, BuiltInTemplarRegistries.TemplarRegistryBootstrap<T> pBootstrap
    ) {
        return internalRegister(pKey, new DefaultedMappedRegistry<>(pDefaultKey, pKey, Lifecycle.stable(), false), pBootstrap);
    }

    private static <T> DefaultedRegistry<T> registerDefaultedWithIntrusiveHolders(
            ResourceKey<? extends Registry<T>> pKey, String pDefaultKey, BuiltInTemplarRegistries.TemplarRegistryBootstrap<T> pBootstrap
    ) {
        return internalRegister(pKey, new DefaultedMappedRegistry<>(pDefaultKey, pKey, Lifecycle.stable(), true), pBootstrap);
    }

    private static <T, R extends WritableRegistry<T>> R internalRegister(
            ResourceKey<? extends Registry<T>> pKey, R pRegistry, BuiltInTemplarRegistries.TemplarRegistryBootstrap<T> pBootstrap
    ) {
        Bootstrap.checkBootstrapCalled(() -> "registry " + pKey.location());
        ResourceLocation resourcelocation = pKey.location();
        var maybeWrapped = net.minecraftforge.registries.GameData.getWrapper(pKey, pRegistry);
        pRegistry = maybeWrapped;
        LOADERS.put(resourcelocation, () -> pBootstrap.run(maybeWrapped));
        WRITABLE_REGISTRY.register((ResourceKey)pKey, pRegistry, RegistrationInfo.BUILT_IN);
        return pRegistry;
    }

    private static void createContents() {
        LOADERS.forEach((p_259863_, p_259387_) -> {
            if (p_259387_.get() == null) {
                LOGGER.error("Unable to bootstrap registry '{}'", p_259863_);
            }
        });
    }

    private static void freeze() {
        REGISTRY.freeze();

        for (Registry<?> registry : REGISTRY) {
            bindBootstrappedTagsToEmpty(registry);
            registry.freeze();
        }
    }

    private static <T extends Registry<?>> void validate(Registry<T> pRegistry) {
        pRegistry.forEach(p_358159_ -> {
            if (p_358159_.keySet().isEmpty()) {
                Util.logAndPauseIfInIde("Registry '" + pRegistry.getKey((T)p_358159_) + "' was empty after loading");
            }

            if (p_358159_ instanceof DefaultedRegistry) {
                ResourceLocation resourcelocation = ((DefaultedRegistry)p_358159_).getDefaultKey();
                Validate.notNull(p_358159_.get(resourcelocation), "Missing default of DefaultedMappedRegistry: " + p_358159_.key() + " - " + resourcelocation);
            }
        });
    }

    private static void bindBootstrappedTagsToEmpty(Registry<?> pRegistry) {
        ((MappedRegistry)pRegistry).bindAllTagsToEmpty();
    }

    @FunctionalInterface
    interface TemplarRegistryBootstrap<T> {
        Object run(Registry<T> pRegistry);
    }
}
