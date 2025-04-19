package com.example.examplemod;

public enum EntityType {
    Block,
    BlockEntity,
    BlockPos,
    BlockState,
    BoxedI32,
    Entity,
    Entry,
    HashMap,
    HashMapIterator,
    HashSet,
    HashSetIterator,
    Item,
    ItemEntity,
    ItemStack,
    Level,
    ResourceLocation,
    Vec3;

    // EntityType.values()[i] is expensive, so we cache .values().
    // See https://stackoverflow.com/a/8762387/13279557
    private final static EntityType[] values = EntityType.values();

    public static EntityType get(int i) {
        return values[i];
    }
}
