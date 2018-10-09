package com.mygdx.game.Modules;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.Module;
import com.mygdx.game.utils.ModuleType;
import com.mygdx.game.utils.Size;

/**
 * Created by Sash on 09.10.2018.
 */

public class Engine extends Module {
    private float power;
    private float angularVelocity;
    private float maxSpeed;

    public Engine(TextureRegion textureRegion, float x, float y, Size size, float density,float power,float angularVelocity,float maxSpeed, World world) {
        super(textureRegion, x, y, size, ModuleType.Engine, density, world);
        this.power=power;
        this.angularVelocity=angularVelocity;
        this.maxSpeed=maxSpeed;
    }
    public void move(Vector2 movementVector)
    {
        getBody().applyForceToCenter(power*movementVector.x,power*movementVector.y,true);


    }
}
