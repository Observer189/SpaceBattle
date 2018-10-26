package com.mygdx.game.model.Asteroids;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.Asteroid;

/**
 * Created by Sash on 25.10.2018.
 */

public class Asteroid1 extends Asteroid {
    //static float width=2f;
    //static float height=2f;
    public Asteroid1(TextureAtlas textureAtlas, float x, float y, float width, float height, Vector2 startVelocity,float maxHp, World world) {
        super(textureAtlas.findRegion("Asteroid"), x, y, width, height, 2000, 2, new float[][]
                {
                        {
                                -width /2,0f,
                                -width /2+width*0.1f,-height /2+ height *0.156f,
                                -width /2+ width *0.39f,-height /2,
                                -width /2+ width *0.68f,-height /2,
                                -width /2+width*0.875f,-height /2+ height *0.1875f,
                                width/2,-height /2+ height *0.41f,
                                width/2,0f
                        },
                        {
                                width/2,0f,
                                -width/2+width*0.97f,-height/2+height*0.67f,
                                -width/2+width*0.62f,height/2,
                                -width/2+width*0.41f,height/2,
                                -width/2+width*0.03f,-height/2+height*0.73f,
                                -width/2,-height/2+height*0.65f,
                                -width/2,0f,
                        }

                        }, startVelocity,maxHp, world);
    }
}
