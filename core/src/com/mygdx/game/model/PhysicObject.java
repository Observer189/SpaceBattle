package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

/**
 * Created by Sash on 15.06.2018.
 */

public class PhysicObject {
    Sprite sprite;
    Body[] bodies;
    Fixture[] fixtures;
    Joint[] joints;
    BodyDef bDef;
    //FixtureDef fDef;
    PolygonShape shape;

    private float width;
    private float height;
    public PhysicObject(TextureRegion textureRegion, float x, float y, float width, float height,float density,int bodiesNumber,float[][] shape, World world)
    {
        this.width=width;
        this.height=height;
        bodies = new Body[bodiesNumber];
        fixtures= new Fixture[bodiesNumber];
        for(int i=0;i<bodies.length;i++) {
        bDef=new BodyDef();
        bDef.type= BodyDef.BodyType.DynamicBody;
        bDef.position.set(x,y);

        bodies[i]=world.createBody(bDef);


        FixtureDef fDef=new FixtureDef();
        this.shape=new PolygonShape();
            //this.shape.set(new float[]{-width/2,-height/2,width/2,-height/2,width/2,height/2,-width/2,height/2});
            //this.shape.set(new float[]{-1/2,-1/2,1/2,-1/2,1/2,1/2,-1/2,1/2});
        this.shape.set(shape[i]);



        fDef.shape=this.shape;



        fDef.density=density;
        fDef.restitution=0.2f;
        fDef.friction=0.5f;
        fixtures[i]=bodies[i].createFixture(fDef);
        if(i!=0) {
            WeldJointDef jointDef;
            jointDef = new WeldJointDef();
            jointDef.bodyA = bodies[i-1];
            jointDef.bodyB = bodies[i];

            jointDef.collideConnected = true;

            joints = new WeldJoint[bodies.length-1];

            joints[i-1] = world.createJoint(jointDef);
        }


        }



        sprite = new Sprite(textureRegion);
        sprite.setSize(width,height);
        sprite.setOrigin(width/2,height/2);
        sprite.setPosition(x-width/2,y-height/2);
    }
    public void draw(SpriteBatch batch)
    {
        sprite.setPosition(bodies[0].getPosition().x-width/2,bodies[0].getPosition().y-height/2);
        sprite.setRotation((float)Math.toDegrees(bodies[0].getAngle()));
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }
    public float getX()
    {
        return bodies[0].getPosition().x;
    }
    public float getY()
    {
        return bodies[0].getPosition().y;
    }

    /*public Body getBody() {
        return body;
    }*/

    public Sprite getSprite() {
        return sprite;
    }



    public Body[] getBodies() {
        return bodies;
    }

    public float getRotation()
    {
        return bodies[0].getAngle();
    }
}
