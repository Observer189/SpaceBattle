package com.mygdx.game.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.ServModels.ServFixingPoint;
import com.mygdx.game.ServModels.ServShip;
import com.mygdx.game.view.Battle;

/**
 * Created by Sash on 01.05.2018.
 */

public class Ship extends GameObject {

    int cost;
    public float realw=100;
    public float realh=100;

    private boolean isAlive;
    private float maxHp;
    private float currentHp;
    private float speedX;
    private float speedY;
    private float velocity;
    private float maxSpeed;
    private float rotationSpeed;
    private int rotationDirection;//-1-влево 1-вправо 0-без вращения
    private int movementPosition;

    private int appliedDamage;//нанесенный урон
    private Integer currentExplosionFrame;
    private int explosionCounter;
    TextureRegion explosionRegion;

    private String name;

    Vector2 movementVector;
    FixingPoint[] fixingPoints;

    public Ship(TextureRegion textureRegion, float x, float y, float width, float height, String name,
                int cost, float maxHp, float velocity, float maxSpeed,float rotationSpeed, FixingPoint[] fixingPoints) {
        super(textureRegion, x, y, width, height);

        this.cost = cost;
        this.maxHp = maxHp;
        currentHp = maxHp;
        this.velocity = velocity;
        this.maxSpeed = maxSpeed;
        this.name = name;
        this.rotationSpeed=rotationSpeed;

        isAlive=true;

        speedX=0;
        speedY=0;
        currentExplosionFrame=0;
        explosionCounter=7;
        movementVector = new Vector2(0, 0);
        movementPosition=0;
        this.fixingPoints = fixingPoints;
        appliedDamage=0;
        rotationDirection=0;
    }



    public FixingPoint[] getFixingPoints() {
        return fixingPoints;
    }

    public void setFixingPoints(FixingPoint[] fixingPoints) {
        this.fixingPoints = fixingPoints;
    }


    public void draw(SpriteBatch batch,TextureAtlas textureAtlas) {
        if (currentHp>0) {
            for (int i = 0; i < fixingPoints.length; i++) {
                fixingPoints[i].draw(batch);
            }

            super.draw(batch);

        } else
        {
            destructShip(batch,textureAtlas);
        }


    }
    public void destructShip(SpriteBatch batch,TextureAtlas textureAtlas)
    {
        if(currentExplosionFrame<=17) {
            if (explosionCounter == 7) {
                currentExplosionFrame++;
                explosionRegion = textureAtlas.findRegion("Explosion" + currentExplosionFrame.toString());
                explosionCounter = 0;
            }
            batch.begin();
            batch.draw(explosionRegion, getX(), getY(), getWidth()+getHeight(),getWidth()+getHeight());
            batch.end();
            explosionCounter++;
        }
        else
        {
            isAlive=false;
        }
    }



    public void shot()
    {
        for(int i=0;i<fixingPoints.length;i++)
        {
            fixingPoints[i].shot();
        }
    }

