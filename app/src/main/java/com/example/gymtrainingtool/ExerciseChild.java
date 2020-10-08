package com.example.gymtrainingtool;
import java.io.Serializable;

public class ExerciseChild implements Serializable{

    private static final long serialVersionUID = 1L;
    private String time,weight;
    private boolean anotherSet;
    private String ChildItemTitle;
    private int reps;

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    private Exercise exercise;

    public ExerciseChild() {
    }

    public ExerciseChild(String weight, String time, int reps) {
        this.weight = weight;
        this.time = time;
        this.reps = reps;
    }

    public ExerciseChild(String childItemTitle) {
        this.ChildItemTitle = childItemTitle;
    }

    public String getChildItemTitle()
    {
        return ChildItemTitle;
    }

    public void setChildItemTitle(String childItemTitle) { ChildItemTitle = childItemTitle; }

    public boolean isAnotherSet() {
        return anotherSet;
    }

    public void setAnotherSet(boolean anotherSet) {
        this.anotherSet = anotherSet;
    }

    public String getTime() {
        return time;
    }

    public String getWeight() { return weight; }

    public void setWeight(String weight) { this.weight = weight; }

    public int getReps() { return reps; }

    public void setReps(int reps) { this.reps = reps; }
}
