package com.mygdx.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.control.BattleProcessor;
import com.mygdx.game.model.BattleStatus;
import com.mygdx.game.model.Coord;
import com.mygdx.game.model.Map;
import com.mygdx.game.model.MiniMap;
import com.mygdx.game.model.OldPlayer;

import com.mygdx.game.utils.ProgressBar;
import com.mygdx.game.requests.servApi;
import com.mygdx.game.utils.ButtonForProcessor;
import com.mygdx.game.utils.GasRegulator;
import com.mygdx.game.utils.Joystick;
import com.mygdx.game.utils.TextManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Thread.sleep;

/**
 * Created by Sash on 27.04.2018.
 */

public class Battle implements Screen {
    TextManager textManager;
    SpriteBatch batch;
    Game game;
    TextureAtlas textureAtlas;
    OldPlayer oldPlayer;
    private OldPlayer enemy;
    private Coord coord;
    private servApi request;
    OrthographicCamera camera;
    private int counter;
    private BitmapFont redFont;
    private BattleStatus battleStatus;
    private InputProcessor processor;
    private MainMenu mainMenu;
    public static float camX;
    public static float camY;
    public static float delta;
    public static float widthCamera;
    public static float heightCamera;
    private long ping;
    private long startTime;
    private long estimatedTime;


    private Joystick joystick;
    private GasRegulator gasRegulator;
    ButtonForProcessor turnLeft;
    ButtonForProcessor turnRight;
    private ProgressBar hpBar;
    private MiniMap miniMap;

    private boolean getCoordIsFinished;
    private final float AspectRatio;
    private final String baseURL = "https://star-project-serv.herokuapp.com/";
    private Map classicMap;
    private Screen endBattle;

    Battle(SpriteBatch batch, Game game, TextureAtlas textureAtlas, BattleStatus battleStatus, OldPlayer oldPlayer, OldPlayer enemy, MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        this.batch = batch;
        this.game = game;
        this.textureAtlas = textureAtlas;
        this.battleStatus = battleStatus;
        this.oldPlayer = oldPlayer;
        this.enemy = enemy;
        AspectRatio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();

        widthCamera = 220;
        heightCamera = 220 / AspectRatio;
        getCoordIsFinished = false;
        ping = 0;

    }

