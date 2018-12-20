package com.mygdx.game;
import java.util.*;
import java.lang.Integer;

public class GameController {
    private Boolean gameComplete; //boolean for whether or not the game is complete
    private Integer currentNode; //node index in node list
    private ArrayList<Integer> neighborNodes;
    private Integer currentScore; //player score
    private Integer currentSupplies; //supplies used to traverse node map
    public Integer currentGold; //gold used to buy things - functionality elsewhere
    private Integer currentTurn; //tracker of current turn
    private String[] objectives; //list of objectives to complete game
    private Player player; //containing information relating to the player
    //private List<Quests> activeQuests; //list of quests issued to the player
    private Node[] nodeList; //full node map - node connections stored in nodes.

    public ArrayList<Integer> getNeighborNodes() {
        return neighborNodes;
    }

    //GameController constructor - includes building node map
    public GameController (int colleges, int departments, String pCollege, String pName) {
        gameComplete = Boolean.FALSE;
        currentNode = 0;
        currentScore = 0;
        currentSupplies = 12;
        currentGold = 100;
        currentTurn = 1;
        objectives = getCollegeObjectives(pCollege);
        Player player = new Player(pName,pCollege);
        Node node = new Node(0,0,0);
        nodeList = node.nodeMapGenerator();
        neighborNodes = nodeList[0].getConnectnodes();
    }

    //iterates through objectives, if all complete returns true and changes gameComplete
    public Boolean checkWin(){
        Boolean outcome = Boolean.TRUE;
        for (int i=0; i<=objectives.length;i++){
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
            case 'G':
                if (currentGold > Integer.parseInt(details[1])){
                return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            case 'T':
                if (currentTurn > Integer.parseInt(details[1])){
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            default: throw new IllegalArgumentException("Invalid objective");
        }
    }

    //completes quest activeQuests(questID): removes it from activeQuests and gives rewards
    public void completeQuest (int questID) {
        ;
    }

    //changes currentNode to nodeList(targetNode) and goes through turn change process such as giving encounters
    public void traverseNode(int targetNode) {
        Encounter encounter;
        currentNode = targetNode;
        currentTurn += 1;
        currentSupplies -= 1;
        //checkLoss();
        /*
        applyWeatherEffect(nodeList[currentNode].getWeather());
        checkLoss();
        currentNodeType = nodeList[currentNode].getNodeType;
        if (currentNodeType == collegeNode){

        } else if (currentNodeType == departmentNode) {

        } else {
           give encounter as below
        }
         */
        //ArrayList<String> encounters = new ArrayList<String>(10);
        //encounter = new Encounter(encounters);

        //checkLoss();
        //checkWin();
        neighborNodes = nodeList[currentNode].getConnectnodes();
        /*
        if (nodeList[currentNode].getNodeType() ==

        */
        /*
        Looks at target node - specifically type and goes through due process
        If it is a department/college - gives options regarding to those specific nodeTypes
        Else: Triggers a random encounter from the nodes list of encounters.
         */

        //return encounter;
    }

    //adds a quest to the list of active quests
    /*
    public void addQuest(Quest quest) {
        ;
    }
    */

    public String[] getCollegeObjectives(String pCollege){
        String[] out = new String[6];
        if (pCollege.equals("Alcuin")){
            out[0] = "placeholder";
            out[1] = "placeholder";
            out[2] = "placeholder";
            out[3] = "placeholder";
            out[4] = "placeholder";
            out[5] = "placeholder";
            return out;
        } else {
            return out;
        }



    }
    //score always goes up, adds to score
    public void addScore (int in) {
        currentScore += in;
    }

    //supplies getter/setters
    public Integer getCurrentSupplies () {
        return currentSupplies;
    }

    public void setCurrentSupplies (int in) {
        currentSupplies = in;
    }



    public int getCurrentTurn(){
        return currentTurn;
    }

    public int getScore() {
        return currentScore;
    }

    public Node[] getNodeList(){
        return nodeList;
    }

    public int getCurrentNode(){
        return currentNode;
    }

    public void setCurrentNode(int in){
        currentNode = in;
    }
}
