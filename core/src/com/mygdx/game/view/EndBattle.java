package com.mygdx.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.model.Map;
import com.mygdx.game.model.OldPlayer;
import com.mygdx.game.requests.servApi;
import com.mygdx.game.utils.TextManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sash on 11.05.2018.
 */

public class EndBattle implements Screen {
    private MainMenu mainMenu;
    Game game;
    OrthographicCamera camera;
    OldPlayer oldPlayer;
    Stage stage;
    private Button.ButtonStyle btnStyle;
    private Button btn;
    BitmapFont font;
    TextManager textManager;
    SpriteBatch batch;
    private Map map;
    private String status;
    private int appliedDamage;
    private int reward;
    private final String baseURL = "https://star-project-serv.herokuapp.com/";
    private servApi request;

    private BitmapFont bigBlueFont;
    private BitmapFont bigRedFont;
    private BitmapFont smallBlueFont;
    private BitmapFont smallRedFont;

    EndBattle(OldPlayer oldPlayer, SpriteBatch batch, Game game, MainMenu mainMenu, Map map, String status) {
        this.mainMenu = mainMenu;
        this.batch = batch;
        this.game = game;
        this.oldPlayer = oldPlayer;
        this.map = map;
        this.map.setWidth(Gdx.graphics.getWidth());
        this.map.setHeight(Gdx.graphics.getHeight());
        this.status = status;
        this.appliedDamage = oldPlayer.getCurrentShip().getAppliedDamage();
        oldPlayer.getCurrentShip().setAppliedDamage(0);
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(servApi.class);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0));
        textManager = new TextManager(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font = textManager.fontInitialize(Color.BLUE, 1);
        bigBlueFont = textManager.fontInitialize(Color.BLUE, 4f);
        bigRedFont = textManager.fontInitialize(Color.RED, 4f);
        smallBlueFont = textManager.fontInitialize(Color.BLUE, 2f);
        smallRedFont = textManager.fontInitialize(Color.RED, 2f);
        Skin skin = new Skin();
        skin.addRegions(new TextureAtlas(Gdx.files.internal("TexturePack.atlas")));
        btnStyle = new Button.ButtonStyle();
        btnStyle.up = skin.getDrawable("Back-up");
        btnStyle.down = skin.getDrawable("Back-down");
        btn = new Button(btnStyle);
        btn.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
        btn.setPosition(0, 0);

        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenu.music.play();
                game.setScreen(mainMenu);


            }
        });

        stage = new Stage();
        stage.addActor(btn);
        Gdx.input.setInputProcessor(stage);
        reward = generateReward(status);
        oldPlayer.setMoney(oldPlayer.getMoney() + reward);
        updatePlayerMoney();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        map.draw();
        stage.act(delta);
        stage.draw();
        if (status.equals("Victory")) {
            //System.out.println("V");
            textManager.displayMessage(batch, bigBlueFont, "Victory!!!", Gdx.graphics.getWidth() / 6, Gdx.graphics.getHeight() * 0.9f);
            textManager.displayMessage(batch, smallBlueFont, "Reward: " + reward, Gdx.graphics.getWidth() / 6, Gdx.graphics.getHeight() * 0.65f);
        }
        if (status.equals("Failure")) {
            //System.out.println("F");
            textManager.displayMessage(batch, bigRedFont, "Failure", Gdx.graphics.getWidth() / 6, Gdx.graphics.getHeight() * 0.9f);
            textManager.displayMessage(batch, smallRedFont, "Reward: " + reward, Gdx.graphics.getWidth() / 6, Gdx.graphics.getHeight() * 0.65f);
        }
        //System.out.println(status);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(mainMenu);
        }

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

    private int generateReward(String status) {
        if (status.equals("Failure")) {
            return appliedDamage;
        }
        if (status.equals("Victory")) {
            double aD = appliedDamage;
            return (int) (aD * (4 + Math.random()));
        } else return 0;
    }

    private void updatePlayerMoney() {
        Call<Integer> call = request.updateMoney(oldPlayer.getName(), oldPlayer.getMoney());
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
