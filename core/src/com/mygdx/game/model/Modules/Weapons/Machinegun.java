package com.mygdx.game.model.Modules.Weapons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.Modules.WeaponModule;


import com.mygdx.game.model.PhysicAmmos.Bullet;
import com.mygdx.game.utils.Size;

/**
 * Created by Sash on 21.10.2018.
 */

public class Machinegun extends WeaponModule {


    private long lastShotTime;

    public Machinegun(float x, float y, float rotation) {
        super("Machinegun", x, y, rotation, Size.Small, 10, 10, 0, 0.5f);

        lastShotTime = 0;
    }

    @Override
    public boolean shot(float l, Vector2 speedVector) {
        if (System.currentTimeMillis() - lastShotTime > getReloadTime() * 1000) {

            getAmmos().add(new Bullet(getBody().getPosition().x - (float) Math.sin(getBody().getAngle()) * l * 1.2f - (float) Math.sin(getBody().getAngle()) * 0.125f,
                    getBody().getPosition().y + (float) Math.cos(getBody().getAngle()) * l * 1.2f + (float) Math.cos(getBody().getAngle()) * 0.125f,
                    getBody().getAngle(), 0.1f, 0.25f,
                    10, speedVector, getDamage()));
            getAmmos().get(getAmmos().size - 1).create(getTextureAtlas(), getWorld());
            lastShotTime = System.currentTimeMillis();
            return true;
        } else
            super.shot(l, speedVector);
        return false;
    }
}
