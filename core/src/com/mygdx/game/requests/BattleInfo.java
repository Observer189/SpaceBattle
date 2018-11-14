package com.mygdx.game.requests;

import com.mygdx.game.model.Player;

/**
 * Created by Sash on 30.10.2018.
 */

public class BattleInfo {
    private String message;
    private RequestType requestType;
    private int queueTurn;
    private String status;
    private int connectionID;
    private boolean isHost;
    private int queueSize;
    private int enemyId;
    private Player player;
    private Player enemy;
    public String getMessage() {
        return message;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public enum RequestType
    {
        Adding,Info
    }

    public int getQueueTurn() {
        return queueTurn;
    }

    public void setQueueTurn(int queueTurn) {
        this.queueTurn = queueTurn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setConnectionID(int connectionID) {
        this.connectionID = connectionID;
    }

    public int getConnectionID() {
        return connectionID;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public int getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(int enemyId) {
        this.enemyId = enemyId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getEnemy() {
        return enemy;
    }

    public void setEnemy(Player enemy) {
        this.enemy = enemy;
    }
}
