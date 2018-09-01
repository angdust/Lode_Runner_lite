package game;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Enemy extends Pane {
    public HEAnimation animation;
    Image enemyImage = new Image("enemy texture 2.png");
    ImageView enemyIV = new ImageView(enemyImage);
    TranslateTransition transition;
    int frames = 2;
    int xOffset = 0;
    int yOffset = 0;
    int width = 105;
    int height = 140;

    public Enemy(int duration, int xMovement) {
        enemyIV.setFitWidth(Main.widthOfMovingObject);
        enemyIV.setFitHeight(Main.heightOfMovingObject);
        animation = new HEAnimation(this.enemyIV, Duration.millis(200), frames, xOffset, yOffset, width, height);
        transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(duration));
        transition.setToX(xMovement);
        transition.setAutoReverse(true);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setNode(this);
        animation.play();
        getChildren().addAll(this.enemyIV);
    }
}
