package com.example.gymtrainingtool;
import java.io.Serializable;

public class ExerciseChild implements Serializable{

    private static final long serialVersionUID = 1L;
    private String time,weight,parentTitle;
    private int reps,rpe,set;


    //constructors
    public ExerciseChild() {
    }

    public ExerciseChild(int set, String weight, String time, int reps,int rpe) {
        this.weight = weight;
        this.time = time;
        this.reps = reps;
        this.set = set;
        this.rpe = rpe;

    }
    public ExerciseChild(int set) {
        this.set = set;
    }

    //getters and setters
    public void setParentTitle(String parentTitle) { this.parentTitle = parentTitle; }

    public String getParentTitle() { return parentTitle; }

    public int getSet() { return set; }

    public void setSet(int set) { this.set = set; }

    public String getTime() {
        return time;
    }

    public String getWeight() { return weight; }

    public void setWeight(String weight) { this.weight = weight; }

    public int getReps() { return reps; }

    public void setReps(int reps) { this.reps = reps; }

    public int getRpe() { return rpe; }

    public void setRpe(int rpe) { this.rpe = rpe; }

}
