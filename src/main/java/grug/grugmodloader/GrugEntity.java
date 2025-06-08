package grug.grugmodloader;

import java.util.HashSet;
import java.util.Set;

public class GrugEntity {
    public long id;
    public byte[] globals;
    public long onFns;
    public int entitiesIndex;
    public Set<GrugObject> childEntities = new HashSet<>();
}
