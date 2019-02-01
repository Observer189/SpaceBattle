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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.model.OldPlayer;
import com.mygdx.game.utils.Confirmation;
import com.mygdx.game.utils.LogListener;
import com.mygdx.game.utils.PassListener;
import com.mygdx.game.utils.TextManager;

/**
 * Created by Sash on 24.04.2018.
 */

public class MainMenu implements Screen {

    public float y = 20 / (float) (Gdx.graphics.getWidth() / Gdx.graphics.getHeight());
    private StageForButton sfplaybutton, sfshop, sfangar;
    SpriteBatch batch;
    Game game;
    public OldPlayer oldPlayer;
    public TextureAtlas textureAtlas;
    TextManager textManager;
    private Button.ButtonStyle p_button, sh_button, ang_button;
    public Screen CTB;//ConnectToBattle
    private Screen PreShop, angar;
    BitmapFont font;
    Input.TextInputListener LogIn;
    private int deltaX = Gdx.graphics.getWidth() / 4;
    public LogListener Log;
    PassListener Pass;
    private int ForLogCounter = 0;
    InputMultiplexer in;
    String first;
    Music music;
    OrthographicCamera camera = new OrthographicCamera();
    private AngarView angar2;
    private Confirmation confirmation;

    public MainMenu(SpriteBatch batch, Game game, OldPlayer oldPlayer) {
        this.batch = batch;
        this.game = game;
        this.oldPlayer = oldPlayer;
    }


    @Override
    public void show() {
        this.music = LoginView.music;
        Gdx.input.setCatchBackKey(true);
        textureAtlas = new TextureAtlas(Gdx.files.internal("TexturePack.atlas"));


        //PreShop=new PreShop(game,batch,textureAtlas);
        batch = new SpriteBatch();
        CTB = new ConnectToBattle(batch, game, textureAtlas, oldPlayer, this);

        PreShop = new PreShop(game, batch, textureAtlas, this, oldPlayer);

        angar = new Angar(game, batch, this, oldPlayer);
        angar2 = new AngarView(game, batch, this, oldPlayer);
        textManager = new TextManager(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font = textManager.fontInitialize(Color.WHITE, 1f);
        //game.setScreen(battle);
        Skin skin = new Skin();
        skin.addRegions(textureAtlas);
        confirmation = new Confirmation(font, batch);

        p_button = new Button.ButtonStyle();
        p_button.up = skin.getDrawable("Start-up");
        p_button.down = skin.getDrawable("Start-down");


        sfplaybutton = new StageForButton(p_button, deltaX - 100, (int) (Gdx.graphics.getHeight() / 4.8));
        sfplaybutton.btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ForLogCounter++;

                music.stop();
                //game.setScreen(CTB);
                game.setScreen(new PreparingToBattle(batch, game, textureAtlas));
            }
        });

        //кнопка магаз
        sh_button = new Button.ButtonStyle();
        sh_button.up = skin.getDrawable("Shop-up");
        sh_button.down = skin.getDrawable("Shop-down");
        sfshop = new StageForButton(sh_button, (int) (deltaX * 2 - 100), (int) (Gdx.graphics.getHeight() / 4.8));
        sfshop.btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ForLogCounter++;
                DisableBtn(true);
                game.setScreen(PreShop);


            }
        });

        //кнопка ангар

        ang_button = new Button.ButtonStyle();
        ang_button.up = skin.getDrawable("Angar-up");
        ang_button.down = skin.getDrawable("Angar-down");
        sfangar = new StageForButton(ang_button, (int) (deltaX * 3 - 100), (int) (Gdx.graphics.getHeight() / 4.8));
        sfangar.btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ForLogCounter++;
                DisableBtn(true);
                game.setScreen(angar2);


            }
        });


        in = new InputMultiplexer();

        in.addProcessor(sfplaybutton);
        in.addProcessor(sfshop);
        in.addProcessor(sfangar);
        in.addProcessor(confirmation.stage);

        Gdx.input.setInputProcessor(in);

    }


    @Override
    public void render(float delta) {


        camera.setToOrtho(false, (float) (Gdx.graphics.getWidth() / 1.6), (float) (Gdx.graphics.getHeight() / 1.5));
        LoginView.textrure.draw();
        LoginView.star.draw();


        textManager.displayMessage(batch, font, "Welcome to Space Battle!", (int) (Gdx.graphics.getWidth() / 3.5), (int) (Gdx.graphics.getHeight() / 1.3 + 20));
        //textManager.displayMessage(batch,"x= "+Gdx.graphics.getWidth()+" y= "+Gdx.graphics.getHeight() ,Color.BLACK,50, (int) (Gdx.graphics.getWidth()/3.5), (int) (Gdx.graphics.getHeight()/1.3+90));


        //batch.draw(img,100,100);

        sfplaybutton.act(delta);
        sfplaybutton.draw();


        sfshop.act(delta);
        sfshop.draw();

        sfangar.act(delta);
        sfangar.draw();
        confirmation.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {

            confirmation.setActive(true);
        }
        batch.begin();
        batch.end();


    }


    @Override
    public void resize(int width, int height) {
        System.out.println("Resumed");
        DisableBtn(false);

    }

    @Override
    public void pause() {
        music.pause();


    }

    @Override
    public void resume() {
        music.play();

    }

    @Override
    public void hide() {
        System.out.println("Hided");
        DisableBtn(true);


    }

    private void DisableBtn(Boolean b) {
        if (b) {
            in.removeProcessor(sfangar);
            in.removeProcessor(sfshop);
            in.removeProcessor(sfplaybutton);
        } else {
            in.addProcessor(sfplaybutton);
            in.addProcessor(sfshop);
            in.addProcessor(sfangar);
        }

    }

    @Override
    public void dispose() {
//        batch.dispose();
        sfplaybutton.dispose();
        sfangar.dispose();
        sfshop.dispose();
        textureAtlas.dispose();
        game.dispose();


    }

    public void setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

    class StageForButton extends Stage {
        Button btn;

        StageForButton(Button.ButtonStyle btnstyle, int x, int y) {

            btn = new Button(btnstyle);
            btn.setBounds(x, y, 200, 200);


            addActor(btn);
        }
    }


}
