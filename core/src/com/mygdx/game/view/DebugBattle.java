package com.mygdx.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.mygdx.game.Modules.WeaponModule;
import com.mygdx.game.PhysicShips.Fury;
import com.mygdx.game.control.DebugBattleProcessor;
import com.mygdx.game.model.Map;
import com.mygdx.game.model.PhysicShip;
import com.mygdx.game.model.Ships.Rock;
import com.mygdx.game.model.WeaponPoint;
import com.mygdx.game.utils.ButtonForProcessor;
import com.mygdx.game.utils.GasRegulator;
import com.mygdx.game.utils.Helm;
import com.mygdx.game.utils.ProgressBar;
import com.mygdx.game.utils.Size;

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
    Fury fury;

    Map map;
    PhysicShip ship;
    PhysicShip ship2;
    World world;
    Box2DDebugRenderer rend;
    InputProcessor processor;

    GasRegulator gasRegulator;
    Helm helm;
    ProgressBar energyBar;
    //ButtonForProcessor turnLeft;
    //ButtonForProcessor turnRight;

    WeldJointDef joint;
    Joint j;
    WeaponPoint point;
    float endMapCoef;
    public DebugBattle(SpriteBatch batch, Game game, TextureAtlas textureAtlas)
    {
        this.batch = batch;
        this.game = game;
        this.textureAtlas = textureAtlas;
        AspectRatio=(float) Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
        widthCamera=20;
        heightCamera=20/AspectRatio;
        width =1.5f;
        height =2;
        endMapCoef=0.05f;
    }
    @Override
    public void show() {
        world=new World(new Vector2(0,0),false);

        map=Map.generateMap(batch,textureAtlas);
        /*ship=new PhysicShip(textureAtlas.findRegion("Dashing"),50,30,width,height,0.05f,
                new float[]{-width/2,-height/2+height*0.26f,
                -width/2,-height/2+height*0.135f,
                -width/2+width*0.18f,-height/2,
                        -width/2+width*0.81f,-height/2,
                        width/2,-height/2+height*0.135f,
                        width/2,-height/2+height*0.26f,
                        -width/2+width*0.83f,-height/2+height*0.41f,
                        -width/2+width*0.157f,-height/2+height*0.41f
                        },world);*/

        /*ship2=new PhysicShip(textureAtlas.findRegion("Dashing"),50f,30,
                width,height,0.05f,new float[]{
                -width/2+width*0.1f,-height/2+height*0.567f,
                -width/2+width*0.83f,-height/2+height*0.41f,
                -width/2+width*0.157f,-height/2+height*0.41f,
                -width/2+width*0.89f,-height/2+height*0.567f,
                -width/2+width*0.67f,height/2,
                -width/2+width*0.33f,height/2},world);*/
        fury= new Fury(textureAtlas,50,30,world);
        fury.create();
        fury.getBodies()[0].setTransform(fury.getBodies()[0].getPosition(), (float) Math.toRadians(0));
        fury.getBodies()[1].setTransform(fury.getBodies()[0].getPosition(), (float) Math.toRadians(0));
        camera=new OrthographicCamera(widthCamera,heightCamera);
        camera.position.set(new Vector3(500,500,0));
        camX =camera.position.x;
        camY =camera.position.y;
        rend=new Box2DDebugRenderer();
        gasRegulator=new GasRegulator(batch,camX-widthCamera*0.45f,camY-heightCamera*0.45f,widthCamera*0.15f,heightCamera*0.4f,textureAtlas,new Rock(textureAtlas,0,0));
        helm=new Helm(textureAtlas,batch,camera,-4.5f,-5f,0.15f,0.15f*AspectRatio,fury);
        energyBar=new ProgressBar(batch,textureAtlas.findRegion("HProgressBar"),textureAtlas.findRegion("HPLine"),
                camX-widthCamera*0.45f,camY+heightCamera*0.45f,-widthCamera*0.45f,heightCamera*0.45f,widthCamera*0.3f,heightCamera*0.05f,fury.getMaxEnergy(),camera);
        //turnLeft=new ButtonForProcessor(batch,camX+widthCamera/5,camY,widthCamera/11,heightCamera/11,textureAtlas.findRegion("TurnLeft"));
        //turnRight=new ButtonForProcessor(batch,camX+widthCamera/5+widthCamera/9,camY,widthCamera/11,heightCamera/11,textureAtlas.findRegion("TurnRight"));
        processor=new DebugBattleProcessor(gasRegulator,helm,fury);
        Gdx.input.setInputProcessor(processor);

        //point=new WeaponPoint(new WeaponModule(textureAtlas.findRegion("Machinegun"),50,30, Size.Small,10,world),fury.getBodies()[0],world);
        //point.installModule(new WeaponModule(textureAtlas.findRegion("Machinegun"),51,31, Size.Small,10,world),fury.getBodies()[0]);
        /*joint = new WeldJointDef();
        joint.collideConnected=false;
        joint.bodyA=fury.getBodies()[0];
        joint.bodyB=fury.getBodies()[1];

        joint.initialize(fury.getBodies()[0],fury.getBodies()[1],new Vector2(50,30));
        j=world.createJoint(joint);*/

        for(int i=0;i<=10;i++)
        {
            createRect();
        }

        createWall();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.delta=delta;

        camera.position.set(new Vector3(fury.getX(),fury.getY(),0));
        camX =camera.position.x;
        camY =camera.position.y;
        gasRegulator.setX(camX-widthCamera*0.45f);
        gasRegulator.setY(camY-heightCamera*0.45f);

        //обновление позиции кнопок вращения
        //turnLeft.setX(camX+widthCamera/5);
        //turnLeft.setY(camY-heightCamera/3);
        //turnRight.setX(camX+widthCamera/5+widthCamera/9);
        //turnRight.setY(camY-heightCamera/3);
        //////////////////////////////////

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        //Отрисовка карты
        map.draw();
        ////////////////////////////////////
        //ship.draw(batch);
        //ship2.draw(batch);
        fury.draw(batch);
        //point.draw(batch);
        //
        gasRegulator.draw();
        helm.draw();
        energyBar.draw(fury.getEnergy());
        /////////////////////////////
        //Отрисовка кнопок вращения
        //turnLeft.draw();
        //turnRight.draw();
        //rend.render(world,camera.combined);
        world.step(1/60f,4,4);
        //ship.move();
        //ship2.move();
        helm.updateShip();
        fury.move();
        energyBar.update();
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
        fDef.density=2;
        fDef.friction=0.5f;
        fDef.restitution=0.2f;
        body.createFixture(fDef);
        body.setAngularVelocity(1);
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
    }

}
