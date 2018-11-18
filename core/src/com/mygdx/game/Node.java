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


public class Node {
    private int id;
    private int[] conectnodes;
    private String nodeType;
    private String weather;
    private boolean visibility;
    private String[] encounters;
    private int encounterchance;
    private int x;
    private int y;
}

public void applyEffect();

public String[] getCoord(){
    String xy[] = {x,y};
    return xy;
}

public void giveEncounter();