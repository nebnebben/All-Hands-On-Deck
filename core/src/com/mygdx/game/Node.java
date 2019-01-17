package com.mygdx.game;

import java.util.*;
//import java.util.Random;
//import java.util.function.IntToDoubleFunction;
//import java.util.Arrays;
//import java.util.Comparator;


public class Node {
    private int id;                 //Unique node identifier
    private ArrayList<Integer> connectnodes;      //Other nodes that are attached to the current node
    private String nodeType;        //Type of the node
    private String weather;         //What weather the node has
    private boolean visibility;     //What the visibility is?
    private String[] encounters;    //The possible encounters
    private int encounterchance;    //Chance of an encounter
    private int x;                  //x location of node
    private int y;                  //y location of node
    private Ship bossShip;

    //Instatiates the Node
    public Node(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
    }


//public void applyEffect();          //What effect the node has

    //Gets co-ordinates of the node
    public Integer[] getCoord(){
        Integer xy[] = {x,y};
        return xy;
    }

    //Randomly decides on which encounter to selectz
    public Encounter giveEncounter() {
        Random chance = new Random();
        int n = chance.nextInt(100)+1;
        if (n > encounterchance) {
            /*
            chance = new Random();
            n = chance.nextInt(encounters.length);
            //encounter[n]
            */
            String[] encounterEffects = new String[2];
            encounterEffects[0] = "S-L-2";
            encounterEffects[1] = "B-50-50-5-50-50-pot,pot,5,5,5,A2";
            return new Encounter(encounterEffects, "You run into a rock and require repairs to your ship",5);
        } else {
            //no encounter
            String[] encounterEffects = new String[2];
            encounterEffects[0] = "S-L-2";
            encounterEffects[1] = "S-L-3";
            return new Encounter(encounterEffects, "You run into a rock and require repairs to your ship", 5);
        }
    }

    //Generate list of nodes
    //each node has (initially) id, x, y - add everything else later
    //id = 0,1,2...
    //x+y = random, regenerate if within z radius of other nodes
    //Attach each node to 3 nearest nodes

