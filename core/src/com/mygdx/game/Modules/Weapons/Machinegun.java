package com.mygdx.game.Modules.Weapons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Modules.WeaponModule;


import com.mygdx.game.model.PhysicAmmos.Bullet;
import com.mygdx.game.utils.Size;

/**
 * Created by Sash on 21.10.2018.
 */

public class Machinegun extends WeaponModule {
    TextureAtlas textureAtlas;
    World world;

    long lastShotTime;
    public Machinegun(TextureAtlas textureAtlas, float x, float y, World world) {
        super(textureAtlas.findRegion("Machinegun"), x, y, Size.Small, 10,10,100,0.5f, world);
        this.textureAtlas=textureAtlas;
        this.world=world;
        lastShotTime=0;
    }

    @Override
    public boolean shot(float l, Vector2 speedVector) {
        if(System.currentTimeMillis()-lastShotTime>getReloadTime()*1000) {

            getAmmos().add(new Bullet(textureAtlas, getBody().getPosition().x - (float) Math.sin(getBody().getAngle())*l-(float) Math.sin(getBody().getAngle())*0.05f,
                    getBody().getPosition().y + (float) Math.cos(getBody().getAngle())*l+(float) Math.cos(getBody().getAngle())*0.125f,
                    getBody().getAngle(), 0.1f, 0.25f,
                    10,speedVector, getDamage(), world));
            lastShotTime=System.currentTimeMillis();
            return true;
        }
        else
        super.shot(l,speedVector);
        return false;
    }
}
