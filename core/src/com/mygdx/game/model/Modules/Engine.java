package com.mygdx.game.model.Modules;

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
    private float angularPower;
    private float maxSpeed;
    private Type type;

    private float energyConsumption;

    public Engine(String spriteName, float x, float y, float rotation, Size size, float density, float power, float angularPower, float maxSpeed, float energyConsumption, Type type) {
        super(spriteName, x, y, rotation, size, ModuleType.Engine, density);
        this.power = power;
        this.angularPower = angularPower;
        this.maxSpeed = maxSpeed;
        this.energyConsumption = energyConsumption;
        this.type = type;

    }

    public void move(Vector2 movementVector) {
        if (type.equals(Type.Cruising)) {
            getBody().applyForceToCenter(power * movementVector.x, power * movementVector.y, true);
        }


    }

    public enum Type {
        Cruising
    }


    public Type getEngineType() {
        return type;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getPower() {
        return power;
    }

    public float getAngularPower() {
        return angularPower;
    }

    public float getEnergyConsumption() {
        return energyConsumption;
    }
}
