package com.carwale.aepl.assignment5;

import java.util.ArrayList;

/**
 * Created by aepl on 28/6/16.
 */
public class State {
    ArrayList<City> cities;
    String stateName;
    int stateId;

    public State(ArrayList<City> cities, String stateName, int stateId){
        this.cities = cities;
        this.stateName = stateName;
        this.stateId = stateId;
    }
}
