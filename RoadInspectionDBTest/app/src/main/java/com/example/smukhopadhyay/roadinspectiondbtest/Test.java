package com.example.smukhopadhyay.roadinspectiondbtest;

import android.util.Log;

import com.example.smukhopadhyay.Algorithms.Djikstra;
import com.example.smukhopadhyay.models.Intersection;
import com.example.smukhopadhyay.models.Path;
import com.example.smukhopadhyay.models.RoadNetwork;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by smukhopadhyay on 10/18/16.
 */
public class Test {

    private static List<Intersection> nodes;
    private static List<Path> edges;

    public static void test() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Intersection location = new Intersection("Node_" + i, "abc", "abc");
            nodes.add(location);
        }

        addLane("Edge_0", 0, 1, 85);
        addLane("Edge_1", 0, 2, 217);
        addLane("Edge_2", 0, 4, 173);
        addLane("Edge_3", 2, 6, 186);
        addLane("Edge_4", 2, 7, 103);
        addLane("Edge_5", 3, 7, 183);
        addLane("Edge_6", 5, 8, 250);
        addLane("Edge_7", 8, 9, 84);
        addLane("Edge_8", 7, 9, 167);
        addLane("Edge_9", 4, 9, 502);
        addLane("Edge_10", 9, 10, 40);
        addLane("Edge_11", 1, 10, 600);

        RoadNetwork network = new RoadNetwork(nodes, edges);
        Djikstra.shortestPath(network, nodes.get(0));
        LinkedList<Intersection> route = Djikstra.getRoute(nodes.get(10));

        for (Intersection intersection : route) {
            Log.i("FINAL_PATH", intersection.getID());
        }


    }

    private static void addLane(String laneId, int sourceLocNo, int destLocNo, int duration) {
        Path lane = new Path(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
        edges.add(lane);
    }
}
