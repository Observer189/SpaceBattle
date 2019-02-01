package com.mygdx.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.ServModels.OldServShip;
import com.mygdx.game.control.ConnectToBattleProcessor;
import com.mygdx.game.model.BattleStatus;
import com.mygdx.game.model.OldPlayer;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.Ship;
import com.mygdx.game.model.Ships.Axe;
import com.mygdx.game.model.Ships.Dakkar;
import com.mygdx.game.model.Ships.Dashing;
import com.mygdx.game.model.Ships.Pulsate;
import com.mygdx.game.model.Ships.Rock;
import com.mygdx.game.model.Ships.Sudden;
import com.mygdx.game.requests.servApi;
import com.mygdx.game.utils.TextManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Thread.sleep;

/**
 * Created by Sash on 03.05.2018.
 */

public class ConnectToBattle implements Screen {
    private MainMenu mainMenu;
    SpriteBatch batch;
    Game game;
    TextureAtlas textureAtlas;
    private servApi request;
    private BattleStatus battleStatus;
    OldPlayer oldPlayer;
    private OldServShip servShip;
    TextManager textManager;
    private int counter;
    private boolean getBattleIsFinished;
    private InputProcessor processor;
    private BitmapFont blueFont;
    private final String baseURL = "https://star-project-serv.herokuapp.com/";

    ConnectToBattle(SpriteBatch batch, Game game, TextureAtlas textureAtlas, OldPlayer oldPlayer, MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        this.batch = batch;
        this.game = game;
        this.textureAtlas = textureAtlas;
        this.oldPlayer = oldPlayer;


    }

    @Override
    public void show() {

        //oldPlayer = new OldPlayer("oldPlayer", new Sudden(textureAtlas,0,0));
        //oldPlayer.generateName();
        //game.setScreen(new DebugBattle(batch,game,textureAtlas,new Client(),true,1,new Player(),new Player()));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(servApi.class);
        servShip = new OldServShip(oldPlayer.getCurrentShip().toServ());
        processor = new ConnectToBattleProcessor();
        //Gdx.input.setInputProcessor(processor);
        textManager = new TextManager(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        blueFont = textManager.fontInitialize(Color.BLUE, 1);
        battleStatus = new BattleStatus(null, null, null, null, "add", null);

        counter = 0;
        getBattleIsFinished = false;
        getBattleNumber();

        /*if((battleStatus.getNumber()!=null)&&(battleStatus.getStatus().equals("ready"))) {

            game.setScreen(new Battle(batch, game, textureAtlas, battleStatus,oldPlayer,mainMenu));
        }*/
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if ((Gdx.input.isKeyPressed(Input.Keys.BACK)) && (!battleStatus.getStatus().equals("ready"))) {
            game.setScreen(mainMenu);
        }
        if (counter == 100) counter = 0;
        counter++;
        if (getBattleIsFinished) {
            getBattleIsFinished = false;
            getBattleNumber();
        }
        //System.out.println(battleStatus.getNumber()+" "+battleStatus.getStatus()+"QueueSize:"+battleStatus.getQueueSize());

        if (battleStatus.getStatus().equals("add")) {
            textManager.displayMessage(batch, blueFont, "Connection to server...", 300, 300);
        }
        if (battleStatus.getStatus().equals("wait")) {
            textManager.displayMessage(batch, blueFont, "Search enemy...", 300, 300);
            textManager.displayMessage(batch, blueFont, "Players in queue:" + " " + battleStatus.getQueueSize(), 300, 350);
        }
        if ((battleStatus.getNumber() != null) && (battleStatus.getStatus().equals("ready")) && (battleStatus.getShip() != null)) {


            OldPlayer enemy = new OldPlayer(battleStatus.getName(), setShipByServ(battleStatus.getShip()));


            game.setScreen(new Battle(batch, game, textureAtlas, battleStatus, oldPlayer, enemy, mainMenu));
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
        //batch.dispose();
        //textureAtlas.dispose();
        //game.dispose();
    }

    private void getBattleNumber() {

        /*Call<BattleStatus> call = request.getBattleNumber(oldPlayer.getName(),battleStatus.getStatus());
        try {
            battleStatus.setBattleStatus(call.execute().body());


        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Call<BattleStatus> call = request.getBattleNumber(oldPlayer.getName(), servShip, battleStatus.getStatus());
        System.out.println("!" + servShip + "!");
        call.enqueue(new Callback<BattleStatus>() {
            @Override
            public void onResponse(Call<BattleStatus> call, Response<BattleStatus> response) {

                battleStatus.setBattleStatus(response.body());


                try {
                    sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getBattleIsFinished = true;
            }

            @Override
            public void onFailure(Call<BattleStatus> call, Throwable t) {
                getBattleIsFinished = true;
                System.out.println("Connection failure");
            }
        });
    }

    public Ship setShipByServ(OldServShip ship) {
        Ship shipFromServ;
        System.out.println("!" + ship.getName() + "!");
        if (ship.getName().equals("Pulsate"))
            shipFromServ = new Pulsate(textureAtlas, 0, 0);
        else if (ship.getName().equals("Dakkar"))
            shipFromServ = new Dakkar(textureAtlas, 0, 0);
        else if (ship.getName().equals("Axe"))
            shipFromServ = new Axe(textureAtlas, 0, 0);
        else if (ship.getName().equals("Dashing"))
            shipFromServ = new Dashing(textureAtlas, 0, 0);
        else if (ship.getName().equals("Rock"))
            shipFromServ = new Rock(textureAtlas, 0, 0);
        else if (ship.getName().equals("Sudden"))
            shipFromServ = new Sudden(textureAtlas, 0, 0);
        else {
            System.out.println("Ship is not exist");
            return null;
        }
        return shipFromServ;

    }
}
