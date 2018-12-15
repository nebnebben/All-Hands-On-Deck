package com.mygdx.game;


import java.util.*;

/**
 * The BattleMode class, handles the entirety of the battle scenario.
 */
public class BattleMode {
    //Calling instances of the ship class for the enemy and the player.
    //Made public for now, may not be correct.
    public Ship enemyShip;
    public Ship playerShip;
    //The mana count and max for both the player and the enemy.
    private Integer playerMana;
    private Integer enemyMana;
    private Integer playerManaMax;
    private Integer enemyManaMax;
    //ShipEffects, buffs, debuffs, and ailments
    private String playerShipEffects;
    private String enemyShipEffects;
    //The currently held cards for the enemy and the player.
    private List<Card> playerHand = new ArrayList<Card>();
    private List<Card> enemyHand = new ArrayList<Card>();
    ////private Card playerCurrentCard;
    ////private Card enemyCurrentCard;
    //Keeps track of the progression of time, giving both players mana and playing their moves.
    private double clock;
    //The deck that both the enemy and the player brings into the current battle.
    private List<Card> playerTempDeck = new ArrayList<Card>(); //the definition of =new arraylist will not be needed when the deck is inputted from the other mode, as needs be.
    private List<Card> enemyTempDeck; //Maybe remove - May not be too much of a pain to fully implement.
    private List<Card> discardedCards = new ArrayList<Card>();


    //Temp for ships, not present in the documents but will be here most likely
    private Integer playerShipHealthMax;
    private Integer enemyShipHealthMax;
    private Integer playerShipHealth;
    private Integer enemyShipHealth;
    private Integer playerShipManaRate;
    private Integer enemyShipManaRate;
    private boolean playerShipDead;
    private boolean enemyShipDead;

    private double playerShipDefence; //Goes from 0 to 1 (100%).
    private double enemyShipDefence;

    //The player earns points depending on the fight and how it goes. Load the # of points gained from fighting a given ship.
    //Maybe also give bonuses on speed, how much damage was taken, etc?
    private Integer playerScore;
    private Integer scoreToGain;
    private Integer playerGoldAmount;
    private Integer goldToGain;
    private Boolean deathTriggered = false;





    public BattleMode() {
        //Some of these depend on the actual ship stats. Pull all of this from existing data when battle starts.
        playerManaMax = 25;
        enemyManaMax = 5;
        playerMana = 0;
        enemyMana = 0;
        playerShipHealthMax = 20;
        enemyShipHealthMax = 50;
        playerShipHealth = 20;
        enemyShipHealth = 50;
       //

        clock = 0;


        Ship playerShip = new Ship();
        Ship enemyShip = new Ship();
        playerShipDead = false;
        enemyShipDead = false;

        // Get this from the ship classes too
        playerShip.setPointsWorth(0); //Import the total score that the player gained to here.
        playerScore = playerShip.getPointsWorth();
        playerShip.setGoldAmount(20);
        playerGoldAmount = playerShip.getGoldAmount();
        playerShip.setManaRegenRate(30); //60 for 1 second, 120 for 2 seconds, etc.
        enemyShip.setManaRegenRate(60);
        playerShipDefence = 0;
        enemyShipDefence = 0;
        //

        //Adding an enemy attack.
        Card basicEnemyAttack = new Card();
        basicEnemyAttack.setEffect("A3"); //A = attack.  D = Defend.
        basicEnemyAttack.setManaCost(5);
        enemyHand.add(basicEnemyAttack);



        Card basicPlayerAttack = new Card();
        Card basicPlayerDefend = new Card();
        Card basicPlayerAttack2 = new Card();
        Card basicPlayerDefend2 = new Card();
        Card basicPlayerAttackDefendCombo = new Card();
        Card specialPlayerAttack = new Card();

        basicPlayerAttack.setEffect("A2"); // The number for A is the attack value.
        basicPlayerAttack.setManaCost(2);
        basicPlayerDefend.setEffect("D3"); //For D, that'll be the % block applied. 5 will be divided by 10 and applied to the defence value.
        basicPlayerDefend.setManaCost(2);  //...Hence, 5 = 0.5 = 50% armour block.
        basicPlayerAttack2.setEffect("A4");
        basicPlayerAttack2.setManaCost(4);
        basicPlayerDefend2.setEffect("D5");
        basicPlayerDefend2.setManaCost(5);
        basicPlayerAttackDefendCombo.setEffect("A3D3");
        basicPlayerAttackDefendCombo.setManaCost(6);
        specialPlayerAttack.setEffect("A7");
        specialPlayerAttack.setManaCost(3);
        specialPlayerAttack.setGoldCost(5);

        playerTempDeck.add(basicPlayerAttack); //These will be replaced in the future, import deck from the collection given from the other class
        playerTempDeck.add(basicPlayerDefend);
        playerTempDeck.add(basicPlayerAttack2);
        playerTempDeck.add(basicPlayerDefend2);
        playerTempDeck.add(basicPlayerAttackDefendCombo);
        playerTempDeck.add(specialPlayerAttack);

        for (int i = 0; i < 4; i++) { //Make playerHand a size of 4, and draw cards for them all.
            playerHand.add(null);
            drawCard(i);
        }

        //drawCard(0);
        //drawCard(1);
        //drawCard(2);
        //drawCard(3);
        //playerHand.add(basicPlayerAttack2);
        //playerHand.add(basicPlayerDefend2);
        //playerHand.add(basicPlayerAttackDefendCombo);
        //playerHand.add(specialPlayerAttack);



        //These aren't included anywhere, so they probably should be added somewhere, temp here maybe.
        playerShipManaRate = playerShip.getManaRegenRate();
        enemyShipManaRate = enemyShip.getManaRegenRate();


        enemyShip.setPointsWorth(500); //Instead of a solid value being passed through, we may have to take a value from the ship class/encounter database to see how much each enemy is worth.
        scoreToGain = enemyShip.getPointsWorth();
        enemyShip.setGoldAmount(10);
        goldToGain = enemyShip.getGoldAmount();

        //Can't figure out a way to add points or gold AFTER the battle is finish, so add them prematurely instead.
        playerShip.setPointsWorth(playerScore + scoreToGain);
        playerShip.setGoldAmount(playerGoldAmount + goldToGain);

    }




