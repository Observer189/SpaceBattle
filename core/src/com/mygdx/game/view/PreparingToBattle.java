package com.mygdx.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.ServModels.ServAsteroid;
import com.mygdx.game.ServModels.ServAsteroidField;
import com.mygdx.game.ServModels.ServObject;
import com.mygdx.game.control.ConnectToBattleProcessor;
import com.mygdx.game.control.ServerListener;
import com.mygdx.game.model.Asteroid;
import com.mygdx.game.model.AsteroidField;
import com.mygdx.game.model.Asteroids.Asteroid1;
import com.mygdx.game.requests.BattleInfo;
import com.mygdx.game.model.PhysicObject;
import com.mygdx.game.model.Player;
import com.mygdx.game.requests.HostStartInfo;
import com.mygdx.game.utils.TextManager;

import java.io.IOException;

import static java.lang.Thread.sleep;

/**
 * Created by Sash on 30.10.2018.
 */

public class PreparingToBattle implements Screen {
    SpriteBatch batch;
    Game game;
    TextureAtlas textureAtlas;
    BitmapFont blueFont;
    TextManager textManager;
    static Client client;
    //IP сервера для подключения
    static String ip = "192.168.0.56";
    //Порт к которому мы будем подключатся
    static int tcpPort = 27960, udpPort = 27960;
    public static boolean messageReceived = false;
    ServerListener listener;

    BattleInfo battleInfo;
    Player player;
    boolean isInQueue;
    long lastRequestTime;

    AsteroidField field;
    World world;
    AsteroidField asteroidField;
    public PreparingToBattle(SpriteBatch batch, Game game, TextureAtlas textureAtlas)
    {
        this.batch = batch;
        this.game = game;
        this.textureAtlas = textureAtlas;
    }
    @Override
    public void show() {

        world=new World(new Vector2(0,0),false);
        Gdx.input.setInputProcessor(new ConnectToBattleProcessor());
        player=new Player();
        player.generateName();
        field=new AsteroidField(15,30,50,40);
        field.generate();

        battleInfo=new BattleInfo();
        battleInfo.setPlayer(player);
        isInQueue=false;
        textManager=new TextManager(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        blueFont=textManager.fontInitialize(Color.BLUE,1);
        client=new Client(32768,32768);
        listener=new ServerListener();
        Kryo kryo=client.getKryo();

        kryo.register(BattleInfo.class);
        kryo.register(BattleInfo.RequestType.class);
        kryo.register(Player.class);
        kryo.register(HostStartInfo.class);
        kryo.register(ServObject.class);
        kryo.register(ServAsteroid.class);
        kryo.register(ServAsteroidField.class);
        kryo.register(ServAsteroid[].class);
        client.start();


        client.addListener(new Listener()
        {
            public void connected(Connection c)
            {
                battleInfo.setRequestType(BattleInfo.RequestType.Adding);
                c.sendTCP(battleInfo);
                System.out.println("Отправляем запрос на добавление в очередь");
            }
            public void received(Connection c, Object p) {
               if(p instanceof BattleInfo)
               {
                   battleInfo=(BattleInfo)p;
                   if(battleInfo.getStatus().equals("inQueue"))
                   {
                       lastRequestTime=System.currentTimeMillis();
                   }
               }

            }
        });


        try {
            client.connect(5000,ip,tcpPort,udpPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Клиент покидает сервер");


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if((battleInfo.getStatus()!=null)&&(battleInfo.getStatus().equals("inQueue"))&&(System.currentTimeMillis()-lastRequestTime>3000))
        {
            battleInfo.setRequestType(BattleInfo.RequestType.Info);
            client.sendTCP(battleInfo);
        }
        if((battleInfo.getStatus()!=null)&&(battleInfo.getStatus().equals("inQueue")))
        {
            textManager.displayMessage(batch,blueFont,"You are in Queue",300,320);
            textManager.displayMessage(batch,blueFont,"Players in queue:"+battleInfo.getQueueSize(),300,280);
        }


        if((battleInfo.getStatus()!=null)&&(battleInfo.getStatus().equals("inBattle")))
        {
            textManager.displayMessage(batch,blueFont,"Battle starts",300,500);
            textManager.displayMessage(batch,blueFont,"Your enemy id:"+battleInfo.getEnemyId(),300,400);
            if(battleInfo.isHost())
            {
                textManager.displayMessage(batch,blueFont,"You are host",300,300);
            }




            game.setScreen(new DebugBattle(batch,game,textureAtlas,client,battleInfo.isHost(),battleInfo.getEnemyId(),battleInfo.getPlayer(),battleInfo.getEnemy()));
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
}
