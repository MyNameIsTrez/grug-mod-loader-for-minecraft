package grug.grugmodloader;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
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
        // Case 0: null values — return 0 hash
        if (obj == null) return 0;

        // Case 1: cycle detection — we've already seen this object, so return 0 to avoid infinite recursion
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
            for (Entry<?, ?> entry : map.entrySet()) {
                int keyHash = safeHashCode(entry.getKey(), seen);
                int valueHash = safeHashCode(entry.getValue(), seen);
                h += keyHash ^ valueHash;
            }
            return h;
        }

        // Case 5: default — non-collection type like Block and BoxedI32
        return obj.hashCode();
    }

    @Override
    public String toString() {
        return safeToString(this, new IdentityHashMap<>());
    }

    private static String safeToString(Object obj, IdentityHashMap<Object, Boolean> seen) {
        // Case 0: null values — return "null"
        if (obj == null) return "null";

        // Case 1: cycle detection — we've already seen this object, return "[cyclic]"
        if (seen.containsKey(obj)) return "[cyclic]";

        // Mark this object as visited to detect future cycles
        seen.put(obj, true);

        // Case 2: if this is a GrugObject, recursively build the string for its fields
        if (obj instanceof GrugObject go) {
            return "GrugObject{type=" + go.type + ", object=" + safeToString(go.object, seen) + "}";
        }

        // Case 3: if this is a Set, represent each element within the Set
        if (obj instanceof Set<?> set) {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            Iterator<?> it = set.iterator();
            while (it.hasNext()) {
                sb.append(safeToString(it.next(), seen));
                if (it.hasNext()) sb.append(", ");
            }
            sb.append("}");
            return sb.toString();
        }

        // Case 4: if this is a Map, represent each key-value pair
        if (obj instanceof Map<?, ?> map) {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            for (Entry<?, ?> entry : map.entrySet()) {
                sb.append(safeToString(entry.getKey(), seen))
                .append("=")
                .append(safeToString(entry.getValue(), seen));
                sb.append(", ");
            }
            // Remove trailing comma and space if present
            if (!map.isEmpty()) {
                sb.setLength(sb.length() - 2);
            }
            sb.append("}");
            return sb.toString();
        }

        // Case 5: default — for other object types like Block and BoxedI32
        return obj.toString();
    }
}
