package com.mygdx.game;
import java.util.*;
import java.lang.Integer;

public class GameController {
    private Boolean gameComplete; //boolean for whether or not the game is complete
    private Integer currentNode; //node index in node list
    private Integer currentScore; //player score
    private Integer currentSupplies; //supplies used to traverse node map
    public Integer currentGold; //gold used to buy things - functionality elsewhere
    private Integer currentTurn; //tracker of current turn
    private String[] objectives; //list of objectives to complete game
    private Player player; //containing information relating to the player
    //private List<Quests> activeQuests; //list of quests issued to the player
    private List<Node> nodeList; //full node map - node connections stored in nodes.


    //GameController constructor - includes building node map
    public GameController () {
        ;
    }

    //iterates through objectives, if all complete returns true and changes gameComplete
    public Boolean checkWin(){
        Boolean outcome = Boolean.TRUE;
        for (int i=0; i<=objectives.length();i++){
            if (!checkObjective(objectives[i])) {
                outcome = Boolean.FALSE;
            }
        }
        if (outcome) {
            gameComplete = Boolean.TRUE;
        }
        return outcome;
    }

    //checks playerShip's health or supplies, if below 0 return true and change gameComplete;
    public Boolean checkLoss(){
        /*
        if (player.playerShip.health <= 0 || currentSupplies < 0){
            gameComplete = Boolean.TRUE;
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
         */
        return Boolean.TRUE; //placeholder
    }

    //takes in quest, goes through its objectives and checks if it has been complete
    /*
    public Boolean checkQuestCompletion(quest: Quest) {
        Boolean outcome = Boolean.TRUE;
        for (int i=0;i<size(quest.Objectives);i++){
            if (!checkObjective(quest.Objectives[i]){
                outcome = Boolean.FALSE;
            }
        }
        return outcome;
    }
     */

    //looks at individual objectives as part of quests or the overall objective and checks for completion
    //objective format "ObjectiveTypeCharacter-RelatedDetail1-..."
    public Boolean checkObjective (String objective) {
        //splits objective into component parts - first part indicates what kind of objective. Rest objective specific
        String[] details = objective.split("-");
        switch (details[0].charAt(0)) {
            //Checks for the specified amount of gold
            case "G":
                if (currentGold > Integer.parseInt(details[1])){
                return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            case "T":
                if (currentTurn > Integer.parseInt(details[1])){
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            default: throw new IllelgalArgumentException("Invalid objective");
        }

        return Boolean.TRUE;
    }

    //completes quest activeQuests(questID): removes it from activeQuests and gives rewards
    public void completeQuest (Integer questID) {
        ;
    }

    //changes currentNode to nodeList(targetNode) and goes through turn change process such as giving encounters
    public void traverseNode(Integer targetNode) {
        currentNode = targetNode;
        currentTurn += 1;
        currentSupplies -= 1;
        //applies entrance effects - dependent on type of node/weather
        //checkWin
        //checkLoss
        /*
        Looks at target node - specifically type and goes through due process
        If it is a department/college - gives options regarding to those specific nodeTypes
        Else: Triggers a random encounter from the nodes list of encounters.
         */
    }

    //adds a quest to the list of active quests
    /*
    public void addQuest(Quest quest) {
        ;
    }
    */

    //score always goes up, adds to score
    public void addScore (Integer in) {
        currentScore += in;
    }

    //supplies getter/setters
    public Integer getCurrentSupplies () {
        return currentSupplies;
    }

    public void setCurrentSupplies (Integer in) {
        currentSupplies = in;
    }

    //ends the game differently depending on whether it was won or lost
    public void endGame (Boolean win) {
        if (win){
            ;
        } else {
            ;
        }

    }

}
