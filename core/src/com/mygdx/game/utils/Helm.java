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
    int shipRotation;
    int spriteRotation;
    float internalArc;
    float externalArc;
    int tempShip;
    int tempSprite;
    private boolean isChanged;
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
        sprite.setRotation(ship.getRotation());
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
       isChanged=true;

    }

    public void updateShip(float angularSpeed)
    {
        //ship.getBodies()[0].setTransform(ship.getBodies()[0].getPosition().x,ship.getBodies()[0].getPosition().y,(float)Math.toRadians(sprite.getRotation()));

         //ship.getBodies()[1].setTransform(ship.getBodies()[1].getPosition().x,ship.getBodies()[1].getPosition().y,(float)Math.toRadians(sprite.getRotation()));
         shipRotation=(int) ship.getRotation();

         while (shipRotation>=360) {                 //
             shipRotation -= 360;                    //
         }                                            //Установление градусной меры корабля в рамки 0 до 360
        while (shipRotation<=0) {                     //
            shipRotation += 360;                      //
        }                                            //
         spriteRotation=(int)sprite.getRotation();   // Обрезаем значение до целого с помощью преобразование до Int
        while (spriteRotation>=360) {
            spriteRotation -= 360;
        }
        while (spriteRotation<=0) {
            spriteRotation += 360;
        }
        //shipRotation=(Math.toDegrees(ship.getRotation())>360)?(float)Math.toDegrees(ship.getRotation())-360:(float)Math.toDegrees(ship.getRotation());
        //spriteRotation=(sprite.getRotation()>360)?sprite.getRotation()-360:sprite.getRotation();
         internalArc=Math.abs(shipRotation-spriteRotation);     //Находим внешнее и внутренне растояние в градусах между
         externalArc=360-internalArc;                           // фазой корабля и штурвала
         /*if(internalArc<externalArc)
         {
             ship.setRotationDirection(1);
         }
         else if(internalArc>externalArc)
         {
             ship.setRotationDirection(-1);
         }
         else ship.setRotationDirection(0);*/
         tempShip=(shipRotation<180)?shipRotation+360:shipRotation;// Увеличиваем половину градусной окружности на 360 для того чтобы избежать проблемы при вычитании перехода через 0
         tempSprite=(spriteRotation<180)?spriteRotation+360:spriteRotation;

            if(Math.abs(tempShip-tempSprite)>angularSpeed) {
                if (internalArc < externalArc) {
                    if (shipRotation > spriteRotation) {
                        ship.setRotationDirection(-1);

                        //ship.getBodies()[0].setAngularVelocity(-2f);
                        //ship.getBodies()[1].setAngularVelocity(-2f);
                    } else if (shipRotation < spriteRotation) {
                        ship.setRotationDirection(1);

                        //ship.getBodies()[0].setAngularVelocity(2f);
                        //ship.getBodies()[1].setAngularVelocity(2f);
                    } else {
                        ship.setRotationDirection(0);

                        //ship.getBodies()[0].setAngularVelocity(0f);
                        //ship.getBodies()[1].setAngularVelocity(0f);
                        isChanged=false;
                    }

                } else {
                    if (shipRotation > spriteRotation) {
                        ship.setRotationDirection(1);

                        //ship.getBodies()[0].setAngularVelocity(2f);
                        //ship.getBodies()[1].setAngularVelocity(2f);
                    } else if (shipRotation < spriteRotation) {
                        ship.setRotationDirection(-1);

                        //ship.getBodies()[0].setAngularVelocity(-2f);
                        //ship.getBodies()[1].setAngularVelocity(-2f);
                    } else {
                        ship.setRotationDirection(0);

                        //ship.getBodies()[0].setAngularVelocity(0f);
                        //ship.getBodies()[1].setAngularVelocity(0f);
                        isChanged=false;
                    }
                }
            }
            else
            {
                ship.setRotationDirection(0);
                ship.setAngularVelocity(0f);
                ship.setAngle((float) Math.toRadians(sprite.getRotation()));
                //ship.getBodies()[0].setAngularVelocity(0f);
                //ship.getBodies()[1].setAngularVelocity(0f);
                isChanged=false;
            }
        //System.out.println(Math.abs(tempShip-tempSprite)+" "+ship.getRotationDirection()+" "+ship.getBodies()[0].getAngularVelocity());



        /*if(Math.abs(ship.getRotation()-sprite.getRotation())<360-Math.abs(ship.getRotation()-sprite.getRotation()))
        {
            ship.setRotationDirection(1);
        }
        else if(Math.abs(ship.getRotation()-sprite.getRotation())>360-Math.abs(ship.getRotation()-sprite.getRotation()))
        {
            ship.setRotationDirection(-1);
        }
        else ship.setRotationDirection(0);*/

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
