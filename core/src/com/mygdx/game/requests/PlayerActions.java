package com.mygdx.game.requests;

/**
 * Created by Sash on 21.12.2018.
 */

public class PlayerActions {
    private int battleID;
    private int shipID;
    private int movementPosition;
    private float targetRotation;
    private long time;//время создания запроса
    public PlayerActions()
    {
        time=System.currentTimeMillis();
    }

    public void setBattleID(int battleID) {
        this.battleID = battleID;
    }

    public int getBattleID() {
        return battleID;
    }

    public float getTargetRotation() {
        return targetRotation;
    }

    public void setTargetRotation(float targetRotation) {
        this.targetRotation = targetRotation;
    }

    public void setMovementPosition(int movementPosition) {
        this.movementPosition = movementPosition;
    }

    public int getMovementPosition() {
        return movementPosition;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getShipID() {
        return shipID;
    }

    public void setShipID(int shipID) {
        this.shipID = shipID;
    }

    public long getTime() {
        return time;
    }

}
