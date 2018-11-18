package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.ServModels.ServAsteroid;
import com.mygdx.game.ServModels.ServObject;

/**
 * Created by Sash on 24.10.2018.
 */

public class Asteroid extends PhysicObject {
    private float maxHp;
    private float hp;
    int bodiesNumber;
    Vector2 startVelocity;
    public Asteroid(String spriteName, float x, float y,float rotation, float width, float height, float density, int bodiesNumber, float[][] shape, Vector2 startVelocity,float maxHp) {
        super(spriteName, x, y,rotation, width, height, density, bodiesNumber, shape);
        this.maxHp=maxHp;
        hp=maxHp;
        this.bodiesNumber=bodiesNumber;
        this.startVelocity=startVelocity;
    }
    public Asteroid()
    {

    }

    @Override
    public void create(TextureAtlas textureAtlas, World world) {
        super.create(textureAtlas, world);
        for (int i=0;i<bodiesNumber;i++)
        {
            getBody().setLinearVelocity(startVelocity);
            getBody().setUserData(this);
        }
    }

    public void update()
    {

    }
    public void hurt(PhysicAmmo ammo)
    {
        hp-=ammo.getDamage();

    }
    @Override
    public String toString() {
        return "Asteroid";
    }

    public float getHp() {
        return hp;
    }

    @Override
    public ServAsteroid toServ() {
        ServAsteroid ast=new ServAsteroid();
        ast.setX(getX());
        ast.setY(getY());
        ast.setWidth(getWidth());
        ast.setHeight(getHeight());
        ast.setRotation(getRotation());
        ast.setHp(hp);
        return ast;
    }

}
