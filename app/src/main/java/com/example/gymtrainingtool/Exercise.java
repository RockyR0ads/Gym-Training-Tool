package com.example.gymtrainingtool;
import java.io.Serializable;
import java.util.List;

public class Exercise implements Serializable{

    private static final long serialVersionUID = 1L;
    private String title,time,weight;
    private boolean anotherSet;
    private String ParentItemTitle;
    private List<ExerciseChild> ChildItemList;


    private int position;

    int reps;

    public Exercise () {
    }

    public Exercise(String title, String weight, String time, int reps) {
        this.title = title;
        this.weight = weight;
        this.time = time;
        this.reps = reps;

    }

    public Exercise(String title,List<ExerciseChild> ChildItemList ) {
        this.title = title;
        this.ChildItemList = ChildItemList;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }



    public List<ExerciseChild> getChildItemList()
    {
        return ChildItemList;
    }

    public void setChildItemList(
            List<ExerciseChild> childItemList)
    {
        ChildItemList = childItemList;
    }
}
