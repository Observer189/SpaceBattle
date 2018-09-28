package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.mygdx.game.Modules.WeaponModule;
import com.mygdx.game.utils.ModuleType;

/**
 * Created by Sash on 21.09.2018.
 */

public class WeaponPoint {
    WeldJointDef jointDef;
    Joint joint;
    WeaponModule weapon;
    World world;

    public WeaponPoint(World world)
    {
        this.world=world;
    }
    public WeaponPoint(WeaponModule weapon, Body shipBody,Vector2 localAnchor,World world)
    {
        this.world=world;
        this.weapon=weapon;
        jointDef = new WeldJointDef();
        jointDef.bodyA = shipBody;
        jointDef.bodyB = weapon.getBody();
        jointDef.localAnchorA.set(shipBody.getLocalCenter().x+localAnchor.x,shipBody.getLocalCenter().y+localAnchor.y);

        jointDef.collideConnected = false;
        joint=world.createJoint(jointDef);
    }
    public boolean installModule(WeaponModule weapon, Body shipBody,Vector2 localAnchor)
    {
        if(weapon.getType().equals(ModuleType.Weapon))
        {
            this.weapon=weapon;
            jointDef = new WeldJointDef();
            jointDef.bodyA = shipBody;
            jointDef.bodyB = weapon.getBody();
            jointDef.localAnchorA.set(shipBody.getLocalCenter().x+localAnchor.x,shipBody.getLocalCenter().y+localAnchor.y);
            jointDef.collideConnected = false;
            joint=world.createJoint(jointDef);

            return true;
        }
        else return false;
    }
    public void draw(SpriteBatch batch)
    {
        if(weapon!=null)
        {
            weapon.draw(batch);
        }
    }
}
