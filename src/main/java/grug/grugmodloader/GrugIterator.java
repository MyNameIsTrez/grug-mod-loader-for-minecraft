package grug.grugmodloader;

import java.util.Iterator;

public class GrugIterator {
    Iterator<?> iterator;
    IterableType iterableType;
    boolean isIterableGlobal;

    public enum IterableType {
        HashMap,
        HashSet;
    }

    public GrugIterator(Iterator<?> iterator, IterableType iterableType, boolean isIterableGlobal) {
        this.iterator = iterator;
        this.iterableType = iterableType;
        this.isIterableGlobal = isIterableGlobal;
    }
}