    public void move(Ship enemyShip, Map map) {
        if(currentHp>0) {
            if(movementPosition==0)
                movementVector.set(0,0);
            else if(movementPosition==1)
            {
                movementVector.set((float)(-Math.sin(Math.toRadians(getRotation()))),(float)(Math.cos(Math.toRadians(getRotation()))));
            }
            else if(movementPosition==-1)
            {
                movementVector.set((float)(Math.sin(Math.toRadians(getRotation())))*0.15f,(float)(-Math.cos(Math.toRadians(getRotation())))*0.15f);
            }

            speedX = speedX + velocity * movementVector.x;
            speedY = speedY + velocity * movementVector.y;

            if (speedX > maxSpeed) speedX = maxSpeed;
            if (speedX < -maxSpeed) speedX = -maxSpeed;
            if (speedY > maxSpeed) speedY = maxSpeed;
            if (speedY < -maxSpeed) speedY = -maxSpeed;
            bounds.setPosition(bounds.getX() + speedX * Battle.delta, bounds.getY() + speedY * Battle.delta);




            for (int i = 0; i < fixingPoints.length; i++) {
                fixingPoints[i].update(this,enemyShip, map);
            }

            if (bounds.getX() >  map.getWidth()) {
                bounds.setPosition(0,map.getHeight()-bounds.getY());
            }
            else if (bounds.getX() <  0) {
                bounds.setPosition(map.getWidth(),map.getHeight()-bounds.getY());
            }
            if (bounds.getY() >  map.getHeight()) {
                bounds.setPosition(map.getWidth()-bounds.getX(),0);
            }
            else if (bounds.getY() <  0) {
                bounds.setPosition(map.getWidth()-bounds.getX(),map.getHeight());
            }
            if(rotationDirection==-1) bounds.rotate(rotationSpeed);
            if(rotationDirection==1) bounds.rotate(-rotationSpeed);

        }

    }
    public void setMovementVector(Vector2 movementVector) {

        this.movementVector.x = movementVector.x;

        this.movementVector.y = movementVector.y;
    }



    public void act(Ship enemyShip, Map map, Vector2 vector)
    {
        setMovementVector(vector);
        shot();
        move(enemyShip,map);
    }




    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }




    public boolean getIsAlive() {
        return isAlive;
    }
    public void setIsAlive(boolean b) {
        isAlive=b;
    }

    public float getCurrentHp() {
        return currentHp;
    }

    public int getAppliedDamage() {
        return appliedDamage;
    }

    public void setAppliedDamage(int appliedDamage) {
        this.appliedDamage = appliedDamage;
    }

    public Drawable getImg(){
        if (name=="Pulsate"){
           Skin skin=new Skin();
           skin.addRegions(new TextureAtlas(Gdx.files.internal("TexturePack.atlas")));
           return skin.getDrawable("1");
        } else {
            Skin skin=new Skin();
            skin.addRegions(new TextureAtlas(Gdx.files.internal("TexturePack.atlas")));
            return skin.getDrawable(name);

        }



    }

public void nullify()
{
    bounds.setPosition(0,0);
    setCurrentHp(getMaxHp());
    setIsAlive(true);
    setMovementVector(new Vector2(0,0));
    movementPosition=0;
    speedX=0;
    speedY=0;
    currentExplosionFrame=0;
    explosionCounter=7;
    rotationDirection=0;
}

    public float getMaxSpeed(){return  maxSpeed;}
    public float getVelocity(){return velocity;}
    public int getCost(){return cost;}
    public float getMaxHp(){return maxHp;}



    public void setCurrentHp(float currentHp) {
        this.currentHp = currentHp;
    }

    public String getName() {return name; }


    public void setRotationDirection(int rotationDirection) {
        this.rotationDirection = rotationDirection;
    }

    public int getRotationDirection() {
        return rotationDirection;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public int getFixingPointsDigit(){return fixingPoints.length;}

    public Vector2 getMovementVector() {
        return movementVector;
    }

    public void setMovementPosition(int movementPosition) {
        this.movementPosition = movementPosition;
    }

    public int getMovementPosition() {
        return movementPosition;
    }

    @Override
    public String toString() {

        return getName();
    }
    @Override
    public boolean equals(Object o) {
        return getName().equals(o.toString());
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        for(int i=0;i<fixingPoints.length;i++)
        {
            fixingPoints[i].setPosition(this);

        }


    }
    public ServShip toServ()
    {
        /*ServFixingPoint[] servFixingPoints =new ServFixingPoint[fixingPoints.length];
        for(int i=0;i<fixingPoints.length;i++)
        {
            servFixingPoints[i]=fixingPoints[i].toServ();
        }*/

        return new ServShip(getName()/*,servFixingPoints*/);
    }

    public float getRealh() {
        return realh;
    }

    public float getRealw() {
        return realw;
    }
}
