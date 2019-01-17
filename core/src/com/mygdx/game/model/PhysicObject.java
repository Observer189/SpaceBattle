package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.mygdx.game.ServModels.ServObject;

/**
 * Created by Sash on 15.06.2018.
 */

public class PhysicObject {
    Sprite sprite;
    Body body;
    Fixture[] fixtures;
    Joint[] joints;
    BodyDef bDef;
    FixtureDef[] fDefs;
    //FixtureDef fDef;
    PolygonShape shape;
    World world;

    String spriteName;
    private float x;
    private float y;
    private float rotation;
    private float width;
    private float height;

    boolean mustDestroyed;
    int fixturesNumber;
    public PhysicObject(String spriteName, float x, float y,float rotation, float width, float height,float density,int fixturesNumber,float[][] shape)
    {
        this.x=x;
        this.y=y;
        this.rotation=rotation;
        this.width=width;
        this.height=height;
        this.spriteName=spriteName;
        this.fixturesNumber=fixturesNumber;
        bDef=new BodyDef();
        bDef.type= BodyDef.BodyType.DynamicBody;
        bDef.position.set(x,y);

        bDef.angle=(float) Math.toRadians(rotation);

        fDefs=new FixtureDef[fixturesNumber];
        fixtures= new Fixture[fixturesNumber];
        for(int i=0;i<fixturesNumber;i++) {



        FixtureDef fDef=new FixtureDef();
        this.shape=new PolygonShape();
            //this.shape.set(new float[]{-width/2,-height/2,width/2,-height/2,width/2,height/2,-width/2,height/2});
            //this.shape.set(new float[]{-1/2,-1/2,1/2,-1/2,1/2,1/2,-1/2,1/2});
        this.shape.set(shape[i]);



        fDef.shape=this.shape;



        fDef.density=density;
        fDef.restitution=0.2f;
        fDef.friction=0.5f;
        fDefs[i]=fDef;

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


    }
    public PhysicObject()
    {

    }
    public void create(TextureAtlas textureAtlas,World world) {
        this.world = world;
        body = world.createBody(bDef);
        for (int i = 0; i < fixturesNumber; i++) {
            fixtures[i]=body.createFixture(fDefs[i]);
        }
        sprite=new Sprite(textureAtlas.findRegion(spriteName));
        sprite.setSize(width,height);
        sprite.setOrigin(width/2,height/2);
        sprite.setPosition(x-width/2,y-height/2);

        mustDestroyed=false;
    }
    public void create(World world)
    {
        this.world = world;
        body = world.createBody(bDef);
        for (int i = 0; i < fixturesNumber; i++) {
            fixtures[i]=body.createFixture(fDefs[i]);
        }

        mustDestroyed=false;
    }
    public void draw(SpriteBatch batch)
    {
        x=body.getPosition().x;
        y=body.getPosition().y;
        rotation=body.getAngle();
        sprite.setPosition(body.getPosition().x-width/2,body.getPosition().y-height/2);
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }
    public void setX(float x)
    {
        if(body!=null)
        {
            body.setTransform(x,body.getPosition().y,body.getAngle());
        }
        else bDef.position.x=x;
    }
    public void setY(float y)
    {
        if(body!=null)
        {
            body.setTransform(body.getPosition().x,y,body.getAngle());
        }
        else bDef.position.y=y;
    }
    public float getX()
    {
        if(body!=null)
        return body.getPosition().x;
        else return bDef.position.x;
    }
    public float getY()
    {
        if(body!=null)
            return body.getPosition().y;
        else return bDef.position.y;
    }
    public float getAngle()
    {
        if(body!=null)
        return body.getAngle();
        else return bDef.angle;
    }

    /*public Body getBody() {
        return body;
    }*/

    public Sprite getSprite() {
        return sprite;
    }
    public void setTransform(float x,float y,float rotation)
    {
        if(body!=null)
        {
            body.setTransform(x,y,(float)Math.toRadians(rotation));
        }
        else
        {
            bDef.position.set(x,y);
            bDef.angle=(float)Math.toRadians(rotation);
        }
    }


    public Body getBody() {
        return body;
    }

    public float getRotation()
    {
        if(body!=null)
            return (float) Math.toDegrees(body.getAngle());
        else return (float) Math.toDegrees(bDef.angle);
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

    public ServObject toServ()
    {
        return new ServObject(spriteName,x,y,height,width,body.getAngle());
    }
}
