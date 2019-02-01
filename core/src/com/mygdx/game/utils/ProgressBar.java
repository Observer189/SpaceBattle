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
    private float width;
    private float height;
    private float offsetXCoeff;
    private float offsetYCoeff;
    private float widthCoeff;
    private float heightCoeff;

    private OrthographicCamera camera;
    private TextureRegion barTexture;
    private TextureRegion lineTexture;
    private SpriteBatch batch;

    private float maxValue;

    public ProgressBar(SpriteBatch batch, TextureRegion barTexture, TextureRegion lineTexture, float x, float y, float width, float height, float maxValue) {
        this.batch = batch;
        this.barTexture = barTexture;
        this.lineTexture = lineTexture;
        this.x = x;
        this.y = y;
        this.widthCoeff = width;
        this.heightCoeff = height;
        this.maxValue = maxValue;
    }

    ProgressBar(SpriteBatch batch, TextureRegion barTexture, TextureRegion lineTexture, float offsetXCoeff, float offsetYCoeff, float widthCoeff, float heightCoeff, float maxValue,
                OrthographicCamera camera) {
        this.batch = batch;
        this.barTexture = barTexture;
        this.lineTexture = lineTexture;
        this.x = camera.position.x + camera.viewportWidth * offsetXCoeff;
        this.y = camera.position.y + camera.viewportHeight * offsetYCoeff;
        this.offsetXCoeff = offsetXCoeff;
        this.offsetYCoeff = offsetYCoeff;
        this.width = camera.viewportWidth * widthCoeff;
        this.height = camera.viewportHeight * heightCoeff;
        this.widthCoeff = widthCoeff;
        this.heightCoeff = heightCoeff;

        this.maxValue = maxValue;
        this.camera = camera;
    }

    public void update() {
        x = camera.position.x + camera.viewportWidth * offsetXCoeff;
        y = camera.position.y + camera.viewportHeight * offsetYCoeff;
        width = camera.viewportWidth * widthCoeff;
        height = camera.viewportHeight * heightCoeff;
        //System.out.println(camera.position.y-y);
    }

    public void draw(float currentValue) {
        batch.begin();
        batch.draw(lineTexture, x, y, width * (currentValue / maxValue), height);
        batch.draw(barTexture, x, y, width, height);
        //System.out.println(width*(currentValue/maxValue));
        batch.end();
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
