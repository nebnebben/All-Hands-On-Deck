package com.mygdx.game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Testing {


    @Test

    //Checks the node map is properly generated and colleges are implemented
    public void testMapColleges(){
        Node[] Mapp = Node.nodeMapGenerator();
        assertEquals( ((CollegeNode) Mapp[1]).getCollegeName(), "James");
    }

    @Test
    //Checks the node map is properly generated and colleges are implemented
    public void testMapDepartments() {
        Node[] Mapp = Node.nodeMapGenerator();
        assertEquals( ((DepartmentNode) Mapp[3]).getDepartmentName(), "Comp Sci");
    }



    //Ship Testing

    @Test
    //Ship health test
    public void testShipHealth(){
        Ship tship = new Ship();
        assertEquals(tship.getTotalHealth(),50);
    }

    @Test
    //Ship mana test
    public void testShipMana(){
        Ship tship = new Ship();
        assertEquals(tship.getTotalMana(),20);
    }


    @Test
    //Ship mana regen
    public void testShipManaRegen(){
        Ship tship = new Ship();
        assertEquals(tship.getManaRegenRate(),200);
    }


    @Test
    //Ship Points
    public void testShipPoints(){
        Ship tship = new Ship();
        assertEquals(tship.getPointsWorth(),30);
    }

    @Test
    //Ship goldAmount
    public void testShipGold(){
        Ship tship = new Ship();
        assertEquals(tship.getGoldAmount(),50);
    }

    @Test
    //ShipNameConstructor
    public void testShipName(){
        Ship tship = new Ship();
        assertEquals(tship.getName(), "The Pigeons Droppings");
    }

    //Player tests

    @Test
    //PlayerName
    public void testPlayerName(){
        Player Ben = new Player("Ben","James");
        assertEquals(Ben.getName(), "Ben");
    }

    @Test
    //PlayerCollege
    public void testPlayerCollege(){
        Player Ben = new Player("Ben","James");
        assertEquals(Ben.getCollege(), "James");
    }

    //Card tests

    @Test
    //CardManaCost
    public void testCardManaCost(){
        Card tCard = new Card("fast attack", "attacks fast", 10, 0, 2, "A9");
        assertEquals(tCard.getManaCost(),2);
    }

    @Test
    //CardGoldCost
    public  void testCardGoldCost(){
        Card tCard = new Card("fast attack", "attacks fast", 10, 0, 2, "A9");
        assertEquals(tCard.getGoldCost(),0);
    }

    @Test
    //CardEffect
    public  void testCardEffect(){
        Card tCard = new Card("fast attack", "attacks fast", 10, 0, 2, "A9");
        assertEquals(tCard.getEffect(),"A9");
    }

    //Encounter testing

    @Test
    //Encounter Score
    public void testEncounterScore(){
        Encounter tEncounter = new Encounter(new String[]{"S-L-2","B-50-10-100-50-50-pot,pot,0,0,1,A2"},
                "You encounter an enemy ship, do you run away or choose to fight",
                10);
        assertEquals(tEncounter.getScore(), 10);
    }

    @Test
    //Encounter Effects
    public void testEncounterEffects(){
        Encounter tEncounter = new Encounter(new String[]{"S-L-2","B-50-10-100-50-50-pot,pot,0,0,1,A2"},
                "You encounter an enemy ship, do you run away or choose to fight",
                10);
       assertEquals(tEncounter.getEffects()[0], "S-L-2");
       assertEquals(tEncounter.getEffects()[1], "B-50-10-100-50-50-pot,pot,0,0,1,A2");


    }

    @Test
    //Encounter Description
    public void testEncounterDescription(){
        Encounter tEncounter = new Encounter(new String[]{"S-L-2","B-50-10-100-50-50-pot,pot,0,0,1,A2"},
                "You encounter an enemy ship, do you run away or choose to fight",
                10);
        assertEquals(tEncounter.getDescription(), "You encounter an enemy ship, do you run away or choose to fight");
    }

    //Battle Tests

    @Test
    //BattleModeTestDamage
    public void testBattleDamage(){
        Ship tship1 = new Ship();
        Ship tship2 = new Ship();
        BattleMode tBattle = new BattleMode(tship1,tship2, 100);
        tBattle.updateHealth("enemy",-10,true);
        assertEquals(tBattle.getShipHealthPercentage("enemy"), 80);

    }

    @Test
    //Battle ends when a ship dies
    public void testGameOver(){
        Ship tship1 = new Ship();
        Ship tship2 = new Ship();
        BattleMode tBattle = new BattleMode(tship1,tship2, 100);
        tBattle.updateHealth("enemy",-50,true);
        assertEquals(tBattle.gameIsOver(), true);
    }

    @Test
    //Mana can be correctly changed
    public void testManaCost(){
        Ship tship1 = new Ship();
        Ship tship2 = new Ship();
        BattleMode tBattle = new BattleMode(tship1,tship2, 100);
        tBattle.updateMana("player", 5); //Start battle at 0 mana
       // tBattle.playerPlayCard(0);
        assertEquals(tBattle.getMana("player"),5);
    }

    @Test
    //Playing a card costs mana
    public void testPlayCard(){
        Ship tship1 = new Ship();
        Ship tship2 = new Ship();
        BattleMode tBattle = new BattleMode(tship1,tship2, 100);
        tBattle.updateMana("player", 15); //Start battle at 0 mana
        tBattle.applyCard(tship1.getDeck().get(0),"player");
        assertEquals(tBattle.getMana("player"),13);
        assertEquals(tBattle.getShipHealthPercentage("enemy"), 82);
    }


    //Other Game Tests

    @Test
    //Node traversal
    public void testNodeTraverse(){
        GameLogic gameLogic = new GameLogic(5,2,"Alcuin","p1");
        Node move = new Node(0,0,0);
        move = gameLogic.getNodeList()[gameLogic.getNeighborNodes().get(0)];
        gameLogic.traverseNode(gameLogic.getNeighborNodes().get(0));
        assertEquals(move.getId(),gameLogic.getCurrentNode());
    }

    @Test
    //Game Loss (run out of supplies)
    public void testGameLossSupplies(){
        GameLogic gameLogic = new GameLogic(5,2,"Alcuin","p1");
        gameLogic.setCurrentSupplies(-1);
        assertEquals(gameLogic.checkLoss(),Boolean.TRUE);
    }

    @Test
    //Game Loss (run out of health)
    public void testGameLossHealth(){
        GameLogic gameLogic = new GameLogic(5,2,"Alcuin","p1");
        gameLogic.getPlayer().getPlayerShip().setIsDead(); //Happens in out of health
        assertEquals(gameLogic.checkLoss(),Boolean.TRUE);
    }

    @Test
    //Game Win + adding score + objectives
    public void testGameWin(){
        GameLogic gameLogic = new GameLogic(5,2,"TestCollege","p1");
        gameLogic.addScore(150);
        assertEquals(gameLogic.checkWin(),Boolean.TRUE);
    }


}

