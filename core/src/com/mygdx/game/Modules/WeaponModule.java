package com.mygdx.game.Modules;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.Ammo;
import com.mygdx.game.model.Module;
import com.mygdx.game.model.PhysicAmmo;
import com.mygdx.game.utils.ModuleType;
import com.mygdx.game.utils.Size;

import java.util.ArrayList;

/**
 * Created by Sash on 21.09.2018.
 */

public class WeaponModule extends Module {
    private float reloadTime;
    private float energyCost;
    private float basicDamage;
    private float damage;

    private ArrayList<PhysicAmmo> ammos;
    public WeaponModule(TextureRegion textureRegion, float x, float y, Size size,  float density,float basicDamage,float energyCost,float reloadTime, World world) {
        super(textureRegion, x, y, size,ModuleType.Weapon, density, world);
        this.basicDamage=basicDamage;
        this.energyCost=energyCost;
        this.reloadTime=reloadTime;
        this.damage=basicDamage;
        ammos=new ArrayList<PhysicAmmo>();
    }

    public void shot(float l) //l - это расстояние от центра пушки до конца корабля
    {

    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for (PhysicAmmo i:ammos)
        {
            i.draw(batch);
        }
    }

    public float getReloadTime() {
        return reloadTime;
    }

    public float getDamage() {
        return damage;
    }

    public float getBasicDamage() {
        return basicDamage;
    }

    public float getEnergyCost() {
        return energyCost;
    }

    public ArrayList<PhysicAmmo> getAmmos() {
        return ammos;
    }
}
