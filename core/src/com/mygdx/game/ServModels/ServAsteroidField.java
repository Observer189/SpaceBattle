package com.mygdx.game.ServModels;

/**
 * Created by Sash on 14.11.2018.
 */

public class ServAsteroidField {
    private ServAsteroid[] asteroids;

    public ServAsteroidField() {

    }

    public ServAsteroidField(int asteroidsNumber) {
        asteroids = new ServAsteroid[asteroidsNumber];
    }

    public ServAsteroid[] getAsteroids() {
        return asteroids;
    }

    public ServAsteroidField(ServAsteroidField asts) {
        asteroids = asts.getAsteroids();
    }

    public void setAsteroids(ServAsteroid[] asteroids) {
        this.asteroids = asteroids;
    }
}