    /**
     * Takes in the user's card choice and begins to put it into play.
     * @param choice - the user's card choice as an Integer.
     * @return The card that the user plays.
     */
    public Card playerPlayCard(Integer choice){
        if(!(playerHand.isEmpty())){
            return playerHand.get(choice);
        } else {
            return null;
        }
    }

    /**
     * Takes in the enemy's card choice and begins to put it into play.
     * @return The card that the enemy plays.
     */
    public Card enemyPlayCard(){ //Is it really worth the effort to give the enemy a full deck/temp hand? Having 2-4 basic abilities that vary should be enough.
        if(!(enemyHand.isEmpty())){
            return enemyHand.get(0);
        } else {
            return null;
        }
    }

    /**
     * Draws a card from the TempDeck and gives it into the selected person's hand.
     * @param cardLocation - The index of the card being replaced.
     */
    public void drawCard(Integer cardLocation){
        Random random = new Random();
        boolean isPlayerHandEmpty = false;
        Integer sizeOfPlayerHand = 4;
        //The reason for this is as follows: we are wanting to keep an array size of 4 consistent for playerHand, however
        //using the .remove() function actually shortens the size. Yet .set(index,null) is still counted as an element, hence
        //isEmpty does not work, and the size() is still 4.
        //Is this the most agile way to do this? I don't know, but it worked.

        for (Card s:playerHand){
            if(s == null){
                sizeOfPlayerHand--;
            }
        }
        if (sizeOfPlayerHand == 0){
            isPlayerHandEmpty = true;
        }



        if (playerTempDeck.size() > 0 && (sizeOfPlayerHand < 4 || playerHand.size() < 4)){
            System.out.println("Adding cards from deck.");

            Integer n = random.nextInt(playerTempDeck.size()); //0 to the number in the bracket. Pick a random card from the deck and use it here.
            playerHand.set(cardLocation, playerTempDeck.get(n));
            System.out.println("Now the playerHand is: " + playerHand.toString());
            //System.out.println("Size of deck: " + playerTempDeck.size());
            //System.out.println("Trying to remove: " + playerTempDeck.get(n).getEffect());
            playerTempDeck.remove(playerTempDeck.get(n));
        }

        if (playerTempDeck.size() == 0 && (isPlayerHandEmpty || playerHand.size() == 0)){ //deck is empty and no cards left to play, reshuffle.
            System.out.println("Hand and deck are both empty!");
            playerTempDeck.addAll(discardedCards);
            discardedCards.clear();
            drawCard(0);
            drawCard(1);
            drawCard(2);
            drawCard(3);
        }
    }

