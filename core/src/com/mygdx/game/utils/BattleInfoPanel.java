package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Sash on 28.10.2018.
 */

public class BattleInfoPanel {
    ProgressBar HPBar;
    ProgressBar energyBar;

    private float x;
    private float y;
    private float width;
    private float height;
    private float offsetXCoeff;
    private float offsetYCoeff;
    private float widthCoeff;
    private float heightCoeff;

    private float HPWidthCoeff;
    private float HPHeightCoeff;

    private float energyWidthCoeff;
    private float energyHeightCoeff;

    private OrthographicCamera camera;

    SpriteBatch batch;
    public BattleInfoPanel(SpriteBatch batch,TextureAtlas textureAtlas,float offsetXCoeff,float offsetYCoeff, float widthCoeff, float heightCoeff,
                    float maxValueHp,float maxValueEnergy,OrthographicCamera camera)
    {
        this.batch=batch;

        this.x=camera.position.x+camera.viewportWidth*offsetXCoeff;
        this.y=camera.position.y+camera.viewportHeight*offsetYCoeff;
        this.offsetXCoeff =offsetXCoeff;
        this.offsetYCoeff =offsetYCoeff;
        this.width=camera.viewportWidth*widthCoeff;
        this.height=camera.viewportHeight*heightCoeff;
        this.widthCoeff =widthCoeff;
        this.heightCoeff =heightCoeff;
        this.camera=camera;

        HPWidthCoeff=widthCoeff;
        HPHeightCoeff=heightCoeff*5/8;

        energyWidthCoeff=widthCoeff*2/3;
        energyHeightCoeff=heightCoeff*3/8;
        HPBar=new ProgressBar(batch,textureAtlas.findRegion("HProgressBar"),textureAtlas.findRegion("HPLine"),offsetXCoeff,offsetYCoeff+energyHeightCoeff,
                HPWidthCoeff,HPHeightCoeff,maxValueHp,camera);
        energyBar=new ProgressBar(batch,textureAtlas.findRegion("EProgressBar"),textureAtlas.findRegion("EnergyLine"),
                offsetXCoeff,offsetYCoeff,energyWidthCoeff,energyHeightCoeff,maxValueEnergy,camera);
    }
    public void update()
    {
        HPBar.update();
        energyBar.update();
    }
    public void draw(float valueHp,float valueEnergy)
    {
        HPBar.draw(valueHp);
        energyBar.draw(valueEnergy);

    }

    public ProgressBar getEnergyBar() {
        return energyBar;
    }

    public ProgressBar getHPBar() {
        return HPBar;
    }
}
