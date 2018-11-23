package com.mygdx.game.requests;

import com.mygdx.game.ServModels.ServAsteroidField;

/**
 * Created by Sash on 23.11.2018.
 */

public class ServStartInfo {
    private ServAsteroidField asteroidField;

    public ServAsteroidField getAsteroidField() {
        return asteroidField;
    }

    public void setAsteroidField(ServAsteroidField asteroidField) {
        this.asteroidField = asteroidField;
    }
}
