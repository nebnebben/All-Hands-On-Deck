package com.mygdx.game;

public class Player {

    public Player(){
        Ship PlayerShip = new Ship();
    }

    private String name;
    private String college;

    //do i need these or do i get from Ship?
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
