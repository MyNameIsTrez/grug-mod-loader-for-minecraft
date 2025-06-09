package grug.grugmodloader;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class GrugObject {
    public final GrugEntityType type;
    public final Object object;

    public GrugObject(GrugEntityType type, Object object) {
        this.type = type;
        this.object = object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrugObject other = (GrugObject)o;
        return type == other.type && Objects.equals(object, other.object);
    }

    @Override
    public int hashCode() {
        return safeHashCode(this, new IdentityHashMap<>());
    }

    private static int safeHashCode(Object obj, IdentityHashMap<Object, Boolean> seen) {
        // Case 0: null values: return 0 hash
        if (obj == null) return 0;

        // Case 1: cycle detection: we've already seen this object, so return 0 to avoid infinite recursion
        if (seen.containsKey(obj)) return 0;

        // Mark this object as visited to detect future cycles
        seen.put(obj, true);

        // Case 2: if this is a GrugObject, recursively compute hash for its type and object fields
        if (obj instanceof GrugObject go) {
            return Objects.hash(go.type, safeHashCode(go.object, seen));
        }

        // Case 3: if this is a Set, combine hashCodes of elements
        if (obj instanceof Set<?> set) {
            int h = 0;
            for (Object item : set) {
                h += safeHashCode(item, seen);
            }
            return h;
        }

        // Case 4: if this is a Map, hash each entry by XORing the key and value hashes
        if (obj instanceof Map<?, ?> map) {
            int h = 0;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                int keyHash = safeHashCode(entry.getKey(), seen);
                int valueHash = safeHashCode(entry.getValue(), seen);
                h += keyHash ^ valueHash;
            }
            return h;
        }

        // Case 5: default: non-collection type like Block or BoxedI32
        return obj.hashCode();
    }

    @Override
    public String toString() {
        return "GrugObject{type=" + type.toString() + ", object=" + object.getClass().getSimpleName() + "}";
    }
}
