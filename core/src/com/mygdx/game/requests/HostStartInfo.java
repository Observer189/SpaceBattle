package com.mygdx.game.requests;

import com.mygdx.game.ServModels.ServAsteroid;
import com.mygdx.game.ServModels.ServAsteroidField;
import com.mygdx.game.model.AsteroidField;

/**
 * Created by Sash on 06.11.2018.
 */

public class HostStartInfo {
    int enemyConID;
    ServAsteroidField field;
    public HostStartInfo()
    {

    }
    public HostStartInfo(int enemyConID)
    {
        this.enemyConID=enemyConID;
    }

    public int getEnemyConID() {
        return enemyConID;
    }

    public void setEnemyConID(int enemyConID) {
        this.enemyConID = enemyConID;
    }

    public ServAsteroidField getField() {
        return field;
    }

    public void setField(ServAsteroidField field) {
        this.field = field;
    }
}
