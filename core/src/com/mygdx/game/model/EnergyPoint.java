package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.mygdx.game.Modules.EnergyModule;
import com.mygdx.game.Modules.WeaponModule;
import com.mygdx.game.utils.ModuleType;

/**
 * Created by Sash on 17.10.2018.
 */

public class EnergyPoint {
    WeldJointDef jointDef;
    Joint joint;
    EnergyModule energyModule;
    World world;

    public EnergyPoint(World world)
    {
        this.world=world;
    }
    public EnergyPoint(EnergyModule energyModule, Body shipBody, Vector2 localAnchor, World world)
    {
        this.world=world;
        this.energyModule=energyModule;
        jointDef = new WeldJointDef();
        jointDef.bodyA = shipBody;
        jointDef.bodyB = energyModule.getBody();
        jointDef.localAnchorA.set(shipBody.getLocalCenter().x+localAnchor.x,shipBody.getLocalCenter().y+localAnchor.y);

        jointDef.collideConnected = false;
        joint=world.createJoint(jointDef);
    }
    public boolean installModule(EnergyModule energyModule, Body shipBody,Vector2 localAnchor)
    {
        if(energyModule.getType().equals(ModuleType.EnergyModule))
        {
            this.energyModule=energyModule;
            jointDef = new WeldJointDef();
            jointDef.bodyA = shipBody;
            jointDef.bodyB = energyModule.getBody();
            jointDef.localAnchorA.set(shipBody.getLocalCenter().x+localAnchor.x,shipBody.getLocalCenter().y+localAnchor.y);
            jointDef.collideConnected = false;
            joint=world.createJoint(jointDef);

            return true;
        }
        else return false;
    }
    public void draw(SpriteBatch batch)
    {
        if(energyModule!=null)
        {
            energyModule.draw(batch);
        }
    }
}
