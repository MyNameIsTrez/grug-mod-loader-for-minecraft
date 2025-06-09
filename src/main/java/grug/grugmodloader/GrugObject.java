package grug.grugmodloader;

import java.util.Objects;

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

        // TODO: Not sure if this code is even correct
        // Prevents infinite recursion when a GrugObject contains itself
        // if (other.type == GrugEntityType.HashSet || other.type == GrugEntityType.HashMap) return false;

        return type == other.type && Objects.equals(object, other.object);
    }

    @Override
    public int hashCode() {
        // TODO: Not sure if this code is even correct
        // Prevents infinite recursion when a GrugObject contains itself
        if (type == GrugEntityType.HashSet || type == GrugEntityType.HashMap) return type.hashCode();

        return Objects.hash(type, object);
    }

    @Override
    public String toString() {
        return "GrugObject{type=" + type.toString() + ", object=" + object.getClass().getSimpleName() + "}";
    }
}
