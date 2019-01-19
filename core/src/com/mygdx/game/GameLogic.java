package com.mygdx.game;
import java.util.*;
import java.lang.Integer;

public class GameLogic {
    private Boolean gameComplete; //boolean for whether or not the game is complete
    private Boolean isWon; //boolean for whether the game is won or not - used after game is resolved
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

    //GameLogic constructor - includes building node map
    public GameLogic(int colleges, int departments, String pCollege, String pName) {
        gameComplete = Boolean.FALSE;
        currentNode = 0;
        currentScore = 0;
        currentSupplies = 12;
        currentGold = 100;
        currentTurn = 1;
        objectives = getCollegeObjectives(pCollege);
        player = new Player(pName,pCollege);
        Node node = new Node(0,0,0);
        nodeList = node.nodeMapGenerator();
        neighborNodes = nodeList[0].getConnectnodes();
        isWon = Boolean.FALSE;
    }

    //iterates through objectives, if all complete returns true and changes gameComplete
    public Boolean checkWin(){
        Boolean outcome = Boolean.TRUE;
        for (int i=0; i<=objectives.length-1;i++){
            if (!checkObjective(objectives[i])) {
                outcome = Boolean.FALSE;
            }
        }
        if (outcome) {
            gameComplete = Boolean.TRUE;
            isWon = Boolean.TRUE;
        }
        return outcome;
    }

    //checks playerShip's health or supplies, if below 0 return true and change gameComplete;
    public Boolean checkLoss(){
        if (player.getPlayerShip().getIsDead() || (currentSupplies < 0)){
            gameComplete = Boolean.TRUE;
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

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
                if (currentGold >= Integer.parseInt(details[1])) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            case 'T':
                if (currentTurn >= Integer.parseInt(details[1])) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            case 'S':
                if (currentScore >= Integer.parseInt(details[1])) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
                //check college relations
            case 'C':
                CollegeNode nodeCollege; //used to cast the relevant nodes to their node list
                //alcuin
                if (details[1].equals("A")) {
                    nodeCollege = (CollegeNode) nodeList[0];
                    //derwent
                } else if (details[1].equals("D")) {
                    nodeCollege = (CollegeNode) nodeList[2];
                } else {
                    nodeCollege = new CollegeNode(999,999,999,"unknown",999,new Card(),new Ship());
                }
                if (nodeCollege.getCollegeStatus() == 1){
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            case 'E':
                DepartmentNode deptNode; //used to cast the relevant nodes to their node list
                //alcuin
                if (details[1].equals("C")) {
                    deptNode = (DepartmentNode) nodeList[3];
                    //comp sci
                } else if (details[1].equals("D")) {
                    deptNode = (DepartmentNode) nodeList[2];
                //dummy department which is never true - for identifying if something has gone wrong
                } else {
                    deptNode = new DepartmentNode(999,999,999,"failure",999,999, new Ship());
                }
                if (deptNode.getDepartmentStatus() == 1){
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
        neighborNodes = nodeList[currentNode].getConnectnodes();
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
            out[0] = "G-500";
            out[1] = "T-10";
            out[2] = "S-100";
            out[3] = "C-A";
            out[4] = "C-D";
            out[5] = "E-C";
            return out;
        } else if (pCollege.equals("TestCollege")) {
            out[0] = "S-100";
            out[1] = "S-100";
            out[2] = "S-100";
            out[3] = "S-100";
            out[4] = "S-100";
            out[5] = "S-100";
            return out;
        } else {
            return out;
        }
    }

    public String obj2Str(String objective){
        String[] details = objective.split("-");
        switch(details[0].charAt(0)){
            case 'T':
                return "Reach turn "+ details[1];
            case 'G':
                return "Have "+details[1]+" gold";
            case 'S':
                return "Have "+details[1]+ " score";
            case 'C':
                if (details[1].equals("A")){
                    return "Have Alcuin allied";
                }
                if (details[1].equals("D")){
                    return "Have Derwent allied";
                }
            case 'E':
                if (details[1].equals("C")){
                    return "Have Computer Science allied";
                }
            default: return "objective failure";
        }
    }

    //score always goes up, adds to score
    public void addScore (int in) {
        currentScore += in;
    }

    //supplies getter/setters + adder (can add negative value to lose)
    public Integer getCurrentSupplies () {
        return currentSupplies;
    }

    public void setCurrentSupplies (int in) {
        currentSupplies = in;
    }

    public void addSupplies (int in){
        currentSupplies = currentSupplies + in;
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

    public Boolean getIsWon(){
        return isWon;
    }

    public Player getPlayer() {
        return player;
    }
}


