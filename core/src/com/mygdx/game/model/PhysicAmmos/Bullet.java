package com.mygdx.game.model.PhysicAmmos;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.PhysicAmmo;

/**
 * Created by Sash on 21.10.2018.
 */

public class Bullet extends PhysicAmmo {
    static final float width=0.1f;
    static final float height=0.15f;
    public Bullet(TextureAtlas textureAtlas, float x, float y, float startAngle, float width, float height, float startPower, float damage, World world) {
        super(textureAtlas.findRegion("Bullet"), x, y, width, height, 1000, 1, new float[][]{{
                -width/2,-height/2,
                width/2,-height/2,
                width/2,height/2,
                -width/2,height/2}}, damage, world);
        for(Body i:getBodies())
        {
            i.setTransform(x,y,startAngle);
            i.applyForceToCenter((float)(-Math.sin(getRotation()))*startPower,(float)(Math.cos(getRotation()))*startPower,true);
        }
    }

    @Override
    public void update() {
        super.update();

    }
}
