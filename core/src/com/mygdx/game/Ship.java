package com.mygdx.game;

public class Ship {
    private String name;
    //private Boolean isDead;
    private Integer totalMana;
    private Integer manaRegenRate;


    public Ship() {
        //this.isDead = false;
        // constructor.
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //public void setIsDead() {
    //    isDead = true;
    //}

    //public Boolean getIsDead()
    //{  return isDead;
    //}

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



}
