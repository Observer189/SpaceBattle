package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Sash on 21.10.2018.
 */

public class PhysicAmmo extends PhysicObject {
    private float damage;

    public PhysicAmmo(String spriteName, float x, float y,float rotation, float width, float height, float density, int bodiesNumber, float[][] shape,float damage) {
        super(spriteName, x, y,rotation, width, height, density, bodiesNumber, shape);
        this.damage=damage;

    }

    @Override
    public void create(TextureAtlas textureAtlas, World world) {
        super.create(textureAtlas, world);
        getBody().setUserData(this);
    }

    public void update()
    {

    }

    @Override
    public String toString() {
        return "Ammo";
    }

    public float getDamage() {
        return damage;
    }
}
