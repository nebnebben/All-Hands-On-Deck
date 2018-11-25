package com.mygdx.game;

//import javax.smartcardio.Card;
import java.util.*;

/**
 * The BattleMode class, handles the entirety of the battle scenario.
 */
public class BattleMode {
    //Calling instances of the ship class for the enemy and the player.
    private Ship enemyShip;
    private Ship playerShip;
    //The mana count and max for both the player and the enemy.
    private Integer playerMana;
    private Integer enemyMana;
    private Integer playerManaMax;
    private Integer enemyManaMax;
    //ShipEffects, buffs, debuffs, and ailments
    private String playerShipEffects;
    private String enemyShipEffects;
    //The currently held cards for the enemy and the player.
    private List<Card> playerHand;
    private List<Card> enemyHand;
    //Keeps track of the progression of time, giving both players mana and playing their moves.
    private Integer clock;
    //The deck that both the enemy and the player brings into the current battle.
    private List<Card> playerTempDeck;
    private ArrayList<Card> enemyTempDeck;

    //Temp, not present in the documents but will be here most likely
    private Integer playerShipHealthMax;
    private Integer enemyShipHealthMax;
    private Integer playerShipHealth;
    private Integer enemyShipHealth;


    public BattleMode() {
        playerManaMax = 20;
        enemyManaMax = 20;
        playerMana = 0;
        enemyMana = 0;
        clock = 0;
        //need to define cards and both hands

        Ship playerShip = new Ship();
        Ship enemyShip = new Ship();
        playerShip.setManaRegenRate(60);
        enemyShip.setManaRegenRate(60);

        //These aren't included anywhere, so they probably should be added somewhere, temp here maybe.
        playerShipHealthMax = 10;
        enemyShipHealthMax = 10;
        playerShipHealth = 10;
        enemyShipHealth = 10;

    }



    /**
     * Increases the value of "clock" by 1.
     */
    public void UpdateClock(){
        clock++;}

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
     * @param amount - the amount of mana, positive or negative, that will be applied to the current count.
     */
    public void updateMana(String target, Integer amount){
        if(target == "player"){
            playerMana+=amount;
            if (playerMana > playerManaMax){playerMana = playerManaMax;}
        } else if (target == "enemy"){
            enemyMana+=amount;
            if (enemyMana > enemyManaMax){enemyMana = enemyManaMax;}
        }
    }

    /**
     * Updates the deck of a chosen target, not too sure what this entails, possibly making sure that a drawn card disappears from their deck and cannot be drawn again. (?)
     * @param target - who is going to going to have their deck updated.
     */
    public void updateDeck(String target){}

    /**
     * Updates the health of a target by a given amount, positive or negative.
     * @param target - The ship that will be receiving the health change.
     * @param amount - The amount of said change, positive or negative.
     */
    public void updateHealth(String target, Integer amount){
        if(target == "player"){
            playerShipHealth+=amount;
            if (playerShipHealth > playerShipHealthMax){
                playerShipHealth = playerShipHealthMax;}
            else if (playerShipHealth <= 0){
                //PlayerDied
                playerShip.setIsDead(true);
            }

        } else if (target == "enemy"){
            enemyMana+=amount;
            if (enemyShipHealth > enemyShipHealthMax){
                enemyShipHealth = enemyShipHealthMax;}
            else if (enemyShipHealth <= 0){
                //Enemy Died
                enemyShip.setIsDead(true);
            }
        }
    }

    /**
     * Update clock and increment mana if specific ship's regen amount has been reached.
     * This most likely isn't needed and will be in the actual section that does the graphics
     * and actually runs it in the render class. However, this might as well be here as a temporary thing.
     */
    public void updateClock(){
        clock += 1;
        if (clock % playerShip.getManaRegenRate() == 0){
            updateMana("player", 1);
        }
        if (clock % enemyShip.getManaRegenRate() == 0){
            updateMana("enemy", 1);
        }
    }

}
