package com.example.gymtrainingtool;
import java.io.Serializable;

public class Exercise implements Serializable{

    private static final long serialVersionUID = 1L;
    private String title,time,sets,weight;


    public Exercise () {
    }

    public Exercise(String title, String sets,String weight, String time) {
        this.title = title;
        this.sets = sets;
        this.weight = weight;
        this.time = time;
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

    public String getSets() { return sets; }

    public void setSets(String sets) { this.sets = sets; }

    public String getWeight() { return weight; }

    public void setWeight(String weight) { this.weight = weight; }
}
