package grug.grugmodloader;

class GrugFile {
    public String name;
    public String entity;
    public String entityType;
    public long dll;
    public int globalsSize;
    public long initGlobalsFn;
    public long onFns;
    public long resourceMtimes;

    public GrugFile() {
    }
}
