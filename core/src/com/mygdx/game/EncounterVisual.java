package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class EncounterVisual extends ScreenAdapter {
    private Game game;
    private ScreenAdapter parent;
    private Encounter encounter;

    private Label[] effectLabels;
    private Label.LabelStyle resourceStyle;
    private Stage encounterStage;

    public EncounterVisual(Game game, ScreenAdapter parent, Encounter encounter){
        this.game = game;
        this.parent = parent;
        this.encounter = encounter;
    }

}
