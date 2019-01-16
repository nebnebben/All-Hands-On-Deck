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
            encounterEffects[1] = "S-L-3";
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
            Map[i] = new Node(i,0,0);
            inbounds = true;
            //Loop to check no node is within x distance of each other
            while(inbounds == true){
                inbounds = false;
                //Random co-ordinates generating
                Map[i].x = randomx.nextInt(200);
                Map[i].y = randomx.nextInt(200);

                //Check to see whether the co-ordinates are within x of another node, if yes loop
                for (int j = 0; j < i; j++){
                    if (Math.sqrt((Math.pow((Map[i].x - Map[j].x),2) + Math.pow((Map[i].y - Map[j].y),2))) < 30){
                        inbounds = true;
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
                Map[i].connectnodes.add(Distance[k][1].intValue());
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

//ArrayList<Integer> list=new ArrayList<Integer>();
}

class CollegeNode extends Node{

    private String name;
    private int CollegeStatus;

    public CollegeNode(int id, int x, int y){
        super( id,  x,  y);
    }

    public void buyCard(int CardID){}

    public void giveQuest(){}

    public void attackCollege(){}


}

class DepartmentNode extends Node{

    private String name;
    private  int departmentStatus;

    public DepartmentNode(int id, int x, int y){
        super( id,  x,  y);
    }

    public void buyUpgrade(int choice){}

    public void attackCollege(){}

    public void playMinigame(){}
}