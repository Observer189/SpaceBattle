package com.mygdx.game.PhysicShips;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Modules.EnergyModules.UnknownReactor;
import com.mygdx.game.Modules.Engines.IonEngine;
import com.mygdx.game.Modules.Weapons.ImpulseLaser;
import com.mygdx.game.model.EnergyPoint;
import com.mygdx.game.model.EnginePoint;
import com.mygdx.game.model.PhysicShip;
import com.mygdx.game.model.WeaponPoint;

/**
 * Created by Sash on 28.10.2018.
 */

public class StarFighter extends PhysicShip {
    static final float width=0.6f;
    static final float height=0.8f;
    public StarFighter(TextureAtlas textureAtlas, float x, float y, World world) {
        super(textureAtlas.findRegion("StarFighter"), x, y, width, height, 2000,1, 2, 3, 1, 0.05f, 300,
                new float[][]
                        {
                                {
                                        -width /2+width*0.1f,-height /2+ height *0.49f,
                                        -width /2,-height /2+ height *0.32f,
                                        -width /2,-height /2+height*0.25f,
                                        -width /2+ width *0.17f,-height /2+height*0.12f,
                                        //-width /2+width*0.17f,-height /2,
                                        //-width /2+width*0.79f,-height/2,
                                        -width /2+width*0.79f,-height /2+ height *0.12f,
                                        width /2,-height /2+ height *0.32f,
                                        width/2,-height/2+height*0.49f

                                }
                        }, world);
        getWeapons()[0]=new WeaponPoint(new ImpulseLaser(textureAtlas,x,y,world),getBody(),new Vector2(-0.1f,0.2f),world);
        getWeapons()[1]=new WeaponPoint(new ImpulseLaser(textureAtlas,x,y,world),getBody(),new Vector2(0.1f,0.2f),world);
        getEngines()[0]=new EnginePoint(new IonEngine(textureAtlas,x,y,world),getBody(),new Vector2(0f,-0.4f),world);
        getEngines()[1]=new EnginePoint(new IonEngine(textureAtlas,x,y,world),getBody(),new Vector2(0.2f,-0.4f),world);
        getEngines()[2]=new EnginePoint(new IonEngine(textureAtlas,x,y,world),getBody(),new Vector2(-0.2f,-0.4f),world);
        getEnergyPoints()[0]=new EnergyPoint(new UnknownReactor(textureAtlas,x,y,world),getBody(),new Vector2(0f,0.f),world);
    }
}