    public static Node[] nodeMapGenerator(){
        //Defines the Map, and gets random numbers for the node location
        Node[] Map = new Node[20];
        Random randomx = new Random();
        Random randomy = new Random();
        Boolean inbounds = true;
        for (int i = 0; i < Map.length; i++){
            //Gives each node an id, and a default location
            if(i < 5){
                switch (i){
                    //First 3 nodes are colleges, 4+5 are departments
                    case 0:
                        //first initializes colleges unique card, then changes a node to a collegeNode
                        Card alcuinCard = new Card("something", "something", 10, 0, 2, "A2");
                        Map[i] = new CollegeNode(i, 0,0,"Alcuin", 1, alcuinCard);
                        Map[i].setNodeType("College");
                        break;
                    case 1:
                        Card jamesCard = new Card("something", "something", 10, 0, 2, "A2");
                        Map[i] = new CollegeNode(i, 0,0,"James", 0, jamesCard);
                        Map[i].setNodeType("College");
                        break;
                    case 2:
                        Card derwentCard = new Card("something", "something", 10, 0, 2, "A2");
                        Map[i] = new CollegeNode(i, 0,0,"Derwent", 0, derwentCard);
                        Map[i].setNodeType("College");
                        break;
                    case 3:
                        Map[i] = new DepartmentNode(i, 0,0,"Comp Sci", 1);
                        Map[i].setNodeType("Department");
                        break;
                    case 4:
                        Map[i] = new DepartmentNode(i, 0,0,"Philosophy", 1);
                        Map[i].setNodeType("Department");
                        break;
                }

            } else {
                //Otherwise just normal nodes
                Map[i] = new Node(i,0,0);
                Map[i].setNodeType("Normal");
            }
            inbounds = true;
            //Loop to check no node is within x distance of each other
            while(inbounds == true){
                inbounds = false;
                //Random co-ordinates generating
                Map[i].x = randomx.nextInt(200);
                Map[i].y = randomx.nextInt(200);

                //Check to see whether the co-ordinates are within x of another node, if yes loop
                for (int j = 0; j < i; j++){
                    //Ensures colleges (first 3) are further away from each other than normal nodes
                    if (i < 3){
                        if (Math.sqrt((Math.pow((Map[i].x - Map[j].x),2) + Math.pow((Map[i].y - Map[j].y),2))) < 120){
                            inbounds = true;
                        }
                    } else {
                        if (Math.sqrt((Math.pow((Map[i].x - Map[j].x),2) + Math.pow((Map[i].y - Map[j].y),2))) < 30){
                            inbounds = true;
                        }
                    }
                }

                randomx = new Random();
                randomy = new Random();
            }

        }

        //Stores distance and id of each node
        //Finds 3 closest nodes to every node, and adds their ids to connectnodes
        Double[][] Distance = new Double[20][2];
        for (int i = 0; i < Map.length; i++){
            for (int j = 0; j < Map.length; j++){
                //Stores id of node at j1, distance at j2
                Distance[j][1] = Double.valueOf(j);
                Distance[j][0] = Math.sqrt((Math.pow((Map[i].x - Map[j].x),2) + Math.pow((Map[i].y - Map[j].y),2)));
                //Makes sure nodes are never connected to themselves, because of distance
                if (i == j){
                    Distance[j][0] = 9999.9;    //Never in bound
                }
            }

            //Sort node distance
            Arrays.sort(Distance, new Comparator<Double[]>() {
                @Override
                public int compare(final Double[] entry1, final Double[] entry2) {
                    final Double column1 = entry1[0];
                    final Double column2 = entry2[0];
                    if (column1 > column2){
                        return 1;
                    } else if (column1 == column2){
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });
            //Adds to the nearest 3 nodes to connectnodes
            Map[i].connectnodes = new ArrayList<Integer>();
            for (int k = 0; k < 3; k++){
                //Connects them if they aren't both colleges
                if (Map[i].nodeType != "College" || Map[k].nodeType != "College"){
                    Map[i].connectnodes.add(Distance[k][1].intValue());
                }
            }


        }

        //Make all nodes are connected both ways
        for (int i = 0; i < Map.length; i++){
            for (int j = 0; j < Map.length; j++){
                //Does another node contain the current nodes id, but isn't recorded
                if (Map[j].connectnodes.contains(Map[i].id) && (!Map[i].connectnodes.contains(Map[j].id))){
                    Map[i].connectnodes.add(Map[j].id);
                }
            }

        }

        //Test printing
//        for (int i = 0; i < Map.length; i++){
//            for (int j = 0; j < Map.length; j++){
//                System.out.print(Distance[j][0]);
//                System.out.println(" " + Distance[j][1]);
//            }
//            System.out.println(Map[i].connectnodes);
//            System.out.println("NEW ARRAY");
//        }

        //Returns completed map of nodes
        return Map;
    }



    //Sets encounterchance
    public void setEncounterchance(int encounterchance){
        this.encounterchance = encounterchance;
    }

    //Gets encounterchance
    public int getEncounterchance(){
        return encounterchance;
    }

    //Sets nodeType
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    //Gets nodeType
    public String getNodeType() {
        return nodeType;
    }

    //Sets weather
    public void setWeather(String weather) {
        this.weather = weather;
    }

    //Gets weather
    public String getWeather() {
        return weather;
    }

    //Returns x value
    public Integer getX(){
        return x;
    }

    //Returns y value
    public Integer getY(){
        return y;
    }

    //Returns ID value
    public Integer getId(){
        return id;
    }

    //Returns all nodes connected to current node
    public ArrayList<Integer> getConnectnodes(){
        return this.connectnodes;
    }

}

class CollegeNode extends Node{

    private String name;
    private Card card;
    private int CollegeStatus; //1 = Friendly, 0 = Hostile

    public CollegeNode(int id, int x, int y, String name, int status, Card card){
        super( id,  x,  y);
        this.name = name;
        this.CollegeStatus = status;
        this.card = card;
    }

    public Card buyCard(
    ){
        //return card ID
        return card;
    }

    public void giveQuest(
    ){
        //return Quest
    }

    public void attackCollege(){
        // BattleMode battleMode = new BattleMode();
    }

    public String getCollegeName(){
        return this.name;
    }

    public void setCollegeName(String name){
        this.name = name;
    }

    public int getCollegeStatus(){
        return this.CollegeStatus;
    }

}

class DepartmentNode extends Node{

    private String name;
    private  int departmentStatus;

    public DepartmentNode(int id, int x, int y, String name, int status){
        super( id,  x,  y);
        this.name = name;
        this.departmentStatus = status;
    }

    public int[] buyUpgrade(){
        int[] upgrade = new int[2];
        if (this.name == "Comp Sci"){
            upgrade[0] = 1;
            upgrade[1] = 10;
            return upgrade;
        } else {
            upgrade[0] = 2;
            upgrade[1] = 20;
            return upgrade;
        }
    }

    public void attackCollege(){
        // BattleMode battleMode = new BattleMode();
    }


    public String getDepartmentName(){
        return this.name;
    }

    public void setDepartmentName(String name){
        this.name = name;
    }

    public int getDepartmentStatus(){
        return this.departmentStatus;
    }
}