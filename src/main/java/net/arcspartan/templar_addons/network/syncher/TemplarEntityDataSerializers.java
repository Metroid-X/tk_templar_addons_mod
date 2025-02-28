package net.arcspartan.templar_addons.network.syncher;

//import net.arcspartan.templar_addons.entity.npc.unused.KeidranCanineData;
import net.arcspartan.templar_addons.entity.npc.KeidranFelineData;
//import net.arcspartan.templar_addons.entity.npc.unused.KeidranVulpineData;
import net.minecraft.network.syncher.EntityDataSerializer;


public class TemplarEntityDataSerializers {
//    private static final CrudeIncrementalIntIdentityHashBiMap<EntityDataSerializer<?>> SERIALIZERS = CrudeIncrementalIntIdentityHashBiMap.create(16);
//
//    private static final StreamCodec<ByteBuf, Optional<BlockState>> OPTIONAL_BLOCK_STATE_CODEC = new StreamCodec<ByteBuf, Optional<BlockState>>() {
//        public void encode(ByteBuf p_329740_, Optional<BlockState> p_331636_) {
//            if (p_331636_.isPresent()) {
//                VarInt.write(p_329740_, Block.getId(p_331636_.get()));
//            } else {
//                VarInt.write(p_329740_, 0);
//            }
//        }
//
//        public Optional<BlockState> decode(ByteBuf p_334256_) {
//            int i = VarInt.read(p_334256_);
//            return i == 0 ? Optional.empty() : Optional.of(Block.stateById(i));
//        }
//    };

//    public static final EntityDataSerializer<KeidranCanineData> KEIDRAN_CANINE_DATA =
//    EntityDataSerializer.forValueType(KeidranCanineData.STREAM_CODEC);

    public static final EntityDataSerializer<KeidranFelineData> KEIDRAN_FELINE_DATA =
        EntityDataSerializer.forValueType(KeidranFelineData.STREAM_CODEC);

//    public static final EntityDataSerializer<KeidranVulpineData> KEIDRAN_VULPINE_DATA =
//    EntityDataSerializer.forValueType(KeidranVulpineData.STREAM_CODEC);

    private TemplarEntityDataSerializers() {
    }
}
