package com.mygdx.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.model.OldPlayer;
import com.mygdx.game.model.Ship;
import com.mygdx.game.requests.servApi;
import com.mygdx.game.utils.TextManager;
import com.mygdx.game.utils.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShipShow implements Screen {
    //for drawing
    OrthographicCamera camera;
    SpriteBatch batch;
    Game game;
    TextManager textManager;
    private BitmapFont font, font1;
    public TextureAtlas textureAtlas;
    Skin skin;
    Stage stage;
    private int xt;
    private int yt;
    private int dyt;
    private Boolean MakeToast = false;
    private Boolean MakeToast1 = false;
    private Boolean MakeToast2 = false;
    private Ship ship;
    //Buttons
    private StageForButton Back, Buy;
    private Button.ButtonStyle BaStyle, BuStyle;
    //Sreens
    private Screen ShList;
    private Toast toast, toast1, toast2;
    private MainMenu menu;
    OldPlayer oldPlayer;
    //for ship's params
    private Image Shipimg;
    private String name;
    int cost;
    int maxHp;
    float width, height;

    float velocity;
    float maxSpeed;

    servApi request;
    private final String baseURL = "https://star-project-serv.herokuapp.com/";

    ShipShow(Ship ship, Game game, MainMenu menu, OldPlayer oldPlayer, float width, float height) {

        this.ship = ship;
        this.game = game;
        this.menu = menu;
        this.oldPlayer = oldPlayer;
        this.width = width;
        this.height = height;
    }


    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);

        textureAtlas = new TextureAtlas(Gdx.files.internal("TexturePack.atlas"));
        stage = new Stage();
        skin = new Skin();
        skin.addRegions(textureAtlas);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(servApi.class);

        Shipimg = new Image(ship.getImg());
        Shipimg.setSize((float) (width * 2), (float) (height * 2));
        Shipimg.setPosition(Gdx.graphics.getWidth() / Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2 - Shipimg.getHeight() / 2);
        //used for textManager params

        xt = (int) (Shipimg.getX() + Shipimg.getWidth() * 1.2);
        yt = (int) ((Shipimg.getY() + Shipimg.getHeight()) * 1.1);
        stage.addActor(Shipimg);
        dyt = Gdx.graphics.getHeight() / 9;

        batch = new SpriteBatch();
        camera = new OrthographicCamera();

        textManager = new TextManager(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ShList = new ShopList(game, batch, textureAtlas, menu, oldPlayer);
        font = textManager.fontInitialize(Color.WHITE, 1);
        font1 = textManager.fontInitialize(Color.WHITE, 1);
        Toast.ToastFactory toastFactory = new Toast.ToastFactory.Builder()
                .font(font1)
                .build();
        toast = toastFactory.create("Successfully bought", Toast.Length.LONG);
        toast1 = toastFactory.create("Already bought", Toast.Length.LONG);
        toast2 = toastFactory.create("Not enough money!", Toast.Length.LONG);


        //Buy button
        BuStyle = new Button.ButtonStyle();
        BuStyle.up = skin.getDrawable("Buy-up");
        BuStyle.down = skin.getDrawable("Buy-down");
        Buy = new StageForButton(BuStyle, (int) (Gdx.graphics.getWidth() - Shipimg.getHeight()), (int) ((Gdx.graphics.getHeight()) / 2 - Shipimg.getHeight() / 2), (int) (Gdx.graphics.getHeight() / 3.6), (int) (Gdx.graphics.getHeight() / 3.6));

        Buy.btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                // ShList=new ShopList(game,batch,textureAtlas,menu,oldPlayer);
                if (oldPlayer.resources.shipList.contains(ship)) {
                    MakeToast1 = true;
                } else {
                    if (!oldPlayer.resources.shipList.contains(ship) && (menu.oldPlayer.resources.getMoney() >= ship.getCost())) {
                        MakeToast = true;
                        int mon = menu.oldPlayer.getMoney() - ship.getCost();
                        menu.oldPlayer.setMoney(mon);
                        updatePlayerMoney();
                        oldPlayer.resources.shipList.add(ship);

                    } else MakeToast2 = true;

                }


            }
        });
        //Back button
        BaStyle = new Button.ButtonStyle();
        BaStyle.up = skin.getDrawable("Back-up");
        BaStyle.down = skin.getDrawable("Back-down");
        Back = new StageForButton(BaStyle, (int) Shipimg.getX(), (int) (Gdx.graphics.getHeight() / Gdx.graphics.getHeight()), (int) (Gdx.graphics.getWidth() / 8.8), (int) (Gdx.graphics.getHeight() / 4.96));
        System.out.println("Clicker");
        Back.btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.setScreen(ShList);
                System.out.println(oldPlayer.resources.shipList);

            }
        });
        Image[] tube;
        tube = new Image[6];
        for (int i = 0; i < 6; i++) {
            tube[i] = new Image(skin.getDrawable("Tube"));
            tube[i].setSize((float) (Gdx.graphics.getWidth() / 3.2), Gdx.graphics.getHeight() / 6);
            tube[i].setPosition((float) (xt * 0.9), (float) ((yt - dyt * (i + 1))));
            stage.addActor(tube[i]);


        }


        InputMultiplexer in = new InputMultiplexer();
        in.addProcessor(Buy);
        in.addProcessor(Back);
        Gdx.input.setInputProcessor(in);


    }

    @Override
    public void render(float delta) {
        camera.setToOrtho(false, (float) (Gdx.graphics.getWidth() / 1.6), (float) (Gdx.graphics.getHeight() / 1.5));
        LoginView.textrure.draw();
        LoginView.star.draw();
        if (name == "1") name = "Pulsate";
        stage.act(delta);
        stage.draw();
        textManager.displayMessage(batch, font, "" + ship.getName(), xt, yt);
        textManager.displayMessage(batch, font, "Price: " + ship.getCost(), xt, yt - dyt);
        textManager.displayMessage(batch, font, "HP: " + ship.getMaxHp(), xt, yt - dyt * 2);
        textManager.displayMessage(batch, font, "Speed: " + ship.getMaxSpeed(), xt, yt - dyt * 3);
        textManager.displayMessage(batch, font, "Velocity: " + ship.getVelocity(), xt, yt - dyt * 4);
        textManager.displayMessage(batch, font, "Weapons: " + ship.getFixingPointsDigit(), xt, yt - dyt * 5);

        if (MakeToast) {
            toast.render(Gdx.graphics.getDeltaTime());

        }
        if (MakeToast1) {
            toast1.render(Gdx.graphics.getDeltaTime());


        }
        if (MakeToast2) {
            toast2.render(Gdx.graphics.getDeltaTime());


        }
        Buy.act(delta);
        Buy.draw();
        Back.act();
        Back.draw();

        batch.begin();
        batch.end();
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(ShList);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        menu.music.pause();


    }

    @Override
    public void resume() {
        menu.music.play();

    }


    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.dispose();
        Buy.dispose();
        Back.dispose();
        batch.dispose();
        textureAtlas.dispose();
        skin.dispose();
        stage.dispose();
        font.dispose();
        font1.dispose();

    }

    class StageForButton extends Stage {
        Button btn;
        int x;
        int y;
        int width;
        int height;

        StageForButton(Button.ButtonStyle btnstyle, int x, int y, int width, int height) {
            this.height = height;
            this.width = width;
            this.y = y;
            this.x = x;
            btn = new Button(btnstyle);
            btn.setBounds(x, y, width, height);


            addActor(btn);
        }
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