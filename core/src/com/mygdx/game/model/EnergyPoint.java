package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.mygdx.game.model.Modules.EnergyModule;
import com.mygdx.game.utils.ModuleType;

/**
 * Created by Sash on 17.10.2018.
 */

public class EnergyPoint {
    private WeldJointDef jointDef;
    private Joint joint;
    private EnergyModule energyModule;
    private World world;
    private Vector2 localAnchor;

    public EnergyPoint(World world) {
        this.world = world;
    }

    public EnergyPoint(EnergyModule energyModule, Vector2 localAnchor) {

        this.energyModule = energyModule;
        energyModule.setTransform(energyModule.getX() + localAnchor.x, energyModule.getY() + localAnchor.y, (float) Math.toDegrees(energyModule.getAngle()));
        this.localAnchor = localAnchor;
        jointDef = new WeldJointDef();

        jointDef.localAnchorA.set(localAnchor.x, localAnchor.y);

        jointDef.collideConnected = false;

    }

    public boolean installModule(EnergyModule energyModule, Body shipBody, Vector2 localAnchor) {
        if (energyModule.getType().equals(ModuleType.EnergyModule)) {
            this.energyModule = energyModule;
            energyModule.setTransform(energyModule.getX() + localAnchor.x, energyModule.getY() + localAnchor.y, (float) Math.toDegrees(energyModule.getAngle()));
            this.localAnchor = localAnchor;
            jointDef = new WeldJointDef();
            jointDef.bodyA = shipBody;
            jointDef.bodyB = energyModule.getBody();
            jointDef.localAnchorA.set(localAnchor.x, localAnchor.y);
            jointDef.collideConnected = false;
            joint = world.createJoint(jointDef);

            return true;
        } else return false;
    }

    public void create(TextureAtlas textureAtlas, World world, Body shipBody) {
        energyModule.create(textureAtlas, world);
        jointDef.bodyA = shipBody;
        jointDef.bodyB = energyModule.getBody();
        joint = world.createJoint(jointDef);
        this.world = world;
    }

    public void create(World world, Body shipBody) {
        energyModule.create(world);
        jointDef.bodyA = shipBody;
        jointDef.bodyB = energyModule.getBody();
        joint = world.createJoint(jointDef);
        this.world = world;
    }

    public void destroy() {
        //world.destroyJoint(joint);
        energyModule.destroy();
    }

    public void draw(SpriteBatch batch) {
        if (energyModule != null) {
            energyModule.draw(batch);
        }
    }

    void toShip(float x, float y, float rotation)//телепортирует тело к кораблю на расстояние нормального смещения
    {//на вход передаются координаты корабля
        energyModule.setTransform(x + localAnchor.x * (float) (Math.cos(Math.toRadians(rotation))) - localAnchor.y * (float) (Math.sin(Math.toRadians(rotation))),
                y + localAnchor.y * (float) (Math.cos(Math.toRadians(rotation))) + localAnchor.x * (float) (Math.sin(Math.toRadians(rotation))), rotation);
    }

    public EnergyModule getEnergyModule() {
        return energyModule;
    }
}
