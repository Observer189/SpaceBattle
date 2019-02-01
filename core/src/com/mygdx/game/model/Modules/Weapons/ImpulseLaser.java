package com.mygdx.game.model.Modules.Weapons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.Modules.WeaponModule;
import com.mygdx.game.model.PhysicAmmos.LaserAmmo;
import com.mygdx.game.utils.Size;

/**
 * Created by Sash on 28.10.2018.
 */

public class ImpulseLaser extends WeaponModule {


    private long lastShotTime;

    public ImpulseLaser(float x, float y, float rotation) {
        super("BlueImpulseLaser", x, y, rotation, Size.Small, 10, 15, 75, 0.2f);


        lastShotTime = 0;
    }

    @Override
    public boolean shot(float l, Vector2 speedVector) {
        if (System.currentTimeMillis() - lastShotTime > getReloadTime() * 1000) {

            getAmmos().add(new LaserAmmo(getBody().getPosition().x - (float) Math.sin(getBody().getAngle()) * l * 1.2f - (float) Math.sin(getBody().getAngle()) * 0.35f,
                    getBody().getPosition().y + (float) Math.cos(getBody().getAngle()) * l * 1.2f + (float) Math.cos(getBody().getAngle()) * 0.35f,
                    getBody().getAngle(), 0.05f, 0.7f,
                    25, speedVector, getDamage()));
            getAmmos().get(getAmmos().size - 1).create(getTextureAtlas(), getWorld());
            lastShotTime = System.currentTimeMillis();
            return true;
        } else
            super.shot(l, speedVector);
        return false;
    }
}
