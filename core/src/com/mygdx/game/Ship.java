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

    //default ship - for player
    public Ship() {
        this.isDead = false;
        //adds base deck
        deck = new ArrayList<Card>();
        deck.add(new Card("fast attack", "attacks fast", 10, 0, 2, "A4"));
        deck.add(new Card("Slow attack", "attacks not so fast", 5, 0, 4, "A4D6"));
        // constructor.
        name = "The Pigeons Droppings";
        totalHealth = 50;
        currentHealth = 50;
        totalMana = 20;
        manaRegenRate = 120;
        pointsWorth = 30;
        goldAmount = 50;
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


    public void addCard(Card card){
        deck.add(card);
    }

    public int getHealth(){
        return totalHealth;
    }

    public void setHealth(int in){
        currentHealth = in;
    }

    public void setTotalHealth(int in){
        totalHealth = in;
    }
}
