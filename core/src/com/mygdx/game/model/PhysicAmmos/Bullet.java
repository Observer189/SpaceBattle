package com.mygdx.game.model.PhysicAmmos;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.PhysicAmmo;

/**
 * Created by Sash on 21.10.2018.
 */

public class Bullet extends PhysicAmmo {
    private float x;
    private float y;
    private float startAngle;
    private float speed;
    private Vector2 shipSpeedVector;

    public Bullet(float x, float y, float startAngle, float width, float height, float speed, Vector2 shipSpeedVector, float damage) {
        super("Bullet", x, y, startAngle, width, height, 1000, 1, new float[][]{{
                -width / 2, -height / 2,
                width / 2, -height / 2,
                width / 2, height / 2,
                -width / 2, height / 2}}, damage);
        this.x = x;
        this.y = y;
        this.startAngle = startAngle;
        this.speed = speed;
        this.shipSpeedVector = shipSpeedVector;


    }

    @Override
    public void create(TextureAtlas textureAtlas, World world) {
        super.create(textureAtlas, world);
        getBody().setTransform(x, y, startAngle);
        getBody().setLinearVelocity((float) (-Math.sin(getBody().getAngle())) * speed + shipSpeedVector.x, (float) (Math.cos(getBody().getAngle())) * speed + shipSpeedVector.y);
    }

    @Override
    public void update() {
        super.update();

    }
}
