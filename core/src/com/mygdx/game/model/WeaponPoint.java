package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.mygdx.game.model.Modules.WeaponModule;
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
    Body shipBody;
    public WeaponPoint(World world)
    {
        this.world=world;
    }
    public WeaponPoint(WeaponModule weapon,Vector2 localAnchor)
    {

        this.weapon=weapon;
        weapon.setTransform(weapon.getX()+localAnchor.x,weapon.getY()+localAnchor.y,(float) Math.toDegrees(weapon.getAngle()));//смещаем координату тела до нужного положения перед его созданием
        //следует также учитывать, что при создании любого корабля мы передаем телу его координаты
        this.localAnchor=localAnchor;
        jointDef = new WeldJointDef();
        this.shipBody=shipBody;
        jointDef.bodyA = shipBody;
        jointDef.bodyB = weapon.getBody();
        jointDef.localAnchorA.set(localAnchor.x,localAnchor.y);
        jointDef.localAnchorB.set(0,0);
        jointDef.collideConnected = false;

    }
    public boolean installModule(WeaponModule weapon, Body shipBody,Vector2 localAnchor)
    {
        if(weapon.getType().equals(ModuleType.Weapon))
        {
            this.weapon=weapon;
            weapon.setTransform(weapon.getX()+localAnchor.x,weapon.getY()+localAnchor.y,(float) Math.toDegrees(weapon.getAngle()));//смещаем координату тела до нужного положения перед его созданием
            this.localAnchor=localAnchor;
            jointDef = new WeldJointDef();
            jointDef.bodyA = shipBody;
            jointDef.bodyB = weapon.getBody();
            jointDef.localAnchorA.set(localAnchor.x,localAnchor.y);//указываем координату точки корабля к которому прекрепляем тело
            jointDef.collideConnected = false;
            joint=world.createJoint(jointDef);

            return true;
        }
        else return false;
    }
    public void create(TextureAtlas textureAtlas,World world,Body shipBody)
    {
        weapon.create(textureAtlas,world);
        jointDef.bodyA = shipBody;
        jointDef.bodyB = weapon.getBody();
        joint=world.createJoint(jointDef);
        this.world=world;
    }
    public void create(World world,Body shipBody)
    {
        weapon.create(world);
        jointDef.bodyA = shipBody;
        jointDef.bodyB = weapon.getBody();
        joint=world.createJoint(jointDef);
        this.world=world;
    }
    public boolean shot(float l,Vector2 speedVector)
    {
        return weapon.shot(l,speedVector);
    }
    public void draw(SpriteBatch batch)
    {
        if(weapon!=null)
        {
            weapon.draw(batch);
        }
    }
    public void destroy()
    {
        //world.destroyJoint(joint);
        destroyAmmo();
        weapon.destroy();
    }
    public void toShip(float x,float y,float rotation)//телепортирует тело к кораблю на расстояние нормального смещения
    {//на вход передаются координаты корабля
        weapon.setTransform(x+localAnchor.x*(float)(Math.cos(Math.toRadians(rotation)))-localAnchor.y*(float)(Math.sin(Math.toRadians(rotation))),
                y+localAnchor.y*(float)(Math.cos(Math.toRadians(rotation)))+localAnchor.x*(float)(Math.sin(Math.toRadians(rotation))),rotation);
    }
    public void destroyAmmo()
    {
        weapon.destroyAmmo();
    }

    public Vector2 getLocalAnchor() {
        return localAnchor;
    }
}
