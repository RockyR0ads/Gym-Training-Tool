package com.example.gymtrainingtool;
import java.io.Serializable;

public class ExerciseChild implements Serializable{

    private static final long serialVersionUID = 1L;
    private String time,weight;
    private boolean anotherSet;
    private int reps;
    private int parentPosition;
    private Exercise exercise;
    private int set;
    private String parentTitle;

    public ExerciseChild() {
    }

    public ExerciseChild(int set, String weight, String time, int reps) {
        this.weight = weight;
        this.time = time;
        this.reps = reps;
        this.set = set;

    }

    public void setParentTitle(String parentTitle) { this.parentTitle = parentTitle; }

    public String getParentTitle() { return parentTitle; }

    public int getSet() { return set; }

    public void setSet(int set) { this.set = set; }

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

    public int getParentPosition() {
        return parentPosition;
    }

    public void setParentPosition(int parentPosition) {
        this.parentPosition = parentPosition;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
