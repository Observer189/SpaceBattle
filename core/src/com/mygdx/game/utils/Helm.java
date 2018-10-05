package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.model.PhysicShip;

/**
 * Created by Sash on 05.10.2018.
 */

public class Helm {
    private Sprite sprite;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private PhysicShip ship;
    private float offsetX;
    private float offsetY;
    private float widthCoeff;
    private float heightCoeff;
    private double pointAngle;//угол между вводимой точкой и центром штурвала
    float shipRotation;
    float spriteRotation;
    float internalArc;
    float externalArc;
    public Helm(TextureAtlas textureAtlas,SpriteBatch batch, OrthographicCamera camera, float offsetX, float offsetY, float widthCoeff, float heightCoeff,PhysicShip ship)
    {
        this.batch=batch;
        this.camera=camera;
        this.offsetX=offsetX;
        this.offsetY=offsetY;
        this.widthCoeff=widthCoeff;
        this.heightCoeff=heightCoeff;
        sprite=new Sprite(textureAtlas.findRegion("Helm"));
        sprite.setPosition(camera.position.x+offsetX,camera.position.y+offsetY);
        sprite.setSize(camera.viewportWidth*widthCoeff,camera.viewportHeight*heightCoeff);
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);
        this.ship=ship;
    }
    public void draw()
    {
        sprite.setPosition(camera.position.x+offsetX,camera.position.y+offsetY);
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }
    public void navigate(float touchX,float touchY)
    {
       pointAngle=Math.toDegrees(Math.atan2(touchX-(sprite.getX()+sprite.getWidth()/2),touchY-(sprite.getY()+sprite.getHeight()/2)));
       sprite.setRotation(-(float)pointAngle);


    }

    public void updateShip()
    {
        //ship.getBodies()[0].setTransform(ship.getBodies()[0].getPosition().x,ship.getBodies()[0].getPosition().y,(float)Math.toRadians(sprite.getRotation()));

         //ship.getBodies()[1].setTransform(ship.getBodies()[1].getPosition().x,ship.getBodies()[1].getPosition().y,(float)Math.toRadians(sprite.getRotation()));
         shipRotation=(Math.toDegrees(ship.getRotation())>0)?(float)Math.toDegrees(ship.getRotation()):360+(float)Math.toDegrees(ship.getRotation());
         spriteRotation=(sprite.getRotation()>0)?sprite.getRotation():360+sprite.getRotation();
        //shipRotation=(Math.toDegrees(ship.getRotation())>360)?(float)Math.toDegrees(ship.getRotation())-360:(float)Math.toDegrees(ship.getRotation());
        //spriteRotation=(sprite.getRotation()>360)?sprite.getRotation()-360:sprite.getRotation();
         internalArc=Math.abs(shipRotation-spriteRotation);
         externalArc=360-internalArc;
         /*if(internalArc<externalArc)
         {
             ship.setRotationDirection(1);
         }
         else if(internalArc>externalArc)
         {
             ship.setRotationDirection(-1);
         }
         else ship.setRotationDirection(0);*/

             if (Math.abs(Math.toDegrees(ship.getRotation())) < Math.abs(sprite.getRotation())) {
                 ship.setRotationDirection(1);
             } else if (Math.abs(Math.toDegrees(ship.getRotation())) > Math.abs(sprite.getRotation())) {
                 ship.setRotationDirection(-1);
             } else ship.setRotationDirection(0);


        /*if(Math.abs(ship.getRotation()-sprite.getRotation())<360-Math.abs(ship.getRotation()-sprite.getRotation()))
        {
            ship.setRotationDirection(1);
        }
        else if(Math.abs(ship.getRotation()-sprite.getRotation())>360-Math.abs(ship.getRotation()-sprite.getRotation()))
        {
            ship.setRotationDirection(-1);
        }
        else ship.setRotationDirection(0);*/
        System.out.println(Math.toDegrees(ship.getRotation())+" "+sprite.getRotation());
    }
    public float getX()
    {
        return sprite.getX();
    }
    public float getY()
    {
        return sprite.getY();
    }
    public float getWidth()
    {
        return sprite.getWidth();
    }
    public float getHeight()
    {
        return sprite.getHeight();
    }
}
