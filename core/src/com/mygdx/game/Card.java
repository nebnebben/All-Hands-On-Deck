package com.mygdx.game;
import java.util.*

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



}
