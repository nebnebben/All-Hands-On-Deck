//id                int
//connected nodes   list
//nodeType          String
//weather           String
//Score             Integer
//Visibility        Boolean
//encounterChance   list
//x                 int
//y                 int
//giveEncounter()   encounter
//getCoord()        List<integer>
//applyEffect()     Void
import java.util.ArrayList;
import java.util.Random;


public class Node {
    private int id;                 //Unique node identifier
    private int[] conectnodes;      //Other nodes that are attached
    private String nodeType;        //Type of the node
    private String weather;         //What weather the node has
    private boolean visibility;     //What the visibility is?
    private String[] encounters;    //The possible encounters
    private int encounterchance;    //Chance of an encounter
    private int x;                  //x location of node
    private int y;                  //y location of node
}

public Node(int id, int x, int y){
    this.id = id;
    this.x = x;
    this.y = y;
}


public void applyEffect();          //What effect the node has

    //Gets co-ordinates of the node
public String[] getCoord(){
    String xy[] = {x,y};
    return xy;
}

//Randomly decides on which encounter to selectz
public void giveEncounter() {
    Random chance = new Random();
    int n = chance.nextInt(100)+1;
    if (n > encounterchance) {
        chance = new Random();
        int n = chance.nextInt(encounters.size());
        //encounter[n]
    } else {
        //no encounter
    }
}

public void nodeMapGenerator(){
    Node[] Map = new Node[20];
    Random randomx = new Random();
    Random randomy = new Random();
    for (int i = 0; i < Map.length(); i++){
        
    }
    //Generate list of nodes
    //each node has (initially) id, x, y - add everything else later
    //id = 0,1,2...
    //x+y = random, regenerate if within z radius of other nodes
    //or generate nodes recursively, each node is within a to b distance from each previous node
    //can only generate front 270 degrees?
    //Attach each node to 3 nearest nodes
    //distance from each node to another = sqrt((x2-x1)*2 + (y2-y1)*2)

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

    //id, x, y

