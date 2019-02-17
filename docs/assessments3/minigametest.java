package com.rear_admirals.york_pirates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MinigameTest {


    //To see whether getting random dice works


    //To see whether checking dice scores works correctly and whether you can win
    @Test
    public void comparisonTest(){
        Minigame ceelo = new Minigame();
        int[] dice1 = {4,5,6};
        int[] dice2 = {1,2,3};
        String[] Scores = ceelo.comparison(dice1,dice2);
        assertEquals("456",Scores[0]);
        assertEquals("123",Scores[1]);
        assertEquals("Win",Scores[2]);
    }

    //To  whether you can lose
    @Test
    public void comparisonLoseTest(){
        Minigame ceelo = new Minigame();
        int[] dice1 = {1,2,3};
        int[] dice2 = {4,5,6};
        String[] Scores = ceelo.comparison(dice1,dice2);
        assertEquals("123",Scores[0]);
        assertEquals("456",Scores[1]);
        assertEquals("Loss",Scores[2]);
    }

    //To  whether you can lose
    @Test
    public void comparisonDrawTest(){
        Minigame ceelo = new Minigame();
        int[] dice1 = {5,5,5};
        int[] dice2 = {5,5,5};
        String[] Scores = ceelo.comparison(dice1,dice2);
        assertEquals("Trip",Scores[0]);
        assertEquals("Trip",Scores[1]);
        assertEquals("Draw",Scores[2]);
    }

    //Check the dice created are within correct bounds
    @Test
    public void getRandomDiceTest(){
        Minigame ceelo = new Minigame();

        int[] dice1 = ceelo.randomdice();
        int[] dice2 = ceelo.randomdice();
        for (int i = 0; i < 3; i ++){
            if (dice1[i] > 6 || dice1[i] < 1){
                fail();
            }
            if (dice2[i] > 6 || dice2[i] < 1){
                fail();
            }
        }

    }

    //Check to see when the game is played, you win, lose or draw
    @Test
    public void playGameTest(){
        Minigame ceelo = new Minigame();
        String gameState = ceelo.playGame();
        if (gameState != "Won" && gameState != "Lost" && gameState != "playing"){
            fail();
        }
    }

}