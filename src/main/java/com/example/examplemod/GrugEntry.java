package com.example.examplemod;

import java.util.Map.Entry;

public class GrugEntry {
    Entry<Long, Long> entry;
    boolean isHashMapGlobal;

    public GrugEntry(Entry<Long, Long> entry, boolean isHashMapGlobal) {
        this.entry = entry;
        this.isHashMapGlobal = isHashMapGlobal;
    }
}
