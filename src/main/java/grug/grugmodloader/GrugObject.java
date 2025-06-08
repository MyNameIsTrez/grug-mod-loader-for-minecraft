package grug.grugmodloader;

public class GrugObject {
    GrugEntityType type;
    Object object;

    public GrugObject(GrugEntityType type, Object object) {
        this.type = type;
        this.object = object;
    }
}
