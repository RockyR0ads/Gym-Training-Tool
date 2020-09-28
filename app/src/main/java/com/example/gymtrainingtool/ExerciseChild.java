package com.example.gymtrainingtool;
import java.io.Serializable;

public class ExerciseChild implements Serializable{

    private static final long serialVersionUID = 1L;
    private String title,time,weight;
    private boolean anotherSet;
    private String ChildItemTitle;

    int reps;

    public ExerciseChild() {
    }

    public ExerciseChild(String title, String weight, String time, int reps) {
        this.title = title;
        this.weight = weight;
        this.time = time;
        this.reps = reps;
    }
    public ExerciseChild(String childItemTitle) {
        this.ChildItemTitle = childItemTitle;
    }

    // Getter and Setter method
    // for the parameter
    public String getChildItemTitle()
    {
        return ChildItemTitle;
    }

    public void setChildItemTitle(
            String childItemTitle)
    {
        ChildItemTitle = childItemTitle;
    }

    public boolean isAnotherSet() {
        return anotherSet;
    }

    public void setAnotherSet(boolean anotherSet) {
        this.anotherSet = anotherSet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getTime() {
        return time;
    }

    public void setYear(String year) {
        this.time = time;
    }

    public String getWeight() { return weight; }

    public void setWeight(String weight) { this.weight = weight; }

    public int getReps() { return reps; }

    public void setReps(int reps) { this.reps = reps; }
}
