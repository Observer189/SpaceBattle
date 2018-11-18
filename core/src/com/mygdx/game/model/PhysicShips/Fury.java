package com.mygdx.game.model.PhysicShips;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.Modules.EnergyModules.UnknownReactor;
import com.mygdx.game.model.Modules.Engines.IonEngine;
import com.mygdx.game.model.Modules.Weapons.ImpulseLaser;
import com.mygdx.game.model.EnergyPoint;
import com.mygdx.game.model.EnginePoint;
import com.mygdx.game.model.Modules.Weapons.Machinegun;
import com.mygdx.game.model.PhysicShip;
import com.mygdx.game.model.WeaponPoint;

/**
 * Created by Sash on 15.09.2018.
 */

public class Fury extends PhysicShip{
    static final float width=0.75f;
    static final float height=1;

    public Fury(float x, float y,float rotation) {
        super("Dashing", x, y,rotation, width, height,3000,2,2,3,1,
                 0.05f,400f,
                new float[][]{
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
                        -width /2+ width *0.17f,-height /2+ height *0.41f,
                        -width /2+ width *0.83f,-height /2+ height *0.41f,
                        -width /2+ width *0.9f,-height /2+ height *0.567f,
                        -width /2+ width *0.67f, height /2,
                        -width /2+ width *0.33f, height /2}
        });
        //getWeapons()[0]=new WeaponPoint(new Machinegun(textureAtlas,x,y,world),getBody(),new Vector2(-0.1f,0.2f),world);
        //getWeapons()[1]=new WeaponPoint(new Machinegun(textureAtlas,x,y,world),getBody(),new Vector2(0.1f,0.2f),world);
        getWeapons()[0]=new WeaponPoint(new Machinegun(x,y,rotation),new Vector2(-0.1f,0.2f));
        getWeapons()[1]=new WeaponPoint(new Machinegun(x,y,rotation),new Vector2(0.1f,0.2f));
        getEngines()[0]=new EnginePoint(new IonEngine(x,y,rotation),new Vector2(0f,-0.4f));
        getEngines()[1]=new EnginePoint(new IonEngine(x,y,rotation),new Vector2(0.2f,-0.4f));
        getEngines()[2]=new EnginePoint(new IonEngine(x,y,rotation),new Vector2(-0.2f,-0.4f));
        getEnergyPoints()[0]=new EnergyPoint(new UnknownReactor(x,y,rotation),new Vector2(0f,0.f));
    }
}
