package com.mygdx.game;

import java.util.ArrayList;

public class Ship {
    private String name;
    private Boolean isDead;
    private Integer totalHealth;
    private Integer currentHealth;
    private Integer totalMana;
    private Integer manaRegenRate; //60 for 1 second, 120 for 2 seconds, etc.
    private Integer pointsWorth;
    private Integer goldAmount;
    private ArrayList<Card> deck;
    private Boolean isSpecial; //For special enemies, e.g. colleges and departments.


    public Ship() {
        this.isDead = false;
        //basic constructor.
    }

    public Ship(Integer totalHealth, Integer currentHealth, Integer totalMana, Integer manaRegenRate,
                Integer pointsWorth, Integer goldAmount, ArrayList<Card> deck, Boolean isSpecial) {
        this.isDead = false;
        this.totalHealth = totalHealth;
        this.currentHealth = currentHealth;
        this.totalMana = totalMana;
        this.manaRegenRate = manaRegenRate;
        this.pointsWorth = pointsWorth;
        this.goldAmount = goldAmount;
        this.deck = deck;
        this.isSpecial = isSpecial;


        //enemy constructor.
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getTotalHealth(){return totalHealth;}

    public Integer getCurrentHealth(){return currentHealth;}

    public void setTotalMana(Integer totalMana) {
        this.totalMana = totalMana;
    }

    public Integer getTotalMana() {
        return totalMana;
    }

    public void setManaRegenRate(Integer manaRegenRate) {
        this.manaRegenRate = manaRegenRate;
    }

    public Integer getManaRegenRate() {
        return this.manaRegenRate;
    }

    //public void addCard(Card card){
        //adds card var to ship from type Card
    //}

    public void removeCard(Integer cardIndex){
        //removes card var to ship from type Card
    }

    public void setPointsWorth(Integer pointsWorth){
        this.pointsWorth = pointsWorth;
    }

    public Integer getPointsWorth(){
        return this.pointsWorth;
    }


    public void setGoldAmount(Integer goldAmount){
        this.goldAmount = goldAmount;
    }

    public Integer getGoldAmount(){
        return this.goldAmount;
    }

    public Boolean getIsDead(){
        return isDead;
    }

    public void setIsDead(){this.isDead = true;}

    public Boolean getIsSpecial(){return isSpecial;}

    public ArrayList<Card> getDeck(){return deck;}



}
