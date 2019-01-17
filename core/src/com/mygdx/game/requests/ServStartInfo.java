package com.mygdx.game.requests;

import com.mygdx.game.ServModels.ServAsteroidField;
import com.mygdx.game.ServModels.ServPlayer;

/**
 * Created by Sash on 23.11.2018.
 */

public class ServStartInfo {
    private int battleID;
    private ServPlayer[] players;

    private ServAsteroidField asteroidField;

    public ServAsteroidField getAsteroidField() {
        return asteroidField;
    }

    public void setAsteroidField(ServAsteroidField asteroidField) {
        this.asteroidField = asteroidField;
    }

    public ServPlayer[] getPlayers() {
        return players;
    }

    public void setPlayers(ServPlayer[] players) {
        this.players = players;
    }

    public int getBattleID() {
        return battleID;
    }

    public void setBattleID(int battleID) {
        this.battleID = battleID;
    }
}
