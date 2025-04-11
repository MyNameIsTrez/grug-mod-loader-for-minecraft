package com.example.examplemod;

import java.util.Iterator;

public class GrugIterator {
    Iterator<?> iterator;
    EntityType containerType;
    EntityType containedType;

    public GrugIterator(Iterator<?> iterator, EntityType containerType, EntityType containedType) {
        this.iterator = iterator;
        this.containerType = containerType;
        this.containedType = containedType;
    }
}
