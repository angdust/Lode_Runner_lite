package game;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class Hero extends Pane {
    public HEAnimation animation;
    public Point2D movement = new Point2D(0, 0);
    public Label counter;
    Image heroImage = new Image("hero texture 2.png");
    ImageView heroIV = new ImageView(heroImage);
    Unit deadCoin = null;
    int frames = 2;
    int xOffset = 0;
    int yOffset = 0;
    int width = 63;
    int height = 84;



    public Hero() {
        heroIV.setFitWidth(Main.widthOfMovingObject);
        heroIV.setFitHeight(Main.heightOfMovingObject);
        heroIV.setViewport(new Rectangle2D(xOffset, yOffset, width, height));
        animation = new HEAnimation(this.heroIV, Duration.millis(200), frames, xOffset, yOffset, width, height);
        getChildren().addAll(this.heroIV);
    }


    public void moveX(int value) {
        boolean movingRight = (value > 0);
        for (int i = 0; i < Math.abs(value); i++) {
            for (Unit texture : Main.textures) {
                if (this.getBoundsInParent().intersects(texture.getBoundsInParent())) {
                    if (movingRight) {
                        if (this.getTranslateX() + Main.widthOfMovingObject == texture.getTranslateX()) {
                            this.setTranslateX(this.getTranslateX() - 1);
                            return;
                        }
                    } else {
                        if (this.getTranslateX() == texture.getTranslateX() + Main.unitSize) {
                            this.setTranslateX(this.getTranslateX() + 1);
                            return;
                        }
                    }
                }
            }
            this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
            isCoinPickedUp();
            isEnemyAttacking();
        }
    }

    public void moveY(int value) {
        boolean movingDown = (value > 0);
        for (int i = 0; i < Math.abs(value); i++) {
            for (Unit texture : Main.textures) {
                if (getBoundsInParent().intersects(texture.getBoundsInParent())) {
                    if (movingDown) {
                        if (this.getTranslateY() + Main.heightOfMovingObject == texture.getTranslateY()) {
                            this.setTranslateY(this.getTranslateY() - 1);
                            return;
                        }
                    } else {
                        if (this.getTranslateY() == texture.getTranslateY() + Main.unitSize) {
                            this.setTranslateY(this.getTranslateY() + 1);
                            movement = new Point2D(0, 0);
                            return;
                        }
                    }
                }
            }
            this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
            isCoinPickedUp();
            isEnemyAttacking();
        }
    }

    public void isCoinPickedUp() {
        Main.coins.forEach((coin) -> {
            if (this.getBoundsInParent().intersects(coin.getBoundsInParent())) {
                deadCoin = coin;
                Main.score++;
                getCounter(Main.score);
            }
        });
        Main.coins.remove(deadCoin);
        Main.textures.remove(deadCoin);
        Main.game.getChildren().remove(deadCoin);
    }

    public void isEnemyAttacking () {
        Main.enemies.forEach((enemy) -> {
            if (this.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                Main.life --;
                this.setTranslateX(5);
                this.setTranslateY(710);
            }
        });
    }

    public boolean isOnLadder() throws InterruptedException {
        for (Unit texture : Main.ladders) {
            if (getBoundsInParent().intersects(texture.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    public void getCounter(int score) {
        String scoreString = score + "/" + Main.maxScore;
        Label scoreLabel = new Label(scoreString);
        scoreLabel.setTranslateX(560);
        scoreLabel.setTranslateY(794);
        scoreLabel.setFont(new Font(45));
        Main.game.getChildren().remove(counter);
        counter = scoreLabel;
        Main.game.getChildren().add(counter);
    }
}