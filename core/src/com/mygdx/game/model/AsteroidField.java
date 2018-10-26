package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
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
    public AsteroidField(TextureAtlas textureAtlas,int minNumber, int maxNumber,float mapWidth,float mapHeight,World world)
    {
        asteroids=new Array<Asteroid>();
        this.world=world;
        this.textureAtlas=textureAtlas;
        size=minNumber+(int)(Math.random()*(maxNumber-minNumber));
        this.mapWidth=mapWidth;
        this.mapHeight=mapHeight;

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
            asteroids.add(new Asteroid1(textureAtlas, mapWidth*(float) Math.random(),mapHeight*(float)Math.random(),0.5f+(float)(Math.random()*2),0.5f+(float)Math.random()*2,
            new Vector2(0,0),10+(float)(Math.random()*100),world));
        }
    }

    public Array<Asteroid> getAsteroids() {
        return asteroids;
    }
}
