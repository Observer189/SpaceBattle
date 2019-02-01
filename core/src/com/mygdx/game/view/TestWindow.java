package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Sash on 23.11.2018.
 */

public class TestWindow implements Screen {
    private World world;
    private Box2DDebugRenderer renderer;
    OrthographicCamera camera;
    SpriteBatch batch;

    @Override
    public void show() {
        world = new World(new Vector2(0, 0), false);
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(20, 20);
        batch = new SpriteBatch();
        createRect();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        renderer.render(world, camera.combined);
        world.step(1 / 60f, 4, 4);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void createRect() {
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set(0, 0);
        Body body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((float) (Math.random() * 3), (float) (Math.random() * 3));
        fDef.shape = shape;
        fDef.density = 1000;
        fDef.friction = 0.5f;
        fDef.restitution = 0.2f;
        body.createFixture(fDef);
        body.setAngularVelocity(1);
    }
}
