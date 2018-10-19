package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Sash on 30.05.2018.
 */

public class ProgressBar {
    private float x;
    private float y;
    private float offsetX;
    private float offsetY;
    private float width;
    private float height;

    private OrthographicCamera camera;
    private TextureRegion barTexture;
    private TextureRegion lineTexture;
    SpriteBatch batch;

    private float maxValue;

    public ProgressBar(SpriteBatch batch,TextureRegion barTexture,TextureRegion lineTexture,float x, float y, float width, float height,float maxValue)
    {
     this.batch=batch;
     this.barTexture=barTexture;
     this.lineTexture=lineTexture;
     this.x=x;
     this.y=y;
     this.width=width;
     this.height=height;
     this.maxValue=maxValue;
    }
    public ProgressBar(SpriteBatch batch,TextureRegion barTexture,TextureRegion lineTexture,
                       float x, float y,float offsetX,float offsetY, float width, float height,float maxValue,
                       OrthographicCamera camera)
    {
        this.batch=batch;
        this.barTexture=barTexture;
        this.lineTexture=lineTexture;
        this.x=x;
        this.y=y;
        this.offsetX=offsetX;
        this.offsetY=offsetY;
        this.width=width;
        this.height=height;

        this.maxValue=maxValue;
        this.camera=camera;
    }
    public void update()
    {
        x=camera.position.x+offsetX;
        y=camera.position.y+offsetY;
    }

    public void draw(float currentValue)
    {
        batch.begin();
        batch.draw(lineTexture,x,y,width*(currentValue/maxValue),height);
        batch.draw(barTexture,x,y,width,height);

        batch.end();
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
