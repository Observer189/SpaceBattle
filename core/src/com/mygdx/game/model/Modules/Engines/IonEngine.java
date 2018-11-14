package com.mygdx.game.model.Modules.Engines;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.Modules.Engine;
import com.mygdx.game.utils.Size;

/**
 * Created by Sash on 09.10.2018.
 */

public class IonEngine extends Engine {
    public IonEngine( float x, float y) {
        super("IonEngine", x, y, Size.Small, 1f, 5000, 1000f, 10,1f, Type.Cruising);
    }
}
