package net.arcspartan.templar_addons.entity.npc;

import com.google.common.collect.Maps;
import net.arcspartan.templar_addons.TemplarAddonsMod;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.arcspartan.templar_addons.core.BuiltInTemplarRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public final class KeidranFelineType {


    // FELINES
    public static final KeidranFelineType TIGER  = register("tiger");
    public static final KeidranFelineType SNOW_LEOPARD = register("snow_leopard");
    public static final KeidranFelineType LEOPARD = register("leopard");
    public static final KeidranFelineType COUGAR = register("cougar");
    public static final KeidranFelineType JAGUAR = register("jaguar");
    public static final KeidranFelineType CHEETAH = register("cheetah");
    public static final KeidranFelineType WHITE_TIGER = register("white_tiger");
    public static final KeidranFelineType SERVAL = register("serval");
    public static final KeidranFelineType LYNX = register("lynx");
    public static final KeidranFelineType CAT = register("cat");
    private final String name;


    private static final Map<ResourceKey<Biome>, KeidranFelineType> FELINE_BY_BIOME = Util.make(Maps.newHashMap(), hashMap -> {
        hashMap.put(Biomes.BADLANDS, COUGAR);
        hashMap.put(Biomes.DESERT, COUGAR);
        hashMap.put(Biomes.ERODED_BADLANDS, COUGAR);
        hashMap.put(Biomes.WOODED_BADLANDS, COUGAR);
        hashMap.put(Biomes.BAMBOO_JUNGLE, TIGER);
        hashMap.put(Biomes.JUNGLE, TIGER);
        hashMap.put(Biomes.SPARSE_JUNGLE, TIGER);
        hashMap.put(Biomes.SAVANNA_PLATEAU, LEOPARD);
        hashMap.put(Biomes.SAVANNA, LEOPARD);
        hashMap.put(Biomes.WINDSWEPT_SAVANNA, LEOPARD);
        hashMap.put(Biomes.DEEP_FROZEN_OCEAN, WHITE_TIGER);
        hashMap.put(Biomes.FROZEN_OCEAN, WHITE_TIGER);
        hashMap.put(Biomes.FROZEN_RIVER, WHITE_TIGER);
        hashMap.put(Biomes.ICE_SPIKES, SNOW_LEOPARD);
        hashMap.put(Biomes.SNOWY_BEACH, WHITE_TIGER);
        hashMap.put(Biomes.SNOWY_TAIGA, LYNX);
        hashMap.put(Biomes.SNOWY_PLAINS, LYNX);
        hashMap.put(Biomes.GROVE, LYNX);
        hashMap.put(Biomes.SNOWY_SLOPES, SNOW_LEOPARD);
        hashMap.put(Biomes.FROZEN_PEAKS, SNOW_LEOPARD);
        hashMap.put(Biomes.JAGGED_PEAKS, SNOW_LEOPARD);
        hashMap.put(Biomes.SWAMP, JAGUAR);
        hashMap.put(Biomes.MANGROVE_SWAMP, JAGUAR);
        hashMap.put(Biomes.OLD_GROWTH_SPRUCE_TAIGA, LYNX);
        hashMap.put(Biomes.OLD_GROWTH_PINE_TAIGA, LYNX);
        hashMap.put(Biomes.WINDSWEPT_GRAVELLY_HILLS, COUGAR);
        hashMap.put(Biomes.WINDSWEPT_HILLS, COUGAR);
        hashMap.put(Biomes.TAIGA, LYNX);
        hashMap.put(Biomes.WINDSWEPT_FOREST, COUGAR);
    });

    public KeidranFelineType(String pName) {
        this.name = pName;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static KeidranFelineType register(String pKey) {
        return Registry.register(
                BuiltInTemplarRegistries.KEIDRAN_FELINE_TYPE, ResourceLocation.withDefaultNamespace(
                        pKey
                ), new KeidranFelineType(pKey)
        );
    }

    public static KeidranFelineType felineByBiome(Holder<Biome> pBiome) {
        return pBiome.unwrapKey().map(FELINE_BY_BIOME::get).orElse(TIGER);
    }

    /** FORGE: Registers the KeidranFelineType that will spawn in the given biome. This method should be called during FMLCommonSetupEvent using event.enqueueWork() */
    public static void registerFelineBiomeType(ResourceKey<Biome> biomeKey, KeidranFelineType keidranType) {
        FELINE_BY_BIOME.put(biomeKey, keidranType);
    }

}
