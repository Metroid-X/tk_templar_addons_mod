package net.arcspartan.templar_addons.entity.npc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.arcspartan.templar_addons.core.BuiltInTemplarRegistries;
import net.arcspartan.templar_addons.core.TemplarRegistries;
import net.arcspartan.templar_addons.entity.npc.KeidranFelineType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.npc.VillagerProfession;

public class KeidranFelineData {
    public static final int MIN_KEIDRAN_LEVEL = 1;
    public static final int MAX_KEIDRAN_LEVEL = 5;
    private static final int[] NEXT_LEVEL_XP_THRESHOLDS = new int[]{0, 10, 70, 150, 250};
    public static final Codec<KeidranFelineData> CODEC = RecordCodecBuilder.create(
            dataInstance -> dataInstance.group(
                            BuiltInTemplarRegistries.KEIDRAN_FELINE_TYPE.byNameCodec().fieldOf("type").orElseGet(() -> KeidranFelineType.TIGER).forGetter(keidranFelineData -> keidranFelineData.type),
                            BuiltInRegistries.VILLAGER_PROFESSION
                                    .byNameCodec()
                                    .fieldOf("profession")
                                    .orElseGet(() -> VillagerProfession.NONE)
                                    .forGetter(keidranFelineData -> keidranFelineData.profession),
                            Codec.INT.fieldOf("level").orElse(1).forGetter(keidranFelineData -> keidranFelineData.level)
                    )
                    .apply(dataInstance, KeidranFelineData::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, KeidranFelineData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(TemplarRegistries.KEIDRAN_FELINE_TYPE),
            keidranFelineData -> keidranFelineData.type,
            ByteBufCodecs.registry(Registries.VILLAGER_PROFESSION),
            keidranFelineData -> keidranFelineData.profession,
            ByteBufCodecs.VAR_INT,
            keidranFelineData -> keidranFelineData.level,
            KeidranFelineData::new
    );

    private final KeidranFelineType type;
    private final VillagerProfession profession;
    private final int level;

    public KeidranFelineData(KeidranFelineType pType, VillagerProfession pProfession, int pLevel) {
        this.type = pType;
        this.profession = pProfession;
        this.level = Math.max(1, pLevel);
    }

    public KeidranFelineType getType() {
        return this.type;
    }

    public VillagerProfession getProfession() {
        return this.profession;
    }

    public int getLevel() {
        return this.level;
    }


    public KeidranFelineData setType(KeidranFelineType pType) {
        return new KeidranFelineData(pType, this.profession, this.level);
    }

    public KeidranFelineData setProfession(VillagerProfession pProfession) {
        return new KeidranFelineData(this.type, pProfession, this.level);
    }

    public KeidranFelineData setLevel(int pLevel) {
        return new KeidranFelineData(this.type, this.profession, pLevel);
    }

    public static int getMinXpPerLevel(int pLevel) {
        return canLevelUp(pLevel) ? NEXT_LEVEL_XP_THRESHOLDS[pLevel - 1] : 0;
    }

    public static int getMaxXpPerLevel(int pLevel) {
        return canLevelUp(pLevel) ? NEXT_LEVEL_XP_THRESHOLDS[pLevel] : 0;
    }

    public static boolean canLevelUp(int pLevel) {
        return pLevel >= 1 && pLevel < 5;
    }
}
