package com.mygdx.game.model;

/**
 * Created by Sash on 31.10.2018.
 */

public class Player {
    private String name;

    public void generateName()
    {
        name="Player"+(int)Math.random()*10000000;
    }

    public String getName() {
        return name;
    }


}
