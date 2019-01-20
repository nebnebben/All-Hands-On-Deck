package com.mygdx.game;

public class Player {

    private String name;
    private String college;
    private Ship playerShip;

    public Player(String pName, String pCollege ){
        name = pName;
        college = pCollege;
        playerShip = new Ship();
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

    public Ship getPlayerShip() {
        return playerShip;
    }
}
