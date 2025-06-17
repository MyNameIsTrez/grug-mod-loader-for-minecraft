package grug.grugmodloader;

import java.util.ArrayList;
import java.util.List;

public class GrugEntity {
    public long id;
    public byte[] globals;
    public long onFns;
    public int entitiesIndex;
    public List<GrugObject> childEntities = new ArrayList<>();
}
