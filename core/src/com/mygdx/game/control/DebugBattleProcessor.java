package com.mygdx.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.model.PhysicShip;
import com.mygdx.game.utils.ButtonForProcessor;
import com.mygdx.game.utils.GasRegulator;
import com.mygdx.game.utils.Helm;
import com.mygdx.game.view.Battle;
import com.mygdx.game.view.DebugBattle;

import static com.mygdx.game.view.Battle.heightCamera;
import static com.mygdx.game.view.Battle.widthCamera;

/**
 * Created by Sash on 15.06.2018.
 */

public class DebugBattleProcessor implements InputProcessor {
    private GasRegulator gasRegulator;
    private PhysicShip ship;
    private Helm helm;
    private ButtonForProcessor fireButton;

    private int gasPointer;
    private int turnPointer;
    private int helmPointer;
    private int firePointer;
    private float convX = Gdx.graphics.getWidth() / DebugBattle.widthCamera;
    private float convY = Gdx.graphics.getHeight() / DebugBattle.heightCamera;
    private float widthCamera = DebugBattle.widthCamera;
    private float heightCamera = DebugBattle.heightCamera;
    private float touchX;
    private float touchY;

    public DebugBattleProcessor(GasRegulator gasRegulator, Helm helm, ButtonForProcessor fireButton, PhysicShip ship) {
        this.gasRegulator = gasRegulator;
        this.helm = helm;
        this.fireButton = fireButton;
        this.ship = ship;
        gasPointer = -1;
        helmPointer = -1;

    }

    @Override
    public boolean keyDown(int keycode) {

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchX = screenX / convX + (DebugBattle.camX - DebugBattle.widthCamera / 2);
        touchY = heightCamera - screenY / convY + (DebugBattle.camY - heightCamera / 2);
        //System.out.println(screenX+" "+screenY);
        if ((touchX >= gasRegulator.getX()) && (touchX <= gasRegulator.getX() + gasRegulator.getWidth())) {
            if ((touchY >= gasRegulator.getY()) && (touchY <= gasRegulator.getY() + gasRegulator.getHeight())) {
                gasRegulator.setPhysicMovement(-1, ship);
                if (touchY <= gasRegulator.getY() + gasRegulator.getHeight() / 3) {
                    gasRegulator.setPhysicMovement(-1, ship);
                    gasPointer = pointer;
                } else if (touchY <= gasRegulator.getY() + gasRegulator.getHeight() / 3 * 2) {
                    gasRegulator.setPhysicMovement(0, ship);
                    gasPointer = pointer;
                } else {
                    gasRegulator.setPhysicMovement(1, ship);
                    gasPointer = pointer;
                }
            }
        }
        /*if((touchX>=DebugBattle.camX+DebugBattle.widthCamera/5)&&((touchX<=DebugBattle.camX+DebugBattle.widthCamera/5+widthCamera/11)))//1-я кнопка x
        {

            if((touchY>=DebugBattle.camY-heightCamera/3)&&(touchY<=DebugBattle.camY-heightCamera/3+heightCamera/11))//1-я кнопка y
            {
                ship.setRotationDirection(-1);
                //System.out.println("Click");
                turnPointer=pointer;
            }

        }
        if((touchX>=DebugBattle.camX+DebugBattle.widthCamera/5+widthCamera/9)&&((touchX<=DebugBattle.camX+DebugBattle.widthCamera/5+widthCamera/11+widthCamera/9)))//1-я кнопка x
        {

            if((touchY>=DebugBattle.camY-heightCamera/3)&&(touchY<=DebugBattle.camY-heightCamera/3+heightCamera/11))//1-я кнопка y
            {
                ship.setRotationDirection(1);
                //System.out.println("Click");
                turnPointer=pointer;
            }

        }*/
        if (((touchX >= helm.getX()) && (touchX <= helm.getX() + helm.getWidth()) && ((touchY >= helm.getY()) && (touchY <= helm.getY() + helm.getHeight())))) {
            helm.navigate(touchX, touchY);
            helmPointer = pointer;
        }
        if (((touchX >= fireButton.getX()) && (touchX <= fireButton.getX() + fireButton.getWidth()) && ((touchY >= fireButton.getY()) && (touchY <= fireButton.getY() + fireButton.getHeight())))) {
            ship.setShooting(true);
            firePointer = pointer;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer == gasPointer) {
            gasPointer = -1;
        }
        if (pointer == turnPointer) {
            ship.setRotationDirection(0);
            turnPointer = 0;
        }
        if (pointer == helmPointer) {
            helmPointer = -1;
        }
        if (pointer == firePointer) {
            firePointer = -1;
            ship.setShooting(false);
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touchX = screenX / convX + (DebugBattle.camX - DebugBattle.widthCamera / 2);
        touchY = heightCamera - screenY / convY + (DebugBattle.camY - heightCamera / 2);
        if (pointer == gasPointer) {
            if (touchY <= gasRegulator.getY() + gasRegulator.getHeight() / 3) {
                gasRegulator.setPhysicMovement(-1, ship);
                gasPointer = pointer;
            } else if (touchY <= gasRegulator.getY() + gasRegulator.getHeight() / 3 * 2) {
                gasRegulator.setPhysicMovement(0, ship);
                gasPointer = pointer;
            } else {
                gasRegulator.setPhysicMovement(1, ship);
                gasPointer = pointer;
            }

        }
        if (pointer == helmPointer) {
            //if(((touchX>=helm.getX())&&(touchX<=helm.getX()+helm.getWidth())&&((touchY>=helm.getY())&&(touchY<=helm.getY()+helm.getHeight()))))
            //{
            helm.navigate(touchX, touchY);

            //}
        }
        if ((pointer == firePointer) && ((touchX >= fireButton.getX()) && (touchX <= fireButton.getX() + fireButton.getWidth()) && ((touchY >= fireButton.getY()) && (touchY <= fireButton.getY() + fireButton.getHeight())))) {
            ship.shot();
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
