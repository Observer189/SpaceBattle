package com.mygdx.game.model;

import com.mygdx.game.ServModels.ServPlayer;

/**
 * Created by Sash on 31.10.2018.
 */

public class Player {
    private String name;
    private int conId;
    private PhysicShip currentShip;

    public void generateName()
    {
        name="Player"+(int)(Math.random()*10000000);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentShip(PhysicShip currentShip) {
        this.currentShip = currentShip;
    }

    public int getConId() {
        return conId;
    }

    public void setConId(int conId) {
        this.conId = conId;
    }

    public PhysicShip getCurrentShip() {
        return currentShip;
    }
    public ServPlayer toServ()
    {
        ServPlayer sp=new ServPlayer();
        sp.setName(name);
        sp.setCurrentShip(currentShip.toServ());
        return sp;
    }
    public static Player fromServ(ServPlayer servPlayer)
    {
        Player player= new Player();
        player.setName(servPlayer.getName());
        player.setConId(servPlayer.getConID());
        player.setCurrentShip(PhysicShip.fromServ(servPlayer.getCurrentShip()));
        return player;
    }
}
