package com.mygdx.game;
import java.util.*

public class GameController {
    private Boolean gameComplete; //boolean for whether or not the game is complete
    private Integer currentNode; //node index in node list
    private Integer currentScore; //player score
    private Integer currentSupplies; //supplies used to traverse node map
    public Integer currentGold; //gold used to buy things - functionality elsewhere
    private Integer currentTurn; //tracker of current turn
    private List<String> objective; //list of objectives to complete game
    /* temporary until proper classes implemented.
    private Player player; //containing information relating to the player
    private List<Quests> activeQuests; //list of quests issued to the player
    private List<Node> nodeList; //full node map - node connections stored in nodes.
     */

    //iterates through objectives, if all complete returns true and changes gameComplete
    public Boolean checkWin(){
        return True;
    }

    //checks playerShips health or supplies, if below 0 return true and change gameComplete;
    public Boolean checkLoss(){
        return False;
    }

    //takes in quest, goes through its objectives and checks if it has been complete
    /*
    public Boolean checkQuestCompletion(quest: Quest) {

    }
     */

    //looks at individual objectives as part of quests or the overall objective and checks for completion
    public Boolean checkObjective (String objective) {
        return True;
    }

    //completes quest activeQuests(questID): removes it from activeQuests and gives rewards
    public void completeQuest (Integer questID) {
        ;
    }

    //changes currentNode to nodeList(targetNode) and goes through turn change process such as giving encounters
    public void traverseNode(Integer targetNode) {
        currentNode = targetNode;
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
        return True;
    }

}
