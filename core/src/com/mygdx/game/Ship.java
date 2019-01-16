package com.mygdx.game;

import java.util.ArrayList;

public class Ship {
    private String name;
    private Boolean isDead;
    private Integer totalHealth;
    private Integer totalMana;
    private Integer manaRegenRate;
    private Integer pointsWorth;
    private Integer goldAmount;
    private ArrayList<Card> deck;

    //default ship
    public Ship() {
        this.isDead = false;
        //adds base deck
        deck = new ArrayList<Card>();
        deck.add(new Card());
        // constructor.
        name = "The Pigeons Droppings";
        totalHealth = 250;
        totalMana = 250;
        manaRegenRate = 10;
        pointsWorth = 30;
        goldAmount = 50;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


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

    public void addCard(Card card){
        deck.add(card);
    }

    public int getHealth(){
        return totalHealth;
    }
}
