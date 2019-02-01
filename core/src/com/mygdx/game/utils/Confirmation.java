package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.awt.Button;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class Confirmation {
    private float x;
    private float y;
    private BitmapFont font;
    private Skin skin;
    Boolean isMenu;
    private TextButton.TextButtonStyle btnstyle1;
    private TextButton ok, cancel, sure;
    public Stage stage = new Stage();
    private TextureAtlas textureAtlas;
    public SpriteBatch batch;
    private Boolean isActive = false;
    public com.badlogic.gdx.scenes.scene2d.ui.Button btn;

    public Confirmation(BitmapFont font, SpriteBatch batch) {
        this.font = font;
        this.batch = batch;
        textureAtlas = new TextureAtlas(Gdx.files.internal("TexturePack.atlas"));
        skin = new Skin();
        skin.addRegions(textureAtlas);
        com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle btnstyle = new com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle();
        btnstyle.up = skin.getDrawable("GrayFrame");

        btn = new com.badlogic.gdx.scenes.scene2d.ui.Button(btnstyle);
        btn.setSize(600, 500);
        btn.setPosition(Gdx.graphics.getWidth() * 2, 200);
        btnstyle1 = new TextButton.TextButtonStyle();
        btnstyle1.font = font;
        btnstyle1.up = skin.getDrawable("FrameInput");
        ok = new TextButton("OK", btnstyle1);
        cancel = new TextButton("Cancel", btnstyle1);
        sure = new TextButton("Are you sure?", btnstyle1);

        ok.setPosition(btn.getX() + 20, btn.getY() + 20);
        sure.setPosition(btn.getX() + ok.getWidth(), btn.getY() + btn.getHeight() - ok.getHeight());
        cancel.setPosition((float) (btn.getX() + btn.getWidth() - cancel.getWidth() * 1.2), btn.getY() + 20);
        cancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setActive(false);

            }
        });
        ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                System.exit(0);

            }
        });
        stage.addActor(btn);
        stage.addActor(ok);
        stage.addActor(cancel);


    }

    public void draw() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if (isActive) {
            TextManager textManager = new TextManager(0, 0);
            textManager.displayMessage(batch, font, "Are you sure??", btn.getX() + btn.getWidth() / 5, (float) (btn.getY() + btn.getHeight() / 1.5));
        }

    }

    public void setActive(Boolean setActive) {
        if (setActive) {
            isActive = setActive;
            System.out.println(" !!!!" + btn.getX());
            btn.setPosition(Gdx.graphics.getWidth() / 2 - btn.getWidth() / 2, Gdx.graphics.getHeight() / 2 - btn.getHeight() / 2);
            System.out.println(" !!!!" + btn.getX());
            ok.setPosition(btn.getX() + 20, btn.getY() + 20);

            cancel.setPosition((float) (btn.getX() + btn.getWidth() - cancel.getWidth() * 1.2), btn.getY() + 20);
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
            TextManager textManager = new TextManager(0, 0);
            textManager.displayMessage(batch, font, "Are you sure?", btn.getX() + btn.getWidth() / 5, (float) (btn.getY() + btn.getHeight() / 1.5));
        } else {
            isActive = false;
            btn.setPosition(Gdx.graphics.getWidth() * 2, btn.getY());
            ok.setPosition(btn.getX() + 20, btn.getY() + 20);

            cancel.setPosition((float) (btn.getX() + btn.getWidth() - cancel.getWidth() * 1.2), btn.getY() + 20);
        }

    }


}
