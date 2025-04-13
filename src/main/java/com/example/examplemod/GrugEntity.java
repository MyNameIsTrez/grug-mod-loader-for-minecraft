package com.example.examplemod;

import java.util.HashSet;
import java.util.Set;

public class GrugEntity {
    public long id;
    public byte[] globals;
    public long onFns;
    public int entitiesIndex;
    public Set<Long> childEntities = new HashSet<>();
}
