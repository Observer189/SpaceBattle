package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.mygdx.game.model.Modules.Engine;
import com.mygdx.game.utils.ModuleType;

/**
 * Created by Sash on 09.10.2018.
 */

public class EnginePoint {
    private WeldJointDef jointDef;
    private Joint joint;
    private Engine engine;
    private World world;

    private Vector2 localAnchor;

    public EnginePoint(World world) {
        this.world = world;
    }

    public EnginePoint(Engine engine, Vector2 localAnchor) {

        this.engine = engine;
        engine.setTransform(engine.getX() + localAnchor.x, engine.getY() + localAnchor.y, (float) Math.toDegrees(engine.getAngle()));
        this.localAnchor = localAnchor;
        jointDef = new WeldJointDef();

        jointDef.localAnchorA.set(localAnchor.x, localAnchor.y);

        jointDef.collideConnected = false;

    }

    public boolean installModule(Engine engine, Body shipBody, Vector2 localAnchor) {
        if (engine.getType().equals(ModuleType.Engine)) {
            this.engine = engine;
            engine.setTransform(engine.getX() + localAnchor.x, engine.getY() + localAnchor.y, (float) Math.toDegrees(engine.getAngle()));
            this.localAnchor = localAnchor;
            jointDef = new WeldJointDef();
            jointDef.bodyA = shipBody;
            jointDef.bodyB = engine.getBody();
            jointDef.localAnchorA.set(localAnchor.x, localAnchor.y);
            jointDef.collideConnected = false;
            joint = world.createJoint(jointDef);

            return true;
        } else return false;
    }

    public void uninstallModule() {
        world.destroyJoint(joint);
        engine = null;
    }

    public void create(TextureAtlas textureAtlas, World world, Body shipBody) {
        engine.create(textureAtlas, world);
        jointDef.bodyA = shipBody;
        jointDef.bodyB = engine.getBody();
        joint = world.createJoint(jointDef);
        this.world = world;
    }

    public void create(World world, Body shipBody) {
        engine.create(world);
        jointDef.bodyA = shipBody;
        jointDef.bodyB = engine.getBody();
        joint = world.createJoint(jointDef);
        this.world = world;
    }

    void move(Vector2 movementVector) {
        if (engine != null) {
            engine.move(movementVector);
        }
    }

    void destroy() {
        //world.destroyJoint(joint);
        engine.destroy();
    }

    public void draw(SpriteBatch batch) {
        if (engine != null) {
            engine.draw(batch);
        }
    }

    void toShip(float x, float y, float rotation)//телепортирует тело к кораблю на расстояние нормального смещения
    {//на вход передаются координаты корабля
        engine.setTransform(x + localAnchor.x * (float) (Math.cos(Math.toRadians(rotation))) - localAnchor.y * (float) (Math.sin(Math.toRadians(rotation))),
                y + localAnchor.y * (float) (Math.cos(Math.toRadians(rotation))) + localAnchor.x * (float) (Math.sin(Math.toRadians(rotation))), rotation);
    }

    float getAngularPower() {
        return engine.getAngularPower();
    }

    public Engine getEngine() {
        if (engine != null)
            return engine;
        else return null;
    }
}
