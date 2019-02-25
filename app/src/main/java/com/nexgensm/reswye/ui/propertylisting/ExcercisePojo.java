package com.nexgensm.reswye.ui.propertylisting;

/**
 * Created by Sumeet on 16-07-2017.
 */

public class ExcercisePojo {
    public int exerciseId;
    public String name;
    public int fid;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public ExcercisePojo() {
    }

    public ExcercisePojo(int exerciseId, String name,int fid) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.fid=fid;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
