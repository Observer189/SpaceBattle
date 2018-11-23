package com.mygdx.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.ServModels.ServAsteroid;
import com.mygdx.game.ServModels.ServAsteroidField;
import com.mygdx.game.model.PhysicShips.Fury;
import com.mygdx.game.model.PhysicShips.StarFighter;
import com.mygdx.game.control.BattleContactListener;
import com.mygdx.game.control.DebugBattleProcessor;
import com.mygdx.game.model.Asteroid;
import com.mygdx.game.model.AsteroidField;
import com.mygdx.game.model.Asteroids.Asteroid1;
import com.mygdx.game.model.Map;
import com.mygdx.game.model.PhysicShip;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.Ships.Rock;
import com.mygdx.game.model.WeaponPoint;
import com.mygdx.game.requests.ClientStartInfo;
import com.mygdx.game.requests.HostStartInfo;
import com.mygdx.game.requests.ServStartInfo;
import com.mygdx.game.utils.BattleInfoPanel;
import com.mygdx.game.utils.ButtonForProcessor;
import com.mygdx.game.utils.GasRegulator;
import com.mygdx.game.utils.Helm;
import com.mygdx.game.utils.ProgressBar;

import sun.management.HotspotInternal;

import static java.lang.Thread.sleep;

/**
 * Created by Sash on 15.06.2018.
 */

public class DebugBattle implements Screen {
    SpriteBatch batch;
    Game game;
    TextureAtlas textureAtlas;
    OrthographicCamera camera;

    float width;
    float height;

    public static float camX;
    public static float camY;
    public static float delta;
    public static float widthCamera;
    public static float heightCamera;

    final public float AspectRatio;
    PhysicShip ship;
    PhysicShip enemyShip;

    Map map;
    AsteroidField asteroidField;
    //Asteroid1 asteroid;

    World world;
    Box2DDebugRenderer rend;
    BattleContactListener battleContactListener;
    InputProcessor processor;

    GasRegulator gasRegulator;
    Helm helm;
    ProgressBar energyBar;
    BattleInfoPanel battleInfoPanel;
    ButtonForProcessor fireButton;
    //ButtonForProcessor turnLeft;
    //ButtonForProcessor turnRight;


    float endMapCoef;

    Client client;


    Player player;
    Player enemy;

