package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Sash on 24.10.2018.
 */

public class Asteroid extends PhysicObject {
    private float maxHp;
    private float hp;
    public Asteroid(TextureRegion textureRegion, float x, float y, float width, float height, float density, int bodiesNumber, float[][] shape, Vector2 startVelocity,float maxHp, World world) {
        super(textureRegion, x, y, width, height, density, bodiesNumber, shape, world);
        this.maxHp=maxHp;
        hp=maxHp;
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
}
