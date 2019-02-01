package com.mygdx.game.ServModels;

/**
 * Created by Sash on 14.11.2018.
 */

public class ServObject {
    private String name;
    private float x;
    private float y;
    private float height;
    private float width;
    private float rotation;

    ServObject() {

    }

    public ServObject(String name, float x, float y, float height, float width, float rotation) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }

    ServObject(ServObject obj) {
        this.name = obj.name;
        this.x = obj.x;
        this.y = obj.y;
        this.width = obj.width;
        this.height = obj.height;
        this.rotation = obj.rotation;
    }

    public String getName() {
        return name;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
