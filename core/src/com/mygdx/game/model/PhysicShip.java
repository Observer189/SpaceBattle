package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Modules.Engine;

/**
 * Created by Sash on 15.06.2018.
 */

public class PhysicShip extends PhysicObject {
    private int movementPosition;
    private float rotationSpeed;
    private float linearDamping;//не ниже 0.01
    private int rotationDirection;//-1-влево 1-вправо 0-без вращения
    private float enginePower;
    private WeaponPoint[] weapons;
    private EnginePoint[] engines;
    private EnergyPoint[] energyPoints;
    private float maxSpeed;
    private float speed;
    private int weaponNumbers;
    private int engineNumbers;
    Vector2 movementVector;

    private float energy;
    private float maxEnergy;
    private long energyConsLastTime;
    private int consumptionReload;

    private float hp;
    public PhysicShip(TextureRegion textureRegion, float x, float y, float width, float height,
                      float density,int bodyNumber,int weaponNumbers,int engineNumbers,int energyNumbers,float linearDamping,float rotationSpeed,
                      float[][] shape,float hp, World world) {
        super(textureRegion, x, y, width, height,density,bodyNumber,shape, world);
        this.rotationSpeed=rotationSpeed;
        movementPosition=0;
        enginePower=50;
        movementVector = new Vector2(0, 0);
        speed=0;
        this.weaponNumbers=weaponNumbers;
        this.engineNumbers=engineNumbers;
        weapons=new WeaponPoint[weaponNumbers];
        engines= new EnginePoint[engineNumbers];
        energyPoints=new EnergyPoint[energyNumbers];
        rotationDirection=0;

        this.hp=hp;

        this.linearDamping=linearDamping;
        for (Body i:bodies)
        {
            i.setLinearDamping(this.linearDamping);
        }

    }
    public void create()
    {
        rotationSpeed=findRotationSpeed();
        maxEnergy=0;
        for(int i=0;i<energyPoints.length;i++)
        {
            maxEnergy+=energyPoints[i].getEnergyModule().getEnergyStorage();
        }

        energy=maxEnergy;
        energyConsLastTime=System.currentTimeMillis();
        consumptionReload=100;
    }

    public void move()
    {
        maxSpeed=findMaxSpeed();
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
        shot();
        if(energy>0) {
            for (int i = 0; i < bodies.length; i++) {
                if (!(speed >= maxSpeed)) {
                    //bodies[i].applyForceToCenter(new Vector2(enginePower * movementVector.x, enginePower * movementVector.y), true);
                    engines[i].move(movementVector);
                }

                speed = bodies[0].getLinearVelocity().len();

                if (rotationDirection == -1)
                    //bodies[i].setTransform(bodies[i].getPosition().x, bodies[i].getPosition().y, bodies[i].getAngle() + rotationSpeed);
                    bodies[i].setAngularVelocity(-rotationSpeed);
                else if (rotationDirection == 1)
                    //bodies[i].setTransform(bodies[i].getPosition().x, bodies[i].getPosition().y, bodies[i].getAngle() - rotationSpeed);
                    bodies[i].setAngularVelocity(rotationSpeed);
                else if (rotationDirection == 0) {
                    bodies[i].setAngularVelocity(0);
                }


            }
        }
            speed = bodies[0].getLinearVelocity().len();
       if(System.currentTimeMillis()-energyConsLastTime>consumptionReload) {
           for (int i = 0; i < engines.length; i++) {
               energy -= engines[i].getEngine().getEnergyConsumption();

           }
           for (int i = 0; i < energyPoints.length; i++) {
               energy += energyPoints[i].getEnergyModule().getEnergyGeneration();

           }
           energyConsLastTime=System.currentTimeMillis();
       }
        if(energy<0)
            energy=0;
       else if(energy>maxEnergy)
           energy=maxEnergy;

        //System.out.println(getBodies()[0].getPosition().y+" "+getBodies()[1].getPosition().y);
    }
    public void shot()
    {
        for (WeaponPoint i:weapons) {
            i.shot(getHeight()/2-i.getLocalAnchor().y);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        for (int i=0;i<engines.length;i++) {
            engines[i].draw(batch);
        }
        super.draw(batch);
        for (int i=0;i<weapons.length;i++) {
            weapons[i].draw(batch);
        }
        for (int i=0;i<energyPoints.length;i++) {
            if(energyPoints[i]!=null)
            energyPoints[i].draw(batch);
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

    public EnginePoint[] getEngines() {
        return engines;
    }
    public float findMaxSpeed()
    {
        float amountSpeed=0;
        int engineNumber=0;
        for(int i=0;i<engines.length;i++)
        {

               amountSpeed+=engines[i].getEngine().getMaxSpeed();
               engineNumber++;

        }
        return amountSpeed/engineNumber;
    }
    public float findRotationSpeed()
    {
        float rotationPower=0;
        for(int i=0;i<engines.length;i++)
        {
            rotationPower+=engines[i].getAngularPower();
        }
        return rotationPower/getMass();
    }
    public void setAngle(float angle)
    {
        for (Body i:bodies)
        {
            i.setTransform(i.getPosition().x,i.getPosition().y,angle);
        }
    }
    public void setAngularVelocity(float angularVelocity)
    {
        for (Body i:bodies)
        {
            i.setAngularVelocity(angularVelocity);
        }
    }

    public int getWeaponNumbers() {
        return weaponNumbers;
    }

    public int getEngineNumbers() {
        return engineNumbers;
    }

    public EnergyPoint[] getEnergyPoints() {
        return energyPoints;
    }
    public float getMass()
    {
        float mass=0;
        for(int i=0;i<bodies.length;i++)
        {
            mass+=bodies[i].getMass();
        }
        return mass;
    }

    public float getMaxEnergy() {
        return maxEnergy;
    }

    public float getEnergy() {
        return energy;
    }
}
