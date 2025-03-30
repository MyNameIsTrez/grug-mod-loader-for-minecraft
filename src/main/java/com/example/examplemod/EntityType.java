package com.example.examplemod;

public enum EntityType {
    BlockEntity,
    BlockPos,
    Entity,
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
