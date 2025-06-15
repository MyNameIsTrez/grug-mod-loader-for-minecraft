package grug.grugmodloader;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class WeakGrugValueMap {
    // The actual map from ID to WeakValue of the object
    private final Map<Long, WeakValue> map = new HashMap<>();

    // ReferenceQueue to track garbage-collected values
    private final ReferenceQueue<GrugObject> queue = new ReferenceQueue<>();

    // Add an object with a long ID
    public void put(long id, GrugObject value) {
        cleanup(); // Clean up any stale references first
        map.put(id, new WeakValue(id, value, queue));
    }

    // Get the object by ID
    public GrugObject get(long id) {
        cleanup();
        WeakValue ref = map.get(id);
        return ref != null ? ref.get() : null;
    }

    // Clean up the map by removing entries whose values were GC'd
    private void cleanup() {
        WeakValue ref;
        while ((ref = (WeakValue)queue.poll()) != null) {
            long id = ref.id;
            map.remove(id);
        }
    }

    public void clear() {
        // Remove all entries from the map
        map.clear();

        // Drain the reference queue to discard any pending references
        while (queue.poll() != null) {}
    }

    public int size() {
        // Ensure stale entries are removed
        cleanup();

        return map.size();
    }

    // A WeakReference subclass that knows its ID
    private static class WeakValue extends WeakReference<GrugObject> {
        final long id;

        WeakValue(long id, GrugObject referent, ReferenceQueue<? super GrugObject> q) {
            super(referent, q);
            this.id = id;
        }
    }
}
