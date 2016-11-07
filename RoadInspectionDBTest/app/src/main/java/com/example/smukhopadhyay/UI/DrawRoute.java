package com.example.smukhopadhyay.UI;

import com.example.smukhopadhyay.models.Intersection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by smukhopadhyay on 10/22/16.
 */
public class DrawRoute {

    private GoogleMap map;
    private LinkedList<Intersection> route;
    private Intersection actualStart;
    private Intersection actualFinish;

    public DrawRoute(GoogleMap map, LinkedList<Intersection> route, Intersection actualStart, Intersection actualFinish) {
        this.map = map;
        this.route = route;
        this.actualStart = actualStart;
        this.actualFinish = actualFinish;
    }

    public void execute() {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        ListIterator<Intersection> iter = route.listIterator(0);
        Intersection start = iter.next();
        builder.include(new LatLng(Double.parseDouble(start.getLat()), Double.parseDouble(start.getLng())));

        // Draw the route
        while (iter.hasNext()) {
            Intersection finish = iter.next();
            builder.include(new LatLng(Double.parseDouble(finish.getLat()), Double.parseDouble(finish.getLat())));
            drawPolyline(start, finish);
            start = finish;
        }

        // Zoom to the start
        zoomToStart(actualStart);
    }

    private void drawPolyline(Intersection start, Intersection finish) {
        LatLng startLatLng = new LatLng(Double.parseDouble(start.getLat()), Double.parseDouble(start.getLng()));
        LatLng finishLatLng = new LatLng(Double.parseDouble(finish.getLat()), Double.parseDouble(finish.getLng()));

        PolylineOptions polylineOptions = new PolylineOptions()
                .add(startLatLng)
                .add(finishLatLng);

        map.addPolyline(polylineOptions);
    }

    private void zoomToStart(Intersection start) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(Double.parseDouble(start.getLat()), Double.parseDouble(start.getLng())))
                .zoom(18)
                .tilt(30)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
