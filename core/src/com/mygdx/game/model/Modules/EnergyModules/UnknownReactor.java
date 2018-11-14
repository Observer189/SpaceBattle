package com.mygdx.game.model.Modules.EnergyModules;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.Modules.EnergyModule;
import com.mygdx.game.utils.Size;

/**
 * Created by Sash on 17.10.2018.
 */

public class UnknownReactor extends EnergyModule {
    public UnknownReactor( float x, float y) {
        super("Reactor", x, y, Size.Small, 1,1000,10);
    }
}
