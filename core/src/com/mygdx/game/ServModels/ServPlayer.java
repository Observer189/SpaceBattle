package com.mygdx.game.ServModels;

/**
 * Created by Sash on 21.11.2018.
 */

public class ServPlayer {
    private int conID;
    private String name;
    private ServShip currentShip;

    public ServPlayer() {

    }

    public ServPlayer(int conID) {
        this.conID = conID;
    }

    public void setCurrentShip(ServShip currentShip) {
        this.currentShip = currentShip;
    }

    public ServShip getCurrentShip() {
        return currentShip;
    }

    public void setConID(int conID) {
        this.conID = conID;
    }

    public int getConID() {
        return conID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
