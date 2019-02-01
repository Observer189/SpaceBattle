package com.mygdx.game.requests;

import com.mygdx.game.ServModels.ServAsteroidField;

/**
 * Created by Sash on 28.12.2018.
 */

public class ServBattleInfo {
    private float x;
    private float y;
    private float rotation;
    private long time;
    private ServAsteroidField saf;

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getRotation() {
        return rotation;
    }

    public float getY() {
        return y;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getX() {
        return x;
    }

    public ServAsteroidField getSaf() {
        return saf;
    }

    public void setSaf(ServAsteroidField saf) {
        this.saf = saf;
    }
}
