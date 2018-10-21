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
    Vector2 localAnchor;

    public WeaponPoint(World world)
    {
        this.world=world;
    }
    public WeaponPoint(WeaponModule weapon, Body shipBody,Vector2 localAnchor,World world)
    {
        this.world=world;
        this.weapon=weapon;
        this.localAnchor=localAnchor;
        jointDef = new WeldJointDef();
        jointDef.bodyA = shipBody;
        jointDef.bodyB = weapon.getBody();
        jointDef.localAnchorA.set(localAnchor.x,localAnchor.y);
        jointDef.localAnchorB.set(0,0);
        jointDef.collideConnected = false;
        joint=world.createJoint(jointDef);
    }
    public boolean installModule(WeaponModule weapon, Body shipBody,Vector2 localAnchor)
    {
        if(weapon.getType().equals(ModuleType.Weapon))
        {
            this.weapon=weapon;
            this.localAnchor=localAnchor;
            jointDef = new WeldJointDef();
            jointDef.bodyA = shipBody;
            jointDef.bodyB = weapon.getBody();
            jointDef.localAnchorA.set(localAnchor.x,localAnchor.y);
            jointDef.collideConnected = false;
            joint=world.createJoint(jointDef);

            return true;
        }
        else return false;
    }
    public void shot(float l)
    {
        weapon.shot(l);
    }
    public void draw(SpriteBatch batch)
    {
        if(weapon!=null)
        {
            weapon.draw(batch);
        }
    }

    public Vector2 getLocalAnchor() {
        return localAnchor;
    }
}
