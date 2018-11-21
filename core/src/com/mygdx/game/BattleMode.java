package com.mygdx.game;

import javax.smartcardio.Card;

/**
 * The BattleMode class, handles the entirety of the battle scenario.
 */
public class BattleMode {
    //Calling instances of the ship class for the enemy and the player.
    private Ship enemyShip;
    private Ship playerShip;
    //The mana count for both the player and the enemy.
    private Integer playerMana;
    private Integer enemyMana;
    //The currently held cards for the enemy and the player.
    private List<Card> playerHand;
    private List<Card> enemyHand;
    //Keeps track of the progression of time, giving both players mana and playing their moves.
    private Integer clock;
    //The deck that both the enemy and the player brings into the current battle.
    private List<Card> playerTempDeck;
    private List<Card> enemyTempDeck;

    /**
     * Increases the value of "clock" by 1.
     */
    public void UpdateClock(){}

    /**
     * Takes in the user's card choice and begins to put it into play.
     * @param choice - the user's card choice as an Integer.
     * @return The card that the user plays.
     */
    public Card playerPlayCard(Integer choice){return null;}

    /**
     * Takes in the enemy's card choice and begins to put it into play.
     * @return The card that the enemy plays.
     */
    public Card enemyPlayCard(){return null;}

    /**
     * Draws a card from the TempDeck and gives it into the selected person's hand.
     * @param isPlayer - Boolean to see if the draw is for the user or the enemy.
     */
    public void drawCard(Boolean isPlayer){}

    /**
     * Puts the card what was chosen into play.
     * @param card - a given card and its information.
     * @param target - whom the effects of the card apply to.
     */
    public void applyCard(Card card, String target){}

    /**
     * Increases the value of someone's mana by 1. (May need to make it so larger than 1 mana increases are possible.
     * @param target - whose mana is going to be updated.
     */
    public void updateMana(String target){}

    /**
     * Updates the deck of a chosen target, not too sure what this entails, possibly making sure that a drawn card disappears from their deck and cannot be drawn again. (?)
     * @param target - who is going to going to have their deck updated.
     */
    public void updateDeck(String target){}

}
