package com.mygdx.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.model.PhysicShip;
import com.mygdx.game.utils.GasRegulator;
import com.mygdx.game.view.Battle;
import com.mygdx.game.view.DebugBattle;

import static com.mygdx.game.view.Battle.heightCamera;
import static com.mygdx.game.view.Battle.widthCamera;

/**
 * Created by Sash on 15.06.2018.
 */

public class DebugBattleProcessor implements InputProcessor {
    GasRegulator gasRegulator;
    PhysicShip ship;

    int gasPointer;
    int turnPointer;
    float convX= Gdx.graphics.getWidth()/ DebugBattle.widthCamera;
    float convY=Gdx.graphics.getHeight()/ DebugBattle.heightCamera;
    float widthCamera=DebugBattle.widthCamera;
    float heightCamera=DebugBattle.heightCamera;
    float touchX;
    float touchY;
    public DebugBattleProcessor(GasRegulator gasRegulator,PhysicShip ship)
    {
        this.gasRegulator=gasRegulator;
        this.ship=ship;

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
        touchX=screenX / convX + (DebugBattle.camX - DebugBattle.widthCamera / 2);
        touchY=heightCamera - screenY / convY + (DebugBattle.camY - heightCamera / 2);
        System.out.println(screenX+" "+screenY);
        if((touchX>=gasRegulator.getX())&&(touchX<=gasRegulator.getX()+gasRegulator.getWidth()))
        {
            if((touchY>=gasRegulator.getY())&&(touchY<=gasRegulator.getY()+gasRegulator.getHeight()))
            {
                gasRegulator.setPhysicMovement(-1,ship);
                if(touchY<=gasRegulator.getY()+gasRegulator.getHeight()/3)
                {
                    gasRegulator.setPhysicMovement(-1,ship);
                    gasPointer=pointer;
                }
                else if(touchY<=gasRegulator.getY()+gasRegulator.getHeight()/3*2)
                {
                    gasRegulator.setPhysicMovement(0,ship);
                    gasPointer=pointer;
                }
                else
                {
                    gasRegulator.setPhysicMovement(1,ship);
                    gasPointer=pointer;
                }
            }
        }
        if((touchX>=DebugBattle.camX+DebugBattle.widthCamera/5)&&((touchX<=DebugBattle.camX+DebugBattle.widthCamera/5+widthCamera/11)))//1-я кнопка x
        {

            if((touchY>=DebugBattle.camY-heightCamera/3)&&(touchY<=DebugBattle.camY-heightCamera/3+heightCamera/11))//1-я кнопка y
            {
                ship.setRotationDirection(-1);
                System.out.println("Click");
                turnPointer=pointer;
            }

        }
        if((touchX>=DebugBattle.camX+DebugBattle.widthCamera/5+widthCamera/9)&&((touchX<=DebugBattle.camX+DebugBattle.widthCamera/5+widthCamera/11+widthCamera/9)))//1-я кнопка x
        {

            if((touchY>=DebugBattle.camY-heightCamera/3)&&(touchY<=DebugBattle.camY-heightCamera/3+heightCamera/11))//1-я кнопка y
            {
                ship.setRotationDirection(1);
                System.out.println("Click");
                turnPointer=pointer;
            }

        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer==gasPointer)
        {
            gasPointer=-1;
        }
        if(pointer==turnPointer) {
            ship.setRotationDirection(0);
            turnPointer=0;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touchX=screenX / convX + (DebugBattle.camX - DebugBattle.widthCamera / 2);
        touchY=heightCamera - screenY / convY + (DebugBattle.camY - heightCamera / 2);
        if(pointer==gasPointer)
        {
            if(touchY<=gasRegulator.getY()+gasRegulator.getHeight()/3)
            {
                gasRegulator.setPhysicMovement(-1,ship);
                gasPointer=pointer;
            }
            else if(touchY<=gasRegulator.getY()+gasRegulator.getHeight()/3*2)
            {
                gasRegulator.setPhysicMovement(0,ship);
                gasPointer=pointer;
            }
            else
            {
                gasRegulator.setPhysicMovement(1,ship);
                gasPointer=pointer;
            }

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
