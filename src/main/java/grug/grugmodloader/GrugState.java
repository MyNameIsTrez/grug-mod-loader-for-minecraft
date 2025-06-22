package grug.grugmodloader;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class GrugState {
    private static final WeakHashMap<ThreadGroup, GrugState> INSTANCES = new WeakHashMap<>();

    // The sole purpose of this List is to allow Grug.reloadModifiedEntities() to despawn old child entities.
    // This is deliberately not initialized with a List, and not marked final.
    // This variable gets assigned an entity's ArrayList of child GrugObjects before init_globals() is called,
    // and it gets assigned a new ArrayList<GrugObject> of on_ fn entities, before an on_ function is called.
    private List<GrugObject> fnEntities = null;

    // TODO: Move all other `static` variables here!

    // TODO: Make sure that Grug.resetVariables() isn't resetting any statics anymore

    public static void reset() {
        INSTANCES.forEach((group, state) -> resetState(state));
    }

    private static void resetState(GrugState state) {
        state.fnEntities = null;
    }

    public static GrugState get() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();

        // Just checking if `group` is `SidedThreadGroups.CLIENT` or `SidedThreadGroups.SERVER` isn't sufficient,
        // as a thread group named "main" calls the onLoad() of BlockEntity instances.
        //
        // The below line gives such a "main" thread its own GrugState,
        // rather than sharing it with the client or server thread.
        return INSTANCES.computeIfAbsent(group, g -> new GrugState());
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
}
