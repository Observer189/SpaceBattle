package com.mygdx.game.Modules;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.Module;
import com.mygdx.game.utils.ModuleType;
import com.mygdx.game.utils.Size;

/**
 * Created by Sash on 21.09.2018.
 */

public class WeaponModule extends Module {
    float reloadTime;
    float energyCost;
    public WeaponModule(TextureRegion textureRegion, float x, float y, Size size,  float density, World world) {
        super(textureRegion, x, y, size,ModuleType.Weapon, density, world);
    }

}
