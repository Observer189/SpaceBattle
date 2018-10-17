package com.mygdx.game.Modules;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.Module;
import com.mygdx.game.utils.ModuleType;
import com.mygdx.game.utils.Size;

/**
 * Created by Sash on 17.10.2018.
 */

public class EnergyModule extends Module {
    private float energyStorage;
    private float energyGeneration;
    private float reloadTime;
    public EnergyModule(TextureRegion textureRegion, float x, float y, Size size, float density,float energyStorage,float energyGeneration,float reloadTime, World world) {
        super(textureRegion, x, y, size, ModuleType.EnergyModule, density, world);
        this.energyGeneration=energyGeneration;
        this.energyStorage=energyStorage;
        this.reloadTime=reloadTime;
    }
}
