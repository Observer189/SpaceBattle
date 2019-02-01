package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Sash on 18.05.2018.
 */

public class ButtonForProcessor {
    private float x;
    private float y;
    private float width;
    private float height;
    private OrthographicCamera camera;
    private float offsetX;
    private float offsetY;
    private float widthCoeff;
    private float heightCoeff;
    private SpriteBatch batch;
    private TextureRegion vision;

    public ButtonForProcessor(SpriteBatch batch, OrthographicCamera camera, float offsetX, float offsetY, float widthCoeff, float heightCoeff, TextureRegion vision) {
        x = camera.position.x + offsetX;
        y = camera.position.y + offsetY;
        width = camera.viewportWidth * widthCoeff;
        height = camera.viewportHeight * heightCoeff;
        this.camera = camera;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.widthCoeff = widthCoeff;
        this.heightCoeff = heightCoeff;
        this.batch = batch;
        this.vision = vision;
    }

    public void draw() {
        x = camera.position.x + offsetX;
        y = camera.position.y + offsetY;
        width = camera.viewportWidth * widthCoeff;
        height = camera.viewportHeight * heightCoeff;
        batch.begin();
        batch.draw(vision, x, y, width, height);
        batch.end();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

}
