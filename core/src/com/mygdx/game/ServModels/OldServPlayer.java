package com.mygdx.game.ServModels;

/**
 * Created by Sash on 25.05.2018.
 */

public class OldServPlayer {
    String name;
    String password;
    int money;
    String shipName;
    public OldServPlayer() {}

    public OldServPlayer(String name, String shipName)
    {
        this.name=name;
        this.shipName=shipName;
    }
    public OldServPlayer(String name, String password, int money, String shipName)
    {
        this.name=name;
        this.password=password;
        this.money=money;
        this.shipName=shipName;
    }
    public OldServPlayer(OldServPlayer servPlayer)
    {
        this.name=servPlayer.getName();
        this.password=servPlayer.getPassword();
        this.money=servPlayer.getMoney();
        this.shipName=servPlayer.getShipName();
    }

    @Override
    public boolean equals(Object obj) {
        return ((OldServPlayer)obj).name.equals(name);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getShipName() {
        return shipName;
    }
    public void setShipName(String shipName) {
        this.shipName = shipName;
    }
    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }

    public void setServPlayer(OldServPlayer servPlayer)
    {
        this.name=servPlayer.getName();
        this.password=servPlayer.getPassword();
        this.money=servPlayer.getMoney();
        this.shipName=servPlayer.getShipName();

    }

}
