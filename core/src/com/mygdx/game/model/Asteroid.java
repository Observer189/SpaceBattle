package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Sash on 24.10.2018.
 */

public class Asteroid extends PhysicObject {
    private float hp;
    public Asteroid(TextureRegion textureRegion, float x, float y, float width, float height, float density, int bodiesNumber, float[][] shape, Vector2 startVelocity, World world) {
        super(textureRegion, x, y, width, height, density, bodiesNumber, shape, world);
        for (int i=0;i<bodiesNumber;i++)
        {
            getBody().setLinearVelocity(startVelocity);
            getBody().getFixtureList().get(0).setUserData("Asteroid");
        }
    }
}
