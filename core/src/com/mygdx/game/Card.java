package com.mygdx.game;
import java.util.*;

public class Card {
    private Integer cardID; //ID integer for each card
    private String name; //name of the card
    private String description; //description of what the card does in battle
    private Integer shopCost; //how much the card costs to buy from a shop
    private Integer goldCost; //how much gold it costs to use the card in battle
    private Integer manaCost; //how much mana it costs to use the card in battle
    private String effect; //actual effect on battle statistics such as health or hit rate

    protected static Integer numberOfCards = 0; //total number of cards available in order to generate a new cardID

    //returns costs of cards
    public List<Integer> getBattleCost(){
        return Arrays.asList(shopCost, goldCost, manaCost);
    }

    public Card(){
        numberOfCards++;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;

    }

    public void setCardID(Integer cardID) {
        this.cardID = cardID;
    }

    public Integer getCardID() {
        return cardID;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setShopCost(Integer shopCost) {
        this.shopCost = shopCost;
    }

    public Integer getShopCost() {
        return shopCost;
    }
    public void setGoldCost(Integer goldCost) {
        this.goldCost = goldCost;
    }

    public Integer getGoldCost() {
        return goldCost;
    }

    public void setManaCost(Integer manaCost) {
        this.manaCost = manaCost;
    }

    public Integer getManaCost() {
        return manaCost;
    }

    public void setEffect (String effect) {
        this.effect = effect;
    }

    public String getEffect() {
        return effect;
    }
}
