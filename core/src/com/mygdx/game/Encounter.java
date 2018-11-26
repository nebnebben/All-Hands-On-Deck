package com.mygdx.game;


import java.util.ArrayList;

/*
Not actually annotations: trying to make notes of how this should work
An encounter occuring is an instance of the encounter object
When an instance is created, a type of encounter is randomly chosen
For that type of encounter: options, score and descriptions are read from a database and effects is entered at initialisation

*/
public class Encounter {
    private ArrayList<String> options;
    private ArrayList<String> effects;
    private int score;
    private String description;

    public Encounter(){
        Encounter encounter = new Encounter();
        encounter.effects = new ArrayList<String>();
        /*
        TO DO:
        Type of encounter is randomly chosen and options, effects and description are all read from some kind of database

         */

    }

    public Encounter(ArrayList<String> effects){
        Encounter encounter = new Encounter();
        encounter.effects = effects;
        /*
        TO DO:
        Type of encounter is randomly chosen and options, effects and description are all read from some kind of database

         */

    }

    public void chooseOption(int choice){

    }

    /*
    public Battle startBattle(Ship playerShip, Ship enemyShip){
        return
    }
    */
}
