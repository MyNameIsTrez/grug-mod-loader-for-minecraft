package com.example.examplemod;

import java.util.ArrayList;

class GrugDir {
    public String name;

    public ArrayList<GrugDir> dirs = new ArrayList<GrugDir>();
    public int dirsSize;

    public ArrayList<GrugFile> files = new ArrayList<GrugFile>();
    public int filesSize;

    public long address;

    public GrugDir() {
    }
}
