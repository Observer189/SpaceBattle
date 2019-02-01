package com.mygdx.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mygdx.game.ServModels.OldServPlayer;
import com.mygdx.game.model.Map;
import com.mygdx.game.model.OldPlayer;
import com.mygdx.game.model.Ships.Axe;
import com.mygdx.game.model.Ships.Dakkar;
import com.mygdx.game.requests.servApi;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.Confirmation;
import com.mygdx.game.utils.StarGen;
import com.mygdx.game.utils.TextManager;
import com.mygdx.game.utils.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginView implements Screen {
    public static Music music;
    Game game;
    SpriteBatch batch;
    OldPlayer oldPlayer;
    private BitmapFont font, font1;
    TextManager textManager;
    private TextField.TextFieldStyle txtStyle;
    private TextField textFieldLog, textFieldPass, textFieldConfirm;
    Stage stage;
    private Boolean KBisActive = false;
    static Map textrure;
    private TextButton.TextButtonStyle btnstyle, btnstyle1;
    private TextButton SignInBtn, SignUpBtn;
    private Toast toast, alreadyExist, NoConnection;
    private Boolean MakeToast = false;
    private Boolean MakeToastExist = false;
    private Boolean MakeToastConnetion = false;
    OrthographicCamera camera = new OrthographicCamera();
    private Boolean ShowConfirm = false;
    Boolean Saved = false;
    private Array<TextureAtlas.AtlasRegion> array;
    public static StarGen star;
    private Boolean isRegistration = false;
    private int createResult;
    private OldServPlayer signPlayer;
    private servApi request;
    private final String baseURL = "https://star-project-serv.herokuapp.com/";
    private Confirmation confirmation;
    TextureAtlas textureAtlas;

    public LoginView(SpriteBatch batch, Game game) {
        this.batch = batch;
        this.game = game;


    }

    @Override
    public void show() {
        Assets.load();
        Gdx.input.setCatchBackKey(true);

        music = Gdx.audio.newMusic(Gdx.files.internal("MenuMusic.mp3"));
        music.setLooping(true);
        music.play();
        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("TexturePack.atlas"));
        textrure = Map.generateMap(batch, textureAtlas);
        textrure.setHeight(Gdx.graphics.getHeight());
        textrure.setWidth(Gdx.graphics.getWidth());

        stage = new Stage();
        Skin skin = new Skin();
        skin.addRegions(textureAtlas);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        request = retrofit.create(servApi.class);
        createResult = -1;

        textManager = new TextManager(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        array = new Array<TextureAtlas.AtlasRegion>();
        for (int i = 0; i < 17; i++)
            array.add(textureAtlas.findRegion("Explosion" + (i + 1)));


        font = textManager.fontInitialize(Color.BLACK, 1f);
        font1 = textManager.fontInitialize(Color.WHITE, 1f);
        confirmation = new Confirmation(font1, batch);
        Toast.ToastFactory toastFactory = new Toast.ToastFactory.Builder()
                .font(font1)
                .build();
        toast = toastFactory.create("Can't be empty/more than 3 letter", Toast.Length.LONG);
        alreadyExist = toastFactory.create("This name is alredy taken", Toast.Length.LONG);
        NoConnection = toastFactory.create("Check your connection!", Toast.Length.LONG);

        txtStyle = new TextField.TextFieldStyle();
        txtStyle.font = font;
        txtStyle.fontColor = Color.BLACK;
        txtStyle.background = skin.getDrawable("FrameInput");
        txtStyle.cursor = skin.getDrawable("GreenLaserAmmo");
        txtStyle.background.setLeftWidth(40);
        txtStyle.background.setRightWidth(45);
        txtStyle.background.setBottomHeight(5);


        textFieldLog = new TextField(Assets.preferences.getString("Log"), txtStyle);
        textFieldLog.setMessageText("Your log-in");
        textFieldLog.setSize(Gdx.graphics.getWidth() / 2, 100);
        textFieldLog.setPosition(Gdx.graphics.getWidth() / 2 - textFieldLog.getWidth() / 2, 540);

        stage.addActor(textFieldLog);
        textFieldLog.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                KBisActive = true;
                textFieldLog.getOnscreenKeyboard().show(true);
            }
        });

        textFieldPass = new TextField(Assets.preferences.getString("Pass"), txtStyle);
        textFieldPass.setMessageText("Your password");
        textFieldPass.setSize(Gdx.graphics.getWidth() / 2, 100);
        textFieldPass.setPosition(Gdx.graphics.getWidth() / 2 - textFieldLog.getWidth() / 2, 420);
        textFieldPass.setPasswordMode(true);
        textFieldPass.setPasswordCharacter('*');

        textFieldPass.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                KBisActive = true;
                textFieldPass.getOnscreenKeyboard().show(true);

            }
        });
        stage.addActor(textFieldPass);
        textFieldConfirm = new TextField("", txtStyle);
        textFieldConfirm.setMessageText("Confirm your pass");

        textFieldConfirm.setSize(textFieldPass.getWidth(), textFieldPass.getHeight());
        textFieldConfirm.setPosition(textFieldPass.getX(), textFieldPass.getY() - textFieldConfirm.getHeight() - 20);
        textFieldConfirm.setPasswordMode(true);
        textFieldConfirm.setPasswordCharacter('*');

        textFieldConfirm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                KBisActive = true;
                textFieldConfirm.getOnscreenKeyboard().show(true);

            }
        });

        btnstyle = new TextButton.TextButtonStyle();
        btnstyle.font = font;
        btnstyle.up = skin.getDrawable("FrameInput");

        SignInBtn = new TextButton("Sign in", btnstyle);
        SignInBtn.setPosition(textFieldLog.getX() - 20, 200);
        SignInBtn.setSize(300, 100);
        SignInBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (SignInBtn.getText().toString().equals("Sign in")) {
                    if (textFieldLog.getText() != null && !textFieldLog.getText().contains(" ") && !(textFieldLog.getText().length() < 3) &&
                            textFieldPass.getText() != null && !textFieldPass.getText().contains(" ") && !(textFieldPass.getText().length() < 3)
                    ) {
                        save();

                        signPlayer = getOldPlayer();
                        if ((signPlayer != null) && (signPlayer.getPassword().equals(textFieldPass.getText()))) {
                            oldPlayer = new OldPlayer(signPlayer);

                            game.setScreen(new MainMenu(batch, game, oldPlayer));
                        } else {
                            System.out.println("Вы ввели неверное имя пользователя или пароль!");
                        }
                    } else {
                        MakeToast = true;
                    }

                }
                if (SignInBtn.getText().toString().equals("Cancel")) {
                    SignInBtn.setText("Sign in");
                    isRegistration = false;
                    textFieldConfirm.setVisible(false);

                }
            }
        });
        stage.addActor(SignInBtn);
        btnstyle1 = new TextButton.TextButtonStyle();
        btnstyle1.font = font;
        btnstyle1.up = skin.getDrawable("FrameInput");

        SignUpBtn = new TextButton("Sign up", btnstyle1);
        SignUpBtn.setSize(300, 100);
        SignUpBtn.setPosition(textFieldLog.getX() + textFieldLog.getWidth() - SignUpBtn.getWidth(), 200);
        SignUpBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isRegistration) {
                    ShowConfirm = true;
                    SignInBtn.setText("Cancel");
                    isRegistration = true;
                } else {
                    if (textFieldLog.getText() != null && !textFieldLog.getText().contains(" ") && !(textFieldLog.getText().length() < 3) &&
                            textFieldPass.getText() != null && !textFieldPass.getText().contains(" ") && !(textFieldPass.getText().length() < 3) &&
                            textFieldConfirm.getText() != null && !textFieldConfirm.getText().contains(" ") && !(textFieldConfirm.getText().length() < 3) &&
                            textFieldConfirm.getText().toString().equals(textFieldPass.getText().toString())
                    ) {
                        save();
                        createResult = createPlayer();
                        if (createResult == 1) {
                            oldPlayer = new OldPlayer(textFieldLog.getText(), new Dakkar(new TextureAtlas(Gdx.files.internal("TexturePack.atlas")), 0, 0));
                            game.setScreen(new MainMenu(batch, game, oldPlayer));
                        } else if (createResult == 0) {
                            System.out.println("Игрок с таким именем уже существует");
                            MakeToastExist = true;
                        } else {
                            System.out.println("Не удалось подключится к серверу");
                            MakeToastConnetion = true;
                        }
                        createResult = -1;
                    } else {
                        MakeToast = true;
                    }


                }
                //System.out.println(" "+isRegistration);
            }
        });


        stage.addActor(SignUpBtn);


        InputMultiplexer in = new InputMultiplexer();
        in.addProcessor(confirmation.stage);
        in.addProcessor(stage);


        Gdx.input.setInputProcessor(in);
        star = new StarGen(textureAtlas, batch);

        oldPlayer = new OldPlayer("sash", new Axe(textureAtlas, 0, 0));
        game.setScreen(new MainMenu(batch, game, oldPlayer));
    }

    @Override
    public void render(float delta) {

        camera.setToOrtho(false, (float) (Gdx.graphics.getWidth() / 1.6), (float) (Gdx.graphics.getHeight() / 1.5));
        if (ShowConfirm) {
            stage.addActor(textFieldConfirm);
            textFieldConfirm.setVisible(true);
            ShowConfirm = false;
        }
        if (Gdx.input.isTouched() && KBisActive) {
            textFieldLog.getOnscreenKeyboard().show(false);
            textFieldPass.getOnscreenKeyboard().show(false);
            textFieldConfirm.getOnscreenKeyboard().show(false);

            KBisActive = false;
        }


        textrure.draw();
        star.draw();

        stage.act(delta);
        stage.draw();
        if (MakeToast) {

            toast.render(Gdx.graphics.getDeltaTime());
            if (toast.timeToLive <= 0) {
                MakeToast = false;
                toast.timeToLive = Toast.Length.LONG.duration;
                toast.opacity = 1f;
            }
        }
        if (MakeToastExist) {
            alreadyExist.render(delta);
            if (alreadyExist.timeToLive <= 0) {
                MakeToastExist = false;
                alreadyExist.timeToLive = Toast.Length.LONG.duration;
                alreadyExist.opacity = 1f;
            }

        }
        if (MakeToastConnetion) {
            NoConnection.render(delta);
            if (NoConnection.timeToLive <= 0) {
                MakeToastConnetion = false;
                NoConnection.timeToLive = Toast.Length.LONG.duration;
                NoConnection.opacity = 1f;
            }

        }
        confirmation.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            confirmation.setActive(true);

        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        music.pause();


    }


    @Override
    public void resume() {
        music.play();

    }

    private void save() {
        //  FileHandle fh=new FileHandle("text");

        /*FileHandle fileHandle = Gdx.files.internal("data/LogPass.xml");
        fileHandle.writeString("",true);

        String str= textFieldLog.getText()+" "+textFieldPass.getText();
        fileHandle.writeString(str,true);*/

        Assets.preferences.putString("Log", textFieldLog.getText());
        Assets.preferences.putString("Pass", textFieldPass.getText());
        Assets.preferences.flush();

    }


    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private int createPlayer() {
        int result = -1;
        Call<Integer> call = request.createPlayer(textFieldLog.getText(), textFieldPass.getText(), 1000);

        try {

            result = call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private OldServPlayer getOldPlayer() {

        OldServPlayer servPlayer = new OldServPlayer();
        Call<OldServPlayer> call = request.getPlayer(textFieldLog.getText());
        try {
            servPlayer.setServPlayer(call.execute().body());
            return servPlayer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
