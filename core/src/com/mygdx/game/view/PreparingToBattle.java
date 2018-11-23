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
import com.mygdx.game.ServModels.ServPlayer;
import com.mygdx.game.ServModels.ServShip;
import com.mygdx.game.control.ConnectToBattleProcessor;
import com.mygdx.game.control.ServerListener;
import com.mygdx.game.model.Asteroid;
import com.mygdx.game.model.AsteroidField;
import com.mygdx.game.model.Asteroids.Asteroid1;
import com.mygdx.game.model.PhysicShip;
import com.mygdx.game.model.PhysicShips.StarFighter;
import com.mygdx.game.requests.BattleInfo;
import com.mygdx.game.model.PhysicObject;
import com.mygdx.game.model.Player;
import com.mygdx.game.requests.ClientStartInfo;
import com.mygdx.game.requests.HostStartInfo;
import com.mygdx.game.requests.ServStartInfo;
import com.mygdx.game.utils.TextManager;

import java.io.IOException;

import static java.lang.Thread.sleep;

/**
 * Created by Sash on 30.10.2018.
 */

public class PreparingToBattle implements Screen {
    SpriteBatch batch;
    Game game;
    DebugBattle debugBattle;
    TextureAtlas textureAtlas;
    BitmapFont blueFont;
    TextManager textManager;
    static Client client;
    //IP сервера для подключения
    static String ip = "192.168.0.56";
    //Порт к которому мы будем подключатся
    static int tcpPort = 27960, udpPort = 27960;
    public boolean messageReceived = false;
    ServerListener listener;

    BattleInfo battleInfo;
    Player player;
    PhysicShip ship;
    ServPlayer servPlayer;

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
        Gdx.input.setInputProcessor(new ConnectToBattleProcessor());
        world=new World(new Vector2(0,0),false);
        //asteroidField=new AsteroidField(15,30,190,114);

        //asteroidField.generate();



        //game.setScreen(new TestWindow());


        player=new Player();
        player.generateName();
        ship=new StarFighter(0,0,0);
        player.setCurrentShip(ship);
        servPlayer=player.toServ();


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
        kryo.register(ServShip.class);
        kryo.register(ClientStartInfo.class);
        kryo.register(ServPlayer.class);
        kryo.register(ServStartInfo.class);
        client.start();


        client.addListener(new Listener()
        {
            public void connected(Connection c)
            {
                //battleInfo.setRequestType(BattleInfo.RequestType.Adding);
                //c.sendTCP(battleInfo);
                c.sendTCP(servPlayer);
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
               else if(p instanceof ServStartInfo) {
                   ServStartInfo csi = (ServStartInfo) p;
                   System.out.println("Мы получили начальные данные от сервера");
                   ServAsteroidField sAstF = new ServAsteroidField(csi.getAsteroidField());
                   asteroidField = new AsteroidField();
                   for (int i = 0; i < sAstF.getAsteroids().length; i++) {
                       asteroidField.getAsteroids().add(new Asteroid1(sAstF.getAsteroids()[i].getX(), sAstF.getAsteroids()[i].getY(), sAstF.getAsteroids()[i].getRotation(),
                               sAstF.getAsteroids()[i].getWidth(), sAstF.getAsteroids()[i].getHeight(), new Vector2(0, 0), sAstF.getAsteroids()[i].getHp()));
                   }
                   //game.setScreen(new DebugBattle(batch,game,textureAtlas,client,battleInfo.isHost(),battleInfo.getEnemyId(),battleInfo.getPlayer(),battleInfo.getEnemy()));
                   //game.setScreen(debugBattle);
                   System.out.println("Size"+asteroidField.getAsteroids().size);
                   messageReceived=true;
               }

            }
        });


        try {
            client.connect(5000,ip,tcpPort,udpPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println("Клиент покидает сервер");

        //game.setScreen(new DebugBattle(batch,game,textureAtlas,client,battleInfo.isHost(),battleInfo.getEnemyId(),battleInfo.getPlayer(),battleInfo.getEnemy()));
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


        if(messageReceived)
        {
            debugBattle=new DebugBattle(batch,game,textureAtlas,client,new Player(),new Player(),asteroidField);
            game.setScreen(debugBattle);
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
