package com.mygdx.game;


import java.util.ArrayList;

/*
Not actually annotations: trying to make notes of how this should work
An encounter occuring is an instance of the encounter object
When an instance is created, a type of encounter is randomly chosen
For that type of encounter: options, score and descriptions are read from a database and effects is entered at initialisation

*/
public class Encounter {
    private String[] options;
    private String[] effects;
    private int score;
    private String description;


    public Encounter(String[] effects, String description, int score){
        this.effects = effects;
        this.description = description;
        this.score = score;
        options = new String[effects.length];
        //Options initialized utilizing effects
        for (int i=0; i<effects.length;i++){
            //effect number
            String num = Integer.toString(i+1);
            //plus the effect parsed as a readable string
            options[i] = num + ". " + effectString2Text(effects[i]);
        }

    }

    private String effectString2Text(String effect){
        String[] details = effect.split("-");
        switch (details[0].charAt(0)) {
            //battle, details[1] contains details for the enemy ship
            case 'B':
                return "Battle ship";
            //change supplies - details [1] indicates whether to add or subtract, details [2] by how much
            case 'S':
                if (details[1].equals("L")) {
                    return "Lose " + details[2] + " supplies";
                } else if (details[1].equals("G")) {
                    return "Gain " + details[2] + " supplies";
                }
            default: return "EFFECT NOT RECOGNIZED";
        }


    }

    public String chooseOption(int choice){
        return effects[choice];
    }

    /*
    NOTE: Battle doesn't exist so this code throws up an error
    public Battle startBattle(Ship playerShip, Ship enemyShip){
        return
    }
    */

    public String[] getOptions(){
        return options;
    }

    public String getDescription(){
        return description;
    }

    public String[] getEffects(){
        return effects;
    }

    public int getScore(){
        return score;
    }
}
