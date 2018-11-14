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
    WeldJointDef jointDef;
    Joint joint;
    EnergyModule energyModule;
    World world;

    public EnergyPoint(World world)
    {
        this.world=world;
    }
    public EnergyPoint(EnergyModule energyModule, Vector2 localAnchor)
    {

        this.energyModule=energyModule;
        jointDef = new WeldJointDef();

        jointDef.localAnchorA.set(localAnchor.x,localAnchor.y);

        jointDef.collideConnected = false;

    }
    public boolean installModule(EnergyModule energyModule, Body shipBody,Vector2 localAnchor)
    {
        if(energyModule.getType().equals(ModuleType.EnergyModule))
        {
            this.energyModule=energyModule;
            jointDef = new WeldJointDef();
            jointDef.bodyA = shipBody;
            jointDef.bodyB = energyModule.getBody();
            jointDef.localAnchorA.set(localAnchor.x,localAnchor.y);
            jointDef.collideConnected = false;
            joint=world.createJoint(jointDef);

            return true;
        }
        else return false;
    }
    public void create(TextureAtlas textureAtlas, World world,Body shipBody)
    {
        energyModule.create(textureAtlas,world);
        jointDef.bodyA = shipBody;
        jointDef.bodyB = energyModule.getBody();
        joint=world.createJoint(jointDef);
        this.world=world;
    }
    public void destroy()
    {
        //world.destroyJoint(joint);
        energyModule.destroy();
    }
    public void draw(SpriteBatch batch)
    {
        if(energyModule!=null)
        {
            energyModule.draw(batch);
        }
    }

    public EnergyModule getEnergyModule() {
        return energyModule;
    }
}
