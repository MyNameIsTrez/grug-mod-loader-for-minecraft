package com.example.examplemod;

class EntityDefinitions {
    public static Block block = new Block();
    public static BlockEntity blockEntity = new BlockEntity();
}

class Block {
    public Block() {
    }

    public Block(Block other) {
    }
}

class BlockEntity {
    public long onFns = 0;

    public BlockEntity() {
    }

    public BlockEntity(BlockEntity other) {
        this.onFns = other.onFns;
    }
}
