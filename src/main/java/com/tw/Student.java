package com.tw;

import java.util.Map;

public class Student {
    private Map<String, Double> classAndScore;
    private String name;
    private String StdId;

    public Student(Map<String, Double> classAndScore, String name, String stdId) {
        this.classAndScore = classAndScore;
        this.name = name;
        StdId = stdId;
    }

    public Map<String, Double> getClassAndScore() {
        return classAndScore;
    }

    public void setClassAndScore(Map<String, Double> classAndScore) {
        this.classAndScore = classAndScore;
    }

    public String getStdId() {
        return StdId;
    }

    public void setStdId(String stdId) {
        StdId = stdId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double calculateTotalScore(){
        double total = 0.0;
        for (String key: classAndScore.keySet()) {
            total+=classAndScore.get(key);
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder stdInfoBuilder = new StringBuilder();
        stdInfoBuilder.append(name).append("|");
        double total = 0.0;
        for (String key: classAndScore.keySet()) {
            total+=classAndScore.get(key);
            stdInfoBuilder.append(classAndScore.get(key)).append("|");
        }
        stdInfoBuilder.append(total/classAndScore.size()).append("|").append(total);
        return stdInfoBuilder.toString();
    }
}
