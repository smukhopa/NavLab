package com.example.smukhopadhyay.Algorithms;

import com.example.smukhopadhyay.models.Intersection;
import com.example.smukhopadhyay.models.Path;
import com.example.smukhopadhyay.models.RoadNetwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by smukhopadhyay on 10/18/16.
 */
// Ref: http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html
public class Djikstra {

    private static List<Path> paths;
    private static List<Intersection> intersections = null;

    private static Set<Intersection> settledNodes;
    private static Set<Intersection> unsettledNodes;
    private static Map<Intersection, Double> distance;
    private static Map<Intersection, Intersection> predecessors;

    public static void shortestPath(RoadNetwork network, Intersection start) {

        paths = network.getPaths();
        intersections = network.getIntersections();

        settledNodes = new HashSet<Intersection>();
        unsettledNodes = new HashSet<Intersection>();

        distance = new HashMap<Intersection, Double>();
        predecessors = new HashMap<Intersection, Intersection>();

        distance.put(start, 0.0);
        unsettledNodes.add(start);

        while (unsettledNodes.size() > 0) {
            Intersection node = getMinimum(unsettledNodes);
            settledNodes.add(node);
            unsettledNodes.remove(node);
            findMinimalDistance(node);
        }
    }

    private static void findMinimalDistance(Intersection node) {
        List<Intersection> adjacentNodes = getNeighbors(node);

        for (Intersection target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unsettledNodes.add(target);
            }
        }
    }

    private static double getDistance(Intersection node, Intersection target) {
        for (Path path : paths) {
            if (path.getSource().equals(node) && path.getDestination().equals(target)) {
                return path.getWeight();
            }
        }
        throw new RuntimeException("SHOULD NOT HAPPEN");
    }

    private static List<Intersection> getNeighbors(Intersection node) {
        List<Intersection> neighbors = new ArrayList<>();
        for (Path path : paths) {
            if (path.getSource().equals(node) && !isSettled(path.getDestination())) {
                neighbors.add(path.getDestination());
            }
        }
        return neighbors;
    }

    private static boolean isSettled(Intersection intersection) {
        return settledNodes.contains(intersection);
    }

    private static Intersection getMinimum(Set<Intersection> intersections) {
        Intersection minimum = null;
        for (Intersection intersection : intersections) {
            if (minimum == null) {
                minimum = intersection;
            } else {
                if (getShortestDistance(intersection) < getShortestDistance(minimum)) {
                    minimum = intersection;
                }
            }
        }
        return minimum;
    }

    private static double getShortestDistance(Intersection destination) {
        Double d = distance.get(destination);
        if (d == null) {
            return Double.MAX_VALUE;
        } else {
            return d;
        }
    }

    /**
     * Method for returning the path from the source to the selected target and null if no paths
     * exist
     */
    public static LinkedList<Intersection> getRoute(Intersection target) {
        LinkedList<Intersection> route = new LinkedList<>();
        Intersection step = target;

        // Check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        route.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            route.add(step);
        }

        // Put it in the correct order
        Collections.reverse(route);
        return route;
    }
}
