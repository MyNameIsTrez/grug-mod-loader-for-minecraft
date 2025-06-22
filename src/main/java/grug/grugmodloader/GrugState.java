package grug.grugmodloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class GrugState {
    private static final WeakHashMap<ThreadGroup, GrugState> INSTANCES = new WeakHashMap<>();

    // The sole purpose of this List is to allow Grug.reloadModifiedEntities() to despawn old child entities.
    // This is deliberately not initialized with a List, and not marked final.
    // This variable gets assigned an entity's ArrayList of child GrugObjects before init_globals() is called,
    // and it gets assigned a new ArrayList<GrugObject> of on_ fn entities, before an on_ function is called.
    private List<GrugObject> fnEntities = null;

    // TODO: This does not recycle indices of despawned entities,
    //       which means this will eventually wrap around back to 0
    private Map<GrugEntityType, Integer> nextEntityIndices = new HashMap<>();

    private WeakGrugValueMap grugObjects = new WeakGrugValueMap();

    public static void reset() {
        INSTANCES.forEach((group, state) -> resetState(state));
    }

    private static void resetState(GrugState state) {
        state.resetNextEntityIndices();

        state.grugObjects.clear();

        state.fnEntities = null;
    }

    public static GrugState get() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();

        // Just checking if `group` is `SidedThreadGroups.CLIENT` or `SidedThreadGroups.SERVER` isn't sufficient,
        // as a thread group named "main" calls the onLoad() of BlockEntity instances.
        //
        // The below line gives such a "main" thread its own GrugState,
        // rather than sharing it with the client or server thread.
        return INSTANCES.computeIfAbsent(group, g -> {
            GrugState state = new GrugState();
            state.resetNextEntityIndices();
            return state;
        });
    }

    public List<GrugObject> getFnEntities() {
        return fnEntities;
    }

    public void setFnEntities(List<GrugObject> newFnEntities) {
        fnEntities = newFnEntities;
    }

    public void newFnEntities() {
        fnEntities = new ArrayList<>();
    }

    public void addFnEntity(GrugObject grugObject) {
        fnEntities.add(grugObject);
    }

    public void resetNextEntityIndices() {
        for (GrugEntityType entityType : GrugEntityType.values()) {
            nextEntityIndices.put(entityType, 0);
        }
    }

    public int getNextEntityIndex(GrugEntityType type) {
        return nextEntityIndices.get(type);
    }

    public void putNextEntityIndex(GrugEntityType type, int entityIndex) {
        nextEntityIndices.put(type, entityIndex);
    }

    public GrugObject getGrugObject(long id) {
        return grugObjects.get(id);
    }

    public void putGrugObject(long id, GrugObject grugObject) {
        grugObjects.put(id, grugObject);
    }

    public int getGrugObjectsSize() {
        return grugObjects.size();
    }
}
