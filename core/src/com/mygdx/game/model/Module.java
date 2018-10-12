package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.utils.ModuleType;
import com.mygdx.game.utils.Size;

/**
 * Created by Sash on 21.09.2018.
 */

public class Module {

    Size size;
    ModuleType type;
    Body body;
    Fixture fixture;
    BodyDef bDef;
    FixtureDef fDef;
    PolygonShape shape;
    float width;
    float height;


    Sprite sprite;
    public Module(TextureRegion textureRegion,float x, float y, Size size,ModuleType type, float density, World world)
    {
        this.size=size;
        if(size.equals(Size.Small))
        {
            width=0.2f;
            height=0.3f;
        }
        else if(size.equals(Size.Medium))
        {
            width=0.4f;
            height=0.4f;

        }
        else if(size.equals(Size.Big))
        {
            width=1;
            height=1;
        }
        this.type=type;
        bDef=new BodyDef();
        fDef= new FixtureDef();
        bDef.type= BodyDef.BodyType.DynamicBody;
        bDef.position.set(x,y);
        body=world.createBody(bDef);
        shape= new PolygonShape();
        shape.setAsBox(width/2,height/2);
        fDef.shape=this.shape;
        fDef.density=density;
        fixture=body.createFixture(fDef);


        sprite = new Sprite(textureRegion);
        sprite.setSize(width,height);
        sprite.setOrigin(width/2,height/2);
        sprite.setPosition(x-width/2,y-height/2);

    }


    public void draw(SpriteBatch batch)
    {
        sprite.setPosition(body.getPosition().x-width/2,body.getPosition().y-height/2);
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    public ModuleType getType() {
        return type;
    }

    public Body getBody() {
        return body;
    }
}
