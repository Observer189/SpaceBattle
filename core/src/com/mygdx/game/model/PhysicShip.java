package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Sash on 15.06.2018.
 */

public class PhysicShip extends PhysicObject {
    private int movementPosition;
    private float rotationSpeed;
    private int rotationDirection;//-1-влево 1-вправо 0-без вращения
    private float enginePower;
    private WeaponPoint[] weapons;
    private float maxSpeed=10;
    private float speed;
    private int weaponNumbers;
    Vector2 movementVector;
    public PhysicShip(TextureRegion textureRegion, float x, float y, float width, float height,float density,int bodyNumber,int weaponNumbers,float rotationSpeed,float[][] shape, World world) {
        super(textureRegion, x, y, width, height,density,bodyNumber,shape, world);
        this.rotationSpeed=rotationSpeed;
        movementPosition=0;
        enginePower=50;
        movementVector = new Vector2(0, 0);
        speed=0;
        this.weaponNumbers=weaponNumbers;
        weapons=new WeaponPoint[weaponNumbers];
    }

    public void move()
    {
        if(movementPosition==0)
            movementVector.set(0,0);
        else if(movementPosition==1)
        {
            movementVector.set((float)(-Math.sin(getRotation())),(float)(Math.cos(getRotation())));
        }
        else if(movementPosition==-1)
        {
            movementVector.set((float)(Math.sin(getRotation()))*0.15f,(float)(-Math.cos(getRotation()))*0.15f);
        }
        for(int i=0;i<bodies.length;i++) {
            bodies[i].applyForceToCenter(new Vector2(enginePower * movementVector.x, enginePower * movementVector.y), true);

            if (rotationDirection == -1)
                //bodies[i].setTransform(bodies[i].getPosition().x, bodies[i].getPosition().y, bodies[i].getAngle() + rotationSpeed);
                bodies[i].applyAngularImpulse(0.1f,true);
            if (rotationDirection == 1)
               //bodies[i].setTransform(bodies[i].getPosition().x, bodies[i].getPosition().y, bodies[i].getAngle() - rotationSpeed);
                bodies[i].applyAngularImpulse(-0.1f,true);
        }
            speed = bodies[0].getLinearVelocity().len();

        //enginePower=100;
         /*if(speed>=maxSpeed)
         {
             enginePower=0;
         }*/


    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for (int i=0;i<weapons.length;i++) {
            weapons[i].draw(batch);
        }
    }

    public void setMovementPosition(int movementPosition) {
        this.movementPosition = movementPosition;
    }

    public int getMovementPosition() {
        return movementPosition;
    }

    public int getRotationDirection() {
        return rotationDirection;
    }

    public void setRotationDirection(int rotationDirection) {
        this.rotationDirection = rotationDirection;
    }

    public WeaponPoint[] getWeapons() {
        return weapons;
    }
}