    /**
     * Puts the card what was chosen into play.
     * @param card - a given card and its information.
     * @param user - who was the one who used said card.
     */
    public void applyCard(Card card, String user){
        if (user.equals("player")){
            if (card == null){
                System.out.println("No card in this slot");
                //System.out.println("Size of tempDeck: " + playerTempDeck.size());
            } else{
                if(playerMana >= card.getManaCost()){
                    updateMana("player", -card.getManaCost());

                    if(card.getEffect().contains("A")){ //attack
                        //looks long, but it finds the index of the Attack symbol ("A") and adds one to it, so it'll find the value 5 if the input is "A5", anywhere on the string.
                        //Allows for things such as "A4D1" in theory.
                        updateHealth("enemy", -Character.getNumericValue(card.getEffect().charAt(card.getEffect().indexOf("A") + 1)), true);
                    }
                    if(card.getEffect().contains("D")) { //Defend
                        updateDefence("player", Character.getNumericValue(card.getEffect().charAt(card.getEffect().indexOf("D") + 1)));
                    }

                    //Discard the card just used, put into discarded pile.
                    Integer index = playerHand.indexOf(card);
                    discardedCards.add(card);
                    System.out.println("Current index is: " + index);
                    System.out.println("At that index was: " + playerHand.get(index));
                    playerHand.set(index, null);

                    System.out.println("The deck is currently: " + playerHand.toString());
                    //playerHand.remove(card); //Doesn't work, shortens the size all together.
                    drawCard(index);
                }
            }
        }

        else if (user.equals("enemy")){
            if(enemyMana >= card.getManaCost()){
                updateMana("enemy", -card.getManaCost());
                if(card.getEffect().contains("A")){
                    updateHealth("player", -Character.getNumericValue(card.getEffect().charAt(card.getEffect().indexOf("A") + 1)), true);
                }
                if(card.getEffect().contains("D")) {
                    updateDefence("enemy", Character.getNumericValue(card.getEffect().charAt(card.getEffect().indexOf("D") + 1)));
                }
            }
        }
    }

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
     * @param isAttack - If the reason why the health is being changed is due to an attack.
     *                   This tells us if we need to ignore the defence or not.
     */
    public void updateHealth(String target, Integer amount, boolean isAttack){

        if(target == "player"){
            if(isAttack){
                //To make defence/armour a bit weaker, round up on the damage.
                playerShipHealth+= (int) (amount - Math.ceil(amount*playerShipDefence));
            } else{
                playerShipHealth+=amount;

            }
            if (playerShipHealth > playerShipHealthMax){
                playerShipHealth = playerShipHealthMax;}
            else if (playerShipHealth <= 0){
                //PlayerDied
                playerShipDead = true;
            }

        } else if (target == "enemy"){
            if(isAttack){
                enemyShipHealth+= (int) (amount - Math.ceil(amount*enemyShipDefence));
            } else{
                enemyShipHealth+=amount;
            }
            if (enemyShipHealth > enemyShipHealthMax){
                enemyShipHealth = enemyShipHealthMax;}
            else if (enemyShipHealth <= 0){
                //Enemy Died
                enemyShipDead = true;
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

        if (clock % playerShipManaRate == 0){
            updateMana("player", 1);
        }
        if (clock % enemyShipManaRate == 0){
            updateMana("enemy", 1);
        }
    }

    /**
     * Show the health current and max for a given target
     * @param target
     * @return a string consisting of (currentHp / maxHp )
     */
    public String showHealthBar(String target) {
        if (target == "player") {
            return "(" + playerShipHealth + "/" + playerShipHealthMax + ")";
        } else if (target == "enemy") {
            return "(" + enemyShipHealth + "/" + enemyShipHealthMax + ")";
        } else{ return null; }
    }

    /**
     * Shows the mana current and max for a given target
     * @param target
     * @return a string consisting of (currentMana / maxMana )
     */
    public String showManaBar(String target) {
        if (target == "player") {
            return "(" + playerMana + "/" + playerManaMax + ")";
        } else if (target == "enemy") {
            return "(" + enemyMana + "/" + enemyManaMax + ")";
        } else{ return null; }
    }

    /**
     * Trying to give the enemy ship an AI.
     * God have mercy on my soul.
     * As of 11/12/18, all it does is attack when its mana is full.
     * Future ideas: - % chances if it blocks or attacks, several attacks, create a set of attacks that'll be applied to specific enemies, etc.
     */
    public void basicEnemyAI(){
        if(enemyMana == enemyManaMax){
            applyCard(enemyPlayCard(), "enemy");
        }
    }

    /**
     * Checks if any of the ships in battle are dead.
     * @return True if a ship is dead, False if not.
     */
    public Boolean gameIsOver(){

        if(playerShipDead || enemyShipDead){
            if(enemyShipDead && !(deathTriggered)){
                deathTriggered = true;
                playerScore += scoreToGain; //This will depend on the enemyShip's score amount. Only affects the display.
                playerGoldAmount += goldToGain;
                //playerShip.setPointsWorth(playerScore); // Doesn't work.
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param target - The ship that'll be receiving the armour change.
     * @param amount - The amount of armour the ship will be receiving. Between 0 and 10, as it gets divided down between 0 to 1 (for 100%)
     */
    public void updateDefence(String target, Integer amount){

        if(target == "player"){
            playerShipDefence += (double)amount / 10;
            if(playerShipDefence > 0.7){ //Having the player be able to be fully resistant wouldn't make for an engaging game.
                playerShipDefence = 0.7;
            }
        } else if(target == "enemy"){
            enemyShipDefence += (double)amount / 10;
            if(enemyShipDefence > 0.7){
                enemyShipDefence = 0.7;
            }
        }
    }

    public String showPoints(){
        return playerScore.toString();
    }

    public String showGold() {
        return playerGoldAmount.toString();
    }


}
