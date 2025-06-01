package grug.grugmodloader;

public enum GrugEntityType {
    Block,
    BlockEntity,
    BlockPos,
    BlockState,
    BoxedI32,
    Entity,
    Entry,
    HashMap,
    HashSet,
    Item,
    ItemEntity,
    ItemStack,
    Iterator,
    Level,
    ResourceLocation,
    Vec3;

    // EntityType.values()[i] is expensive, so we cache .values().
    // See https://stackoverflow.com/a/8762387/13279557
    private final static GrugEntityType[] values = GrugEntityType.values();

    public static GrugEntityType get(int i) {
        return values[i];
    }
}
