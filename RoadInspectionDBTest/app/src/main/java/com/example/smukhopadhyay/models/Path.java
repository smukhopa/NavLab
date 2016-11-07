package com.example.smukhopadhyay.models;

/**
 * Created by smukhopadhyay on 10/18/16.
 */
// Reference: http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html
public class Path {

    private String ID;
    private Intersection source;
    private Intersection destination;
    private double weight;

    public Path(String ID, Intersection source, Intersection destination, double weight) {
        this.ID = ID;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public String getID() {
        return ID;
    }

    public Intersection getSource() {
        return source;
    }

    public Intersection getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }
}
