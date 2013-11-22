package simcity.gui;

import java.awt.*;

import simcity.Location;

public interface Gui {

    public void updatePosition();
    // if we make this a class:
    /* 
     * everyone has xPos, yPos, (or a Location pos), xVel, yVel (or a vector)
     * updatePosition(long dt) {
     * 	point = point+vel*dt;
     * 
     * 
     */
    public void draw(Graphics2D g);
    public boolean isPresent();
    public boolean contains(Point point);
    public Location getLocation();

}