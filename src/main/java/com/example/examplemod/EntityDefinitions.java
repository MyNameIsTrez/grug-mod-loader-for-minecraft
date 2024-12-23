package com.example.examplemod;

class EntityDefinitions {
    public static GrugBlock block = new GrugBlock();
    public static GrugBlockEntity blockEntity = new GrugBlockEntity();
}

class GrugBlock {
    public GrugBlock() {
    }

    public GrugBlock(GrugBlock other) {
    }
}

class GrugBlockEntity {
    public long onFns = 0;

    public GrugBlockEntity() {
    }

    public GrugBlockEntity(GrugBlockEntity other) {
        this.onFns = other.onFns;
    }
}
