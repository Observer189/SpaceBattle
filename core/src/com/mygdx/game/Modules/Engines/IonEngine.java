package com.mygdx.game.Modules.Engines;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Modules.Engine;
import com.mygdx.game.utils.ModuleType;
import com.mygdx.game.utils.Size;

/**
 * Created by Sash on 09.10.2018.
 */

public class IonEngine extends Engine {
    public IonEngine(TextureAtlas textureAtlas, float x, float y, World world) {
        super(textureAtlas.findRegion("IonEngine"), x, y, Size.Small, 1f, 5000, 500f, 10,1f, Type.Cruising, world);
    }
}
