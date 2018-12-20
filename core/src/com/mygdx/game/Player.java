package com.mygdx.game;

public class Player {

    public Player(){
        Ship PlayerShip = new Ship();
    }

    private String name;
    private String college;

    public Player(String pName, String pCollege ){
        name = pName;
        college = pCollege;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCollege() {
        return college;
    }
}
