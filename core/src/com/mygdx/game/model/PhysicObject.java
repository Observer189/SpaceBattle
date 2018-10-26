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
    Body body;
    Fixture[] fixtures;
    Joint[] joints;
    BodyDef bDef;
    //FixtureDef fDef;
    PolygonShape shape;
    World world;


    private float width;
    private float height;

    boolean mustDestroyed;
    public PhysicObject(TextureRegion textureRegion, float x, float y, float width, float height,float density,int bodiesNumber,float[][] shape, World world)
    {
        this.width=width;
        this.height=height;
        this.world=world;
        bDef=new BodyDef();
        bDef.type= BodyDef.BodyType.DynamicBody;
        bDef.position.set(x,y);

        body=world.createBody(bDef);
        fixtures= new Fixture[bodiesNumber];
        for(int i=0;i<bodiesNumber;i++) {



        FixtureDef fDef=new FixtureDef();
        this.shape=new PolygonShape();
            //this.shape.set(new float[]{-width/2,-height/2,width/2,-height/2,width/2,height/2,-width/2,height/2});
            //this.shape.set(new float[]{-1/2,-1/2,1/2,-1/2,1/2,1/2,-1/2,1/2});
        this.shape.set(shape[i]);



        fDef.shape=this.shape;



        fDef.density=density;
        fDef.restitution=0.2f;
        fDef.friction=0.5f;
        fixtures[i]=body.createFixture(fDef);
        /*if(i!=0) {
            WeldJointDef jointDef;
            jointDef = new WeldJointDef();
            jointDef.bodyA = bodies[i-1];
            jointDef.bodyB = bodies[i];

            jointDef.collideConnected = true;

            joints = new WeldJoint[bodies.length-1];

            joints[i-1] = world.createJoint(jointDef);
        }*/


        }



        sprite = new Sprite(textureRegion);
        sprite.setSize(width,height);
        sprite.setOrigin(width/2,height/2);
        sprite.setPosition(x-width/2,y-height/2);

        mustDestroyed=false;
    }
    public void draw(SpriteBatch batch)
    {
        sprite.setPosition(body.getPosition().x-width/2,body.getPosition().y-height/2);
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }
    public float getX()
    {
        return body.getPosition().x;
    }
    public float getY()
    {
        return body.getPosition().y;
    }

    /*public Body getBody() {
        return body;
    }*/

    public Sprite getSprite() {
        return sprite;
    }



    public Body getBody() {
        return body;
    }

    public float getRotation()
    {
        return body.getAngle();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setMustDestroyed(boolean mustDestroyed) {
        this.mustDestroyed = mustDestroyed;
    }
    public boolean getMustDestroyed() {
        return mustDestroyed;
    }

    public void destroy()
    {
        world.destroyBody(body);
    }
}
