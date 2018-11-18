package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.ServModels.ServAsteroid;
import com.mygdx.game.ServModels.ServAsteroidField;
import com.mygdx.game.model.Asteroids.Asteroid1;

/**
 * Created by Sash on 26.10.2018.
 */

public class AsteroidField {
    Array<Asteroid> asteroids;
    World world;
    TextureAtlas textureAtlas;
    int size;
    float mapWidth;
    float mapHeight;
    public AsteroidField(int minNumber, int maxNumber,float mapWidth,float mapHeight)
    {
        asteroids=new Array<Asteroid>();

        size=minNumber+(int)(Math.random()*(maxNumber-minNumber));
        this.mapWidth=mapWidth;
        this.mapHeight=mapHeight;

    }
    public AsteroidField()
    {
        asteroids=new Array<Asteroid>();
    }
    public void create(TextureAtlas textureAtlas,World world)
    {
        this.world=world;
        this.textureAtlas=textureAtlas;
        for(int i=0;i<asteroids.size;i++)
        {
            asteroids.get(i).create(textureAtlas,world);
        }
    }
    public void update()
    {
        for(int i=0;i<asteroids.size;i++)
        {
            asteroids.get(i).update();
            if(asteroids.get(i).getHp()<0)
            {
                asteroids.get(i).destroy();
                asteroids.removeIndex(i);
            }
        }
    }
    public void draw(SpriteBatch batch)
    {
        for(int i=0;i<asteroids.size;i++)
        {
            asteroids.get(i).draw(batch);
        }
    }
    public void generate()
    {
        for(int i=0;i<size;i++)
        {
            asteroids.add(new Asteroid1( mapWidth*(float) Math.random(),mapHeight*(float)Math.random(),360*(float)Math.random(),
                    0.5f+(float)(Math.random()*2),0.5f+(float)Math.random()*2,
            new Vector2(0,0),10+(float)(Math.random()*100)));
        }
    }

    public Array<Asteroid> getAsteroids() {
        return asteroids;
    }
    public ServAsteroidField toServ()
    {
        ServAsteroidField field=new ServAsteroidField(asteroids.size);
        for (int i=0;i<asteroids.size;i++)
        {
            field.getAsteroids()[i]=new ServAsteroid(asteroids.get(i).toServ());
        }
        return field;
    }
}
