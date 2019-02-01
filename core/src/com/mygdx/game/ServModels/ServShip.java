package com.mygdx.game.ServModels;

/**
 * Created by Sash on 18.11.2018.
 */

public class ServShip extends ServObject {
    private int id;

    public ServShip() {
        super();
    }

    public ServShip(ServShip ship) {
        setName(ship.getName());
        setX(ship.getX());
        setY(ship.getY());
        setWidth(ship.getWidth());
        setHeight(ship.getHeight());
        setRotation(ship.getRotation());
        setId(ship.getId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
