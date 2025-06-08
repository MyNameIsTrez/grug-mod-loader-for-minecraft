package grug.grugmodloader;

import java.util.Iterator;

public class GrugIterator {
    Iterator<?> iterator;
    IterableType iterableType;

    public enum IterableType {
        HashMap,
        HashSet;
    }

    public GrugIterator(Iterator<?> iterator, IterableType iterableType) {
        this.iterator = iterator;
        this.iterableType = iterableType;
    }
}