    boolean isStartInfoReceived;
    boolean isReady;
    public DebugBattle(SpriteBatch batch, Game game, TextureAtlas textureAtlas,Client client,Player player,Player enemy,AsteroidField asteroidField)
    {
        System.out.println("Заходим в бой");
        this.batch = batch;
        this.game = game;

        this.textureAtlas = textureAtlas;
        this.client=client;


        this.player=player;
        this.enemy=enemy;
        isStartInfoReceived=false;
        isReady=false;
        AspectRatio=(float) Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
        widthCamera=30;
        heightCamera=30/AspectRatio;
        width =1.5f;
        height =2;
        endMapCoef=0.05f;
        System.out.println("Size"+asteroidField.getAsteroids().size);
        this.asteroidField = new AsteroidField(asteroidField);
        System.out.println("Size"+this.asteroidField.getAsteroids().size);
        isStartInfoReceived=true;
    }
    @Override
    public void show() {

        client.addListener(new Listener()
        {
            public void received(Connection c, Object p) {

            if(p instanceof ClientStartInfo)
            {
                ClientStartInfo csi=(ClientStartInfo)p;
                System.out.println("Мы получили начальные данные от клиента");
                enemyShip=PhysicShip.fromServ(csi.getShip());
                isStartInfoReceived=true;

            }

            }
        });
        world=new World(new Vector2(0,0),false);

        map=Map.generateMap(batch,textureAtlas);


        ship = new StarFighter( 50, 30,180);
        enemyShip=new Fury(55,30,0);

        //asteroidField=new AsteroidField(15,30,map.getWidth()*(1-endMapCoef),map.getHeight()*(1-endMapCoef));
        //asteroidField.generate();
        /*while (!isStartInfoReceived)
        {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        ship.create(textureAtlas, world);
        enemyShip.create(textureAtlas, world);

        asteroidField.create(textureAtlas, world);


        camera=new OrthographicCamera(widthCamera,heightCamera);
        camera.position.set(new Vector3(500,500,0));
        camX =camera.position.x;
        camY =camera.position.y;
        rend=new Box2DDebugRenderer();
        gasRegulator=new GasRegulator(batch,camX-widthCamera*0.45f,camY-heightCamera*0.45f,widthCamera*0.15f,heightCamera*0.4f,textureAtlas,new Rock(textureAtlas,0,0));
        helm=new Helm(textureAtlas,batch,camera,-6.5f,-7.5f,0.15f,0.15f*AspectRatio, ship);
        fireButton=new ButtonForProcessor(batch,camera,6.5f,-7.5f,0.1f,0.1f*AspectRatio,textureAtlas.findRegion("FireButton"));

        battleInfoPanel=new BattleInfoPanel(batch,textureAtlas,-0.45f,0.4f,0.3f,0.1f, ship.getMaxHP(), ship.getMaxEnergy(),camera);

        battleContactListener=new BattleContactListener();
        processor=new DebugBattleProcessor(gasRegulator,helm,fireButton, ship);
        Gdx.input.setInputProcessor(processor);



        createRectJoint();
        createWall();
        world.setContactListener(battleContactListener);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Size"+asteroidField.getAsteroids().size);
    }

    @Override
    public void render(float delta) {

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            this.delta = delta;

            camera.position.set(new Vector3(ship.getX(), ship.getY(), 0));
            camX = camera.position.x;
            camY = camera.position.y;
            gasRegulator.setX(camX - widthCamera * 0.45f);
            gasRegulator.setY(camY - heightCamera * 0.45f);

            battleInfoPanel.update();

            //////////////////////////////////

            camera.update();

            batch.setProjectionMatrix(camera.combined);
            //Отрисовка карты
            map.draw();
            ////////////////////////////////////
            //ship.draw(batch);
            //ship2.draw(batch);
            ship.draw(batch);
            if (enemyShip != null)
                enemyShip.draw(batch);
            //asteroid.draw(batch);
            asteroidField.draw(batch);
            //asteroid.draw(batch);
            //point.draw(batch);
            //
            gasRegulator.draw();
            helm.draw();
            fireButton.draw();
            //energyBar.draw(ship.getEnergy());
            battleInfoPanel.draw(ship.getHp(), ship.getEnergy());
            /////////////////////////////
            //Отрисовка кнопок вращения
            //turnLeft.draw();
            //turnRight.draw();
            rend.render(world, camera.combined);
            world.step(1 / 60f, 4, 4);
            //ship.move();
            //ship2.move();
            helm.updateShip(ship.findRotationSpeed());
            ship.move();
            //asteroid.update();
            asteroidField.update();
            if (enemyShip != null) {
                if (enemyShip.getHp() < 0) {
                    enemyShip.destroy();
                    enemyShip = null;
                }
            }
        }
        //ury.setRotationDirection(1);
        //System.out.println(battleInfoPanel.getHPBar().getX()-camera.position.x);


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
    public void createRect()
    {
        BodyDef bDef=new BodyDef();
        bDef.type= BodyDef.BodyType.DynamicBody;
        bDef.position.set((float)(Math.random()*150),(float)(Math.random()*100));
        Body body=world.createBody(bDef);

        FixtureDef fDef=new FixtureDef();
        PolygonShape shape=new PolygonShape();
        shape.setAsBox((float)(Math.random()*3),(float)(Math.random()*3));
        fDef.shape=shape;
        fDef.density=1000;
        fDef.friction=0.5f;
        fDef.restitution=0.2f;
        body.createFixture(fDef);
        body.setAngularVelocity(1);
    }
    public void createRectJoint()
    {
        BodyDef bDef1=new BodyDef();
        bDef1.type= BodyDef.BodyType.DynamicBody;
        bDef1.position.set(47,30);
        Body body=world.createBody(bDef1);

        FixtureDef fDef1=new FixtureDef();
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(2,2);
        fDef1.shape=shape;
        fDef1.density=2000;
        fDef1.friction=0.5f;
        fDef1.restitution=0.2f;
        body.createFixture(fDef1);
        body.setAngularVelocity(0);

        BodyDef bDef2=new BodyDef();
        bDef2.type= BodyDef.BodyType.DynamicBody;
        bDef2.position.set(47,30);
        Body body2=world.createBody(bDef2);

        FixtureDef fDef2=new FixtureDef();
        PolygonShape shape2=new PolygonShape();
        shape2.set(new float[]{-1f,-1f,1f,-1f,0f,0f});
        fDef2.shape=shape2;
        fDef2.density=2000;
        fDef2.friction=0.5f;
        fDef2.restitution=0.2f;
        body2.createFixture(fDef2);
        FixtureDef fDef3=new FixtureDef();
        PolygonShape shape3=new PolygonShape();
        shape3.set(new float[]{0.f,0.f,1f,1f,-1f,1f});
        fDef3.shape=shape3;
        fDef3.density=2000;
        fDef3.friction=0.5f;
        fDef3.restitution=0.2f;
        body2.createFixture(fDef3);
        body2.setAngularVelocity(0);

        WeldJointDef jointDef=new WeldJointDef();
        jointDef.bodyA=body;
        jointDef.bodyB=body2;
        jointDef.localAnchorA.set(2,2);
        jointDef.localAnchorB.set(-1,-1);
        world.createJoint(jointDef);
    }
    public void createWall()
    {
        BodyDef bDef=new BodyDef();
        bDef.type= BodyDef.BodyType.StaticBody;
        bDef.position.set(0,0);
        Body body=world.createBody(bDef);

        FixtureDef fDef=new FixtureDef();
        ChainShape shape=new ChainShape();
        shape.createLoop(new float[]{map.getWidth()*endMapCoef,map.getHeight()*endMapCoef,
                map.getWidth()*(1-endMapCoef),map.getHeight()*endMapCoef,
                map.getWidth()*(1-endMapCoef),map.getHeight()*(1-endMapCoef),
                map.getWidth()*endMapCoef,map.getHeight()*(1-endMapCoef)});
        fDef.shape=shape;
        fDef.friction=0.2f;
        fDef.restitution=0.5f;
        body.createFixture(fDef);
        body.setUserData(new Object(){
            @Override
            public String toString() {
                return "EndMap";
            }
        });
    }
    public void createAsteroid()
    {
        Asteroid[] asteroids=new Asteroid[10];
    }

    public void setAsteroidField(AsteroidField asteroidField) {
        this.asteroidField = new AsteroidField(asteroidField);
    }
}
