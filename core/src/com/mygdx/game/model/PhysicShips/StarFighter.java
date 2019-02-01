package com.mygdx.game.model.PhysicShips;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.Modules.EnergyModules.UnknownReactor;
import com.mygdx.game.model.Modules.Engines.IonEngine;
import com.mygdx.game.model.Modules.Weapons.ImpulseLaser;
import com.mygdx.game.model.EnergyPoint;
import com.mygdx.game.model.EnginePoint;
import com.mygdx.game.model.PhysicShip;
import com.mygdx.game.model.WeaponPoint;

/**
 * Created by Sash on 28.10.2018.
 */

public class StarFighter extends PhysicShip {
    private static final float width = 0.6f;
    private static final float height = 0.8f;

    public StarFighter(float x, float y, float rotation) {
        super("StarFighter", x, y, rotation, width, height, 2500, 3, 2, 3, 1, 0.05f, 50,
                new float[][]
                        {
                                {
                                        -width / 2 + width * 0.1f, -height / 2 + height * 0.49f,
                                        -width / 2, -height / 2 + height * 0.32f,
                                        -width / 2, -height / 2 + height * 0.25f,
                                        -width / 2 + width * 0.17f, -height / 2 + height * 0.12f,
                                        //-width /2+width*0.17f,-height /2,
                                        //-width /2+width*0.79f,-height/2,
                                        -width / 2 + width * 0.79f, -height / 2 + height * 0.12f,
                                        width / 2, -height / 2 + height * 0.32f,
                                        -width / 2 + width * 0.87f, -height / 2 + height * 0.49f

                                },
                                {

                                        -width / 2 + width * 0.91f, -height / 2 + height * 0.64f,
                                        -width / 2 + width * 0.797f, -height / 2 + height * 0.86f,
                                        -width / 2 + width * 0.62f, height / 2,
                                        -width / 2 + width * 0.33f, height / 2,
                                        -width / 2 + width * 0.17f, -height / 2 + height * 0.86f,
                                        -width / 2 + width * 0.06f, -height / 2 + height * 0.64f,

                                },
                                {
                                        -width / 2 + width * 0.91f, -height / 2 + height * 0.64f,
                                        -width / 2 + width * 0.87f, -height / 2 + height * 0.49f,
                                        -width / 2 + width * 0.91f, -height / 2 + height * 0.526f,
                                        -width / 2 + width * 0.06f, -height / 2 + height * 0.526f,
                                        -width / 2 + width * 0.1f, -height / 2 + height * 0.49f,
                                        -width / 2 + width * 0.06f, -height / 2 + height * 0.64f,
                                }

                        });

        getWeapons()[0] = new WeaponPoint(new ImpulseLaser(x, y, rotation), new Vector2(-0.1f, 0.2f));
        getWeapons()[1] = new WeaponPoint(new ImpulseLaser(x, y, rotation), new Vector2(0.1f, 0.2f));
        getEngines()[0] = new EnginePoint(new IonEngine(x, y, rotation), new Vector2(0f, -0.4f));
        getEngines()[1] = new EnginePoint(new IonEngine(x, y, rotation), new Vector2(0.2f, -0.4f));
        getEngines()[2] = new EnginePoint(new IonEngine(x, y, rotation), new Vector2(-0.2f, -0.4f));
        getEnergyPoints()[0] = new EnergyPoint(new UnknownReactor(x, y, rotation), new Vector2(0f, 0.f));
    }
}
