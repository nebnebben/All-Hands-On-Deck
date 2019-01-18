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
        //for multiple effect effects
        String[] sumEffects = effect.split("&");
        //and counter for phrasing
        int andCounter = sumEffects.length;
        //out string
        String out = "";
        for (String e: sumEffects) {
            System.out.println(e);
            String[] details = effect.split("-");
            System.out.println(details[0].charAt(0));
            switch (details[0].charAt(0)) {
                //battle, details[1] contains details for the enemy ship
                case 'B':
                    out += "Battle ship";
                    break;
                //change supplies - details [1] indicates whether to add or subtract, details [2] by how much
                case 'S':
                    if (details[1].equals("L")) {
                        out += "Lose " + details[2] + " supplies";
                    } else if (details[1].equals("G")) {
                        out += "Gain " + details[2] + " supplies";
                    }
                    break;
                case 'H':
                    if (details[1].equals("L")){
                        out += "Lose " +details[2] + " health";
                    } else if (details[1].equals("G")){
                        out += "Gain " +details[2] + "health";
                    } else if (details[1].equals("M")){
                        out += "Gain full health";
                    }
                    break;
                case 'G':
                    if (details[1].equals("L")){
                        out += "Lose " + details[2] + " gold";
                    } else if (details[1].equals("G")){
                        out += "Gain " + details[2] + " gold";
                    }
                //max health
                case 'I':
                    if (details[1].equals("L")){
                        out += "Lose "+details[2] + " max health";
                    } else if (details[1].equals("G")) {
                        out += "Gain " +details[2] + " max health";
                    }
                    break;
                default:
                    out= "EFFECT NOT RECOGNIZED";
                    break;
            }
            //dec and counter
            andCounter -= 1;
            //if there is more than 1 effect left, add comma. If there is one, add and. Otherwise nothing
            if (andCounter > 1){
                out += ", ";
            } else if (andCounter == 1){
                out += " and ";
            }
        }
        return out;



    }

    public String chooseOption(int choice){
        return effects[choice];
    }

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