    @Override
    public void show() {


        classicMap = Map.generateMap(batch, textureAtlas);

        //enemy = new OldPlayer(battleStatus.getName(), new Pulsate(textureAtlas, 0, 0));
        //Распределение игроков по позициям
        if (battleStatus.getPositionNumber() == 1) {
            oldPlayer.getCurrentShip().setPosition(200, 400);
            oldPlayer.getCurrentShip().setRotation(0);
            enemy.getCurrentShip().setPosition(800, 200);
            enemy.getCurrentShip().setRotation(0);
        } else if (battleStatus.getPositionNumber() == 2) {
            oldPlayer.getCurrentShip().setPosition(800, 200);
            oldPlayer.getCurrentShip().setRotation(0);
            enemy.getCurrentShip().setPosition(200, 400);
            enemy.getCurrentShip().setRotation(0);
        }
        ////////////////////////////////////////////////////


        camera = new OrthographicCamera(widthCamera, heightCamera);
        camera.position.set(new Vector3(oldPlayer.getCurrentShip().getCenterX(), oldPlayer.getCurrentShip().getCenterX(), 0));
        camX = camera.position.x;
        camY = camera.position.y;
        coord = new Coord(333f, 333f, 0f);
        counter = 0;
        joystick = new Joystick(batch, 0, 10, textureAtlas.findRegion("Dj1p1"), textureAtlas.findRegion("Dj1p2"));
        gasRegulator = new GasRegulator(batch, camX - widthCamera * 0.45f, camY - heightCamera * 0.45f, widthCamera * 0.15f, heightCamera * 0.4f, textureAtlas, oldPlayer.getCurrentShip());
        //turnLeft=new ButtonForProcessor(batch,camX+widthCamera/5,camY,20,20,textureAtlas.findRegion("TurnLeft"));
        //turnRight=new ButtonForProcessor(batch,camX+widthCamera/5+30,camY,20,20,textureAtlas.findRegion("TurnRight"));
        hpBar = new ProgressBar(batch, textureAtlas.findRegion("HProgressBar"), textureAtlas.findRegion("HPLine"), camX - widthCamera * 0.45f, camY + heightCamera * 0.45f, widthCamera * 0.3f, heightCamera * 0.05f, oldPlayer.getCurrentShip().getMaxHp());
        miniMap = new MiniMap(batch, textureAtlas, camX - widthCamera * 0.1f, camY + heightCamera * 0.3f, widthCamera * 0.6f, heightCamera * 0.2f, oldPlayer.getCurrentShip(), enemy.getCurrentShip(), classicMap);
        textManager = new TextManager(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        redFont = textManager.fontInitialize(Color.RED, 0.1f);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(servApi.class);
        processor = new BattleProcessor(joystick, gasRegulator, oldPlayer.getCurrentShip());
        Gdx.input.setInputProcessor(processor);


        getCoord();
    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Battle.delta = delta;
        if (getCoordIsFinished) {
            getCoord();
        }
        /*if ((coord.getX() != null) && (coord.getY() != null)) {
            oldPlayer.getCurrentShip().setPosition(coord.getX(), coord.getY());

        }*/
        //System.out.println(coord.getX() + " " + coord.getY() + " " + "count:" + counter + "number:" + battleStatus.getNumber());
        if (oldPlayer.getCurrentShip().getCenterX() + widthCamera / 2 >= classicMap.getWidth()) {
            camera.position.x = classicMap.getWidth() - widthCamera / 2;
        } else if (oldPlayer.getCurrentShip().getCenterX() - widthCamera / 2 <= 0) {
            camera.position.x = widthCamera / 2;
        } else {
            camera.position.x = oldPlayer.getCurrentShip().getCenterX();
        }

        if (oldPlayer.getCurrentShip().getCenterY() + heightCamera / 2 >= classicMap.getHeight()) {
            camera.position.y = classicMap.getHeight() - heightCamera / 2;
        } else if (oldPlayer.getCurrentShip().getCenterY() - heightCamera / 2 <= 0) {
            camera.position.y = heightCamera / 2;
        } else {
            camera.position.y = oldPlayer.getCurrentShip().getCenterY();
        }


        camX = camera.position.x;
        camY = camera.position.y;
        //
        gasRegulator.setX(camX - widthCamera * 0.45f);
        gasRegulator.setY(camY - heightCamera * 0.45f);
        ///////////////////////////////////
        //обновление позиции кнопок вращения
        //turnLeft.setX(camX+widthCamera/5);
        //turnLeft.setY(camY-heightCamera/3);
        //turnRight.setX(camX+widthCamera/5+30);
        //turnRight.setY(camY-heightCamera/3);
        //////////////////////////////////
        //Обновление позиции прогресс бара
        hpBar.setX(camX - widthCamera * 0.45f);
        hpBar.setY(camY + heightCamera * 0.45f);
        ///////////////////////////////////
        //Обновление позиции миникарты
        miniMap.setX(camX - widthCamera * 0.1f);
        miniMap.setY(camY + heightCamera * 0.3f);
        //////////////////////////////////////
        camera.update();
        //System.out.println("x="+coord.getX()+"y="+coord.getY());

        //System.out.println(" OldPlayer: "+oldPlayer.getName()+"Enemy: "+enemy.getName());

        batch.setProjectionMatrix(camera.combined);
        //Отрисовка карты
        classicMap.draw();
        ////////////////////////////////////

        //Логика движения игроков
        oldPlayer.getCurrentShip().act(enemy.getCurrentShip(), classicMap, joystick.getVector());
        enemy.getCurrentShip().act(oldPlayer.getCurrentShip(), classicMap, new Vector2(0, 0));
        //enemy.getCurrentShip().setRotation(coord.getRotation());
        //////////////////////////////////////
        //Отрисовка игроков
        oldPlayer.getCurrentShip().draw(batch, textureAtlas);
        enemy.getCurrentShip().draw(batch, textureAtlas);
        //////////////////////////////////////


        //Логика столкновения игроков
        if (Intersector.overlapConvexPolygons(oldPlayer.getCurrentShip().getBounds(), enemy.getCurrentShip().getBounds())) {
            oldPlayer.getCurrentShip().setCurrentHp(0);
            enemy.getCurrentShip().setCurrentHp(0);

        }
        /////////////////////////////////////////


        /////////////////////////////////////////////
        //Логика и отрисовка джойстика
        if ((oldPlayer.getCurrentShip().getCurrentHp() > 0) && (enemy.getCurrentShip().getCurrentHp() > 0)) {
            joystick.update(BattleProcessor.offsetX, BattleProcessor.offsetY, BattleProcessor.offsetDynamicX, BattleProcessor.offsetDynamicY);//компенсирует смещение камеры смещением джойстика
            joystick.draw();
            //
            gasRegulator.draw();
            /////////////////////////////
            //Отрисовка кнопок вращения
            //turnLeft.draw();
            //turnRight.draw();
            ////////////////////////////////////
            //Отрисовка прогресс бара
            hpBar.draw(oldPlayer.getCurrentShip().getCurrentHp());
            /////////////////////////////////////
            //Отрисовка миникарты
            miniMap.draw(oldPlayer.getCurrentShip(), enemy.getCurrentShip());
            /////////////////////////////////////////////////////////////

        }
        if (enemy.getCurrentShip().getCurrentHp() <= 0) {
            camera.position.x = enemy.getCurrentShip().getCenterX();
            camera.position.y = enemy.getCurrentShip().getCenterY();
        }
        ///////////////////////////////////////////////
        //Обработка условий завершения боя
        if (!oldPlayer.getCurrentShip().getIsAlive()) {
            joystick.setActive(false);
            oldPlayer.getCurrentShip().nullify();
            endBattle = new EndBattle(oldPlayer, batch, game, mainMenu, classicMap, "Failure");
            game.setScreen(endBattle);
        }
        if (!enemy.getCurrentShip().getIsAlive()) {
            joystick.setActive(false);
            oldPlayer.getCurrentShip().nullify();
            endBattle = new EndBattle(oldPlayer, batch, game, mainMenu, classicMap, "Victory");
            game.setScreen(endBattle);
        }
        //////////////////////////////////////////////
        //System.out.println(oldPlayer.getCurrentShip().getFixingPoints()[0].getWeapon().getX()+"!"+oldPlayer.getCurrentShip().getFixingPoints()[0].getWeapon().getY());
        // System.out.println("OldPlayer:"+oldPlayer.getCurrentShip().getCurrentHp()+"Enemy:"+enemy.getCurrentShip().getCurrentHp());

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


    private void getCoord() {

        startTime = System.currentTimeMillis();
        Call<Coord> call = request.get(battleStatus.getNumber(), oldPlayer.getName(), enemy.getName(), oldPlayer.getCurrentShip().getX(), oldPlayer.getCurrentShip().getY(), oldPlayer.getCurrentShip().getRotation());
        //System.out.println(oldPlayer.getCurrentShip().getX()+" "+oldPlayer.getCurrentShip().getY());
        //System.out.println(enemy.getName()+" "+oldPlayer.getName()+" c="+counter);
        call.enqueue(new Callback<Coord>() {

            @Override
            public void onResponse(Call<Coord> call, Response<Coord> response) {
                //coord=response.body();
                counter++;
                coord.setX(response.body().getX());
                coord.setY(response.body().getY());
                coord.setRotation(response.body().getRotation());
                //System.out.println(coord.getX()+" "+coord.getY());
                if (coord.getX() != null)
                    enemy.getCurrentShip().setPosition(coord.getX(), coord.getY());
                enemy.getCurrentShip().setRotation(coord.getRotation());
                //System.out.println("OldPlayer: "+oldPlayer.getCurrentShip().getX()+" "+oldPlayer.getCurrentShip().getY());
                //System.out.println("Enemy: "+enemy.getCurrentShip().getX()+" "+enemy.getCurrentShip().getY());
                while (!getCoordIsFinished) {
                    if (System.currentTimeMillis() - startTime >= 200) {
                        try {
                            sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getCoordIsFinished = true;
                    }
                }
                //System.out.println("getCoord succes!!!");
                //getCoordIsFinished=true;
                estimatedTime = System.currentTimeMillis() - startTime;
                ping = estimatedTime;

                //System.out.println("Ping:"+ping);

            }

            @Override
            public void onFailure(Call<Coord> call, Throwable t) {
                getCoordIsFinished = true;
                //System.out.println("getCoord failure");
            }
        });

    }


}
