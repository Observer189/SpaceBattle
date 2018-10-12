package com.mygdx.game.PhysicShips;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Modules.Engines.IonEngine;
import com.mygdx.game.Modules.WeaponModule;
import com.mygdx.game.model.EnginePoint;
import com.mygdx.game.model.PhysicShip;
import com.mygdx.game.model.WeaponPoint;
import com.mygdx.game.utils.Size;

/**
 * Created by Sash on 15.09.2018.
 */

public class Fury extends PhysicShip{
    static final float width=0.75f;
    static final float height=1;

    public Fury(TextureAtlas textureAtlas, float x, float y, World world) {
        super(textureAtlas.findRegion("Dashing"), x, y, width, height,30,2,2,3, 0.05f, new float[][]{
                {-width /2,-height /2+ height *0.26f,
                -width /2,-height /2+ height *0.135f,
                -width /2+ width *0.18f,-height /2,
                -width /2+ width *0.82f,-height /2,
                width /2,-height /2+ height *0.135f,
                width /2,-height /2+ height *0.26f,
                -width /2+ width *0.82f,-height /2+ height *0.41f,
                -width /2+ width *0.18f,-height /2+ height *0.41f
        },
                {
                        -width /2+ width *0.1f,-height /2+ height *0.567f,
                        -width /2+ width *0.83f,-height /2+ height *0.41f,
                        -width /2+ width *0.17f,-height /2+ height *0.41f,
                        -width /2+ width *0.9f,-height /2+ height *0.567f,
                        -width /2+ width *0.67f, height /2,
                        -width /2+ width *0.33f, height /2}
        }, world);
        getWeapons()[0]=new WeaponPoint(new WeaponModule(textureAtlas.findRegion("Machinegun"),x,y, Size.Small,10,world),getBodies()[1],new Vector2(-0.2f,-0.1f),world);
        getWeapons()[1]=new WeaponPoint(new WeaponModule(textureAtlas.findRegion("Machinegun"),x,y, Size.Small,10,world),getBodies()[1],new Vector2(0.2f,-0.1f),world);
        getEngines()[0]=new EnginePoint(new IonEngine(textureAtlas,x,y,world),getBodies()[0],new Vector2(0f,-0.1f),world);
        getEngines()[1]=new EnginePoint(new IonEngine(textureAtlas,x,y,world),getBodies()[0],new Vector2(0.2f,-0.1f),world);
        getEngines()[2]=new EnginePoint(new IonEngine(textureAtlas,x,y,world),getBodies()[0],new Vector2(-0.2f,-0.1f),world);
    }
}
