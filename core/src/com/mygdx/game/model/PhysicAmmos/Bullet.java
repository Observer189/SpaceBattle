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

    public Bullet(TextureAtlas textureAtlas, float x, float y, float startAngle, float width, float height, float speed, Vector2 shipSpeedVector, float damage, World world) {
        super(textureAtlas.findRegion("Bullet"), x, y, width, height, 1000, 1, new float[][]{{
                -width/2,-height/2,
                width/2,-height/2,
                width/2,height/2,
                -width/2,height/2}}, damage, world);

            getBody().setTransform(x,y,startAngle);
            getBody().setLinearVelocity((float)(-Math.sin(getRotation()))*speed+shipSpeedVector.x,(float)(Math.cos(getRotation()))*speed+shipSpeedVector.y);

    }

    @Override
    public void update() {
        super.update();

    }
}
