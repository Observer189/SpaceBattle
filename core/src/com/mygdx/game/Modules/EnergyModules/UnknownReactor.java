package com.mygdx.game.Modules.EnergyModules;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Modules.EnergyModule;
import com.mygdx.game.utils.Size;

/**
 * Created by Sash on 17.10.2018.
 */

public class UnknownReactor extends EnergyModule {
    public UnknownReactor(TextureAtlas textureAtlas, float x, float y, World world) {
        super(textureAtlas.findRegion("Reactor"), x, y, Size.Small, 1,100,10,1, world);
    }
}
