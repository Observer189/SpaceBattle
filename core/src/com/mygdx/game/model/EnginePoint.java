package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.mygdx.game.Modules.Engine;
import com.mygdx.game.Modules.WeaponModule;
import com.mygdx.game.utils.ModuleType;

/**
 * Created by Sash on 09.10.2018.
 */

public class EnginePoint {
    WeldJointDef jointDef;
    Joint joint;
    Engine engine;
    World world;

    public EnginePoint(World world)
    {
        this.world=world;
    }
    public EnginePoint(Engine engine, Body shipBody, Vector2 localAnchor, World world)
    {
        this.world=world;
        this.engine=engine;
        jointDef = new WeldJointDef();
        jointDef.bodyA = shipBody;
        jointDef.bodyB = engine.getBody();
        jointDef.localAnchorA.set(shipBody.getLocalCenter().x+localAnchor.x,shipBody.getLocalCenter().y+localAnchor.y);

        jointDef.collideConnected = false;
        joint=world.createJoint(jointDef);
    }
    public boolean installModule(Engine engine, Body shipBody,Vector2 localAnchor)
    {
        if(engine.getType().equals(ModuleType.Engine))
        {
            this.engine=engine;
            jointDef = new WeldJointDef();
            jointDef.bodyA = shipBody;
            jointDef.bodyB = engine.getBody();
            jointDef.localAnchorA.set(shipBody.getLocalCenter().x+localAnchor.x,shipBody.getLocalCenter().y+localAnchor.y);
            jointDef.collideConnected = false;
            joint=world.createJoint(jointDef);

            return true;
        }
        else return false;
    }
    public void move(Vector2 movementVector)
    {
        engine.move(movementVector);
    }
    public void draw(SpriteBatch batch)
    {
        if(engine!=null)
        {
            engine.draw(batch);
        }
    }

    public Engine getEngine() {
        return engine;
    }
}
