package com.mygdx.game.ServModels;

/**
 * Created by Sash on 14.11.2018.
 */

public class ServAsteroid extends ServObject {
    private float hp;

    public ServAsteroid() {
        super();
    }

    public ServAsteroid(String name, float x, float y, float height, float width, float rotation, float hp) {
        super(name, x, y, height, width, rotation);
        this.hp = hp;

    }

    public ServAsteroid(ServAsteroid ast) {
        super(ast);
        this.hp = ast.hp;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }
}
