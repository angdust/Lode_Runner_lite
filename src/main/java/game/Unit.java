package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Unit extends Pane {
    Image unitImage;
    ImageView unit;
    boolean ladder = false;
    boolean commonBlock = false;

    public Unit(UnitType unitType, int x, int y) {
        setTranslateX(x);
        setTranslateY(y);
        boolean coin = false;
        switch (unitType) {
            case BLOCK:
                unitImage = new Image("block.png", 50, 50, false, true);
                unit = new ImageView(unitImage);
                commonBlock = true;
                break;
            case LADDER:
                unitImage = new Image("ladder.png", 50, 50, false, true);
                unit = new ImageView(unitImage);
                ladder = true;
                break;
            case COIN:
                unitImage = new Image("coin.png", 50, 50, false, true);
                unit = new ImageView(unitImage);
                unit.setFitHeight(30);
                unit.setFitWidth(30);
                setTranslateX(x+10);
                setTranslateY(y+20);
                coin = true;
                break;
            case ROPE:
                unitImage = new Image("rope1.png", 50, 12, false, true);
                unit = new ImageView(unitImage);
                setTranslateY(y+38);
                ladder = true;
                break;
            case MAINBLOCK:
                unitImage = new Image("block.png", 50, 50, false, true);
                unit = new ImageView(unitImage);
                break;

        }
        getChildren().add(unit);
        if (!ladder) {
            Main.textures.add(this);
        } else {
            Main.ladders.add(this);
        }
        if (coin) {
            Main.coins.add(this);
        }

        Main.game.getChildren().add(this);
    }

    public enum UnitType {
        BLOCK, LADDER, COIN, ROPE, MAINBLOCK
    }
}



