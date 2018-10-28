package com.mygdx.game.Modules.Weapons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Modules.WeaponModule;
import com.mygdx.game.model.PhysicAmmos.Bullet;
import com.mygdx.game.model.PhysicAmmos.LaserAmmo;
import com.mygdx.game.utils.Size;

/**
 * Created by Sash on 28.10.2018.
 */

public class ImpulseLaser extends WeaponModule {
    TextureAtlas textureAtlas;
    World world;

    long lastShotTime;
    public ImpulseLaser(TextureAtlas textureAtlas, float x, float y, World world) {
        super(textureAtlas.findRegion("BlueImpulseLaser"), x, y, Size.Small, 10,15,75,0.2f, world);
        this.textureAtlas=textureAtlas;
        this.world=world;
        lastShotTime=0;
    }

    @Override
    public boolean shot(float l, Vector2 speedVector) {
        if(System.currentTimeMillis()-lastShotTime>getReloadTime()*1000) {

            getAmmos().add(new LaserAmmo(textureAtlas, getBody().getPosition().x - (float) Math.sin(getBody().getAngle())*l-(float) Math.sin(getBody().getAngle())*0.05f,
                    getBody().getPosition().y + (float) Math.cos(getBody().getAngle())*l+(float) Math.cos(getBody().getAngle())*0.125f,
                    getBody().getAngle(), 0.05f, 0.7f,
                    25,speedVector, getDamage(), world));
            lastShotTime=System.currentTimeMillis();
            return true;
        }
        else
            super.shot(l,speedVector);
        return false;
    }
}
