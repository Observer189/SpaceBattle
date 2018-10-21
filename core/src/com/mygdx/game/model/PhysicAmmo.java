package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Sash on 21.10.2018.
 */

public class PhysicAmmo extends PhysicObject {
    private float damage;

    public PhysicAmmo(TextureRegion textureRegion, float x, float y, float width, float height, float density, int bodiesNumber, float[][] shape,float damage, World world) {
        super(textureRegion, x, y, width, height, density, bodiesNumber, shape, world);
        this.damage=damage;
    }
    public void update()
    {

    }
}
