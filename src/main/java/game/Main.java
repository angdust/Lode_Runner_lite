package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {
    public static final int unitSize = 50;
    public static final int widthOfMovingObject = 30;
    public static final int heightOfMovingObject = 40;
    public static Pane app = new Pane();
    public static Pane game = new Pane();
    public static ArrayList<Unit> textures = new ArrayList<>();
    public static ArrayList<Unit> coins = new ArrayList<>();
    public static ArrayList<Unit> ladders = new ArrayList<>();
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static AnimationTimer timer;
    public static int widthOfLevel;
    public static int score = 0;
    public static int maxScore;
    public static int life = 3;
    public static int counterOfTap = 0;
    public Hero tentacleHero;
    private ImageView life1 = new ImageView(new Image("heart.png"));
    private ImageView life2 = new ImageView(new Image("heart.png"));
    private ImageView life3 = new ImageView(new Image("heart.png"));
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();

    public static void main(String[] args) {
        launch(args);
    }

    private void drawTheMap() {
        Image backgroundImage = new Image("background.png");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitHeight(855);
        backgroundImageView.setFitWidth(1500);
        widthOfLevel = DataOfLevel.levelData[0].length() * unitSize;
        for (int i = 0; i < DataOfLevel.levelData.length; i++) {
            String string = DataOfLevel.levelData[i];
            for (int j = 0; j < string.length(); j++) {
                switch (string.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Unit block = new Unit(Unit.UnitType.BLOCK, j * unitSize, i * unitSize);
                        break;
                    case '2':
                        Unit ladder = new Unit(Unit.UnitType.LADDER, j * unitSize, i * unitSize);
                        break;
                    case '3':
                        Unit coin = new Unit(Unit.UnitType.COIN, j * unitSize, i * unitSize);
                        break;
                    case '4':
                        Unit rope = new Unit(Unit.UnitType.ROPE, j * unitSize, i * unitSize);
                        break;
                    case '*':
                        Unit mainBlock = new Unit(Unit.UnitType.MAINBLOCK, j * unitSize, i * unitSize);
                        break;
                }
            }
        }

        life1.setFitWidth(50);
        life1.setFitHeight(50);
        life2.setFitWidth(50);
        life2.setFitHeight(50);
        life3.setFitWidth(50);
        life3.setFitHeight(50);
        ImageView coin = new ImageView(new Image("coin.png"));
        coin.setFitHeight(50);
        coin.setFitWidth(50);
        ImageView blockDestroyer = new ImageView(new Image("hA.png"));
        blockDestroyer.setFitHeight(50);
        blockDestroyer.setFitWidth(50);
        Label possibilityForDestroy = new Label("можно удалить 3 блока");
        Label ruleForDestroy = new Label("(удаление блока происходит слева и справ от героя" +
                " при помощи кливиш Z и X)");
        game.getChildren().addAll(life1, life2, life3, coin, blockDestroyer, possibilityForDestroy, ruleForDestroy);
        life1.setTranslateX(10);
        life1.setTranslateY(801);
        life2.setTranslateX(70);
        life2.setTranslateY(801);
        life3.setTranslateX(130);
        life3.setTranslateY(801);
        coin.setTranslateX(500);
        coin.setTranslateY(801);
        blockDestroyer.setTranslateX(900);
        blockDestroyer.setTranslateY(801);
        possibilityForDestroy.setTranslateX(1030);
        possibilityForDestroy.setTranslateY(790);
        possibilityForDestroy.setFont(new Font(30));
        ruleForDestroy.setTranslateX(960);
        ruleForDestroy.setTranslateY(830);
        ruleForDestroy.setFont(new Font(13));
        tentacleHero = new Hero();
        tentacleHero.setTranslateX(5);
        tentacleHero.setTranslateY(710);
        game.getChildren().add(tentacleHero);
        maxScore = coins.size();
        tentacleHero.getCounter(score);
        Enemy enemy1 = new Enemy(10, 260);
        enemy1.setTranslateX(1460);
        enemy1.setTranslateY(710);
        enemy1.transition.play();
        enemies.add(enemy1);
        Enemy enemy2 = new Enemy(5, 260);
        enemy2.setTranslateX(510);
        enemy2.setTranslateY(610);
        enemy2.transition.play();
        enemies.add(enemy2);
        Enemy enemy3 = new Enemy(5, 1110);
        enemy3.setTranslateX(1410);
        enemy3.setTranslateY(610);
        enemy3.transition.play();
        enemies.add(enemy3);
        Enemy enemy4 = new Enemy(10, 10);
        enemy4.setTranslateX(1010);
        enemy4.setTranslateY(460);
        enemy4.transition.play();
        enemies.add(enemy4);
        Enemy enemy5 = new Enemy(9, 710);
        enemy5.setTranslateX(1460);
        enemy5.setTranslateY(310);
        enemy5.transition.play();
        enemies.add(enemy5);
        Enemy enemy6 = new Enemy(7, 10);
        enemy6.setTranslateX(360);
        enemy6.setTranslateY(310);
        enemy6.transition.play();
        enemies.add(enemy6);
        Enemy enemy7 = new Enemy(8, 910);
        enemy7.setTranslateX(1460);
        enemy7.setTranslateY(160);
        enemy7.transition.play();
        enemies.add(enemy7);
        Enemy enemy8 = new Enemy(9, 10);
        enemy8.setTranslateX(710);
        enemy8.setTranslateY(60);
        enemy8.transition.play();
        enemies.add(enemy8);
        game.getChildren().addAll(enemy1, enemy2, enemy3, enemy4, enemy5, enemy6, enemy7, enemy8);
        app.getChildren().addAll(backgroundImageView, game);
    }

    private void update() throws InterruptedException {
        if (isPressed(KeyCode.RIGHT) && tentacleHero.getTranslateX() + 40 <= widthOfLevel - 5) {
            tentacleHero.setScaleX(1);
            tentacleHero.animation.play();
            tentacleHero.moveX(2);
        }
        if (isPressed(KeyCode.LEFT) && tentacleHero.getTranslateX() >= 5) {
            tentacleHero.setScaleX(-1);
            tentacleHero.animation.play();
            tentacleHero.moveX(-2);
        }
        if (isPressed(KeyCode.UP) && tentacleHero.getTranslateY() >= 5 && tentacleHero.isOnLadder()) {
            tentacleHero.setScaleY(1);
            tentacleHero.animation.play();
            tentacleHero.moveY(-2);
        }
        if (isPressed(KeyCode.DOWN) && tentacleHero.isOnLadder()) {
            tentacleHero.animation.play();
            tentacleHero.moveY(2);
        }
        if (!tentacleHero.isOnLadder()) {
            tentacleHero.moveY(1);
        }
        if (isPressed(KeyCode.X)) {
            tentacleHero.setScaleX(-1);
            DeadBlock process = new DeadBlock();
            process.findTheRightDeadBlock(tentacleHero);
            process.delete();
        }
        if (isPressed(KeyCode.Z)) {
            DeadBlock process = new DeadBlock();
            process.findTheLeftDeadBlock(tentacleHero);
            process.delete();
        }
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    public void makeAlertWindow(String string) {
        Stage alertWindow = new Stage();
        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle("Конец игры");
        alertWindow.setMinHeight(200);
        alertWindow.setMinWidth(300);
        Label info = new Label(string);
        Button closeButton = new Button("Закрыть");
        closeButton.setOnAction(e -> System.exit(0));
        VBox alert = new VBox();
        alert.getChildren().addAll(info, closeButton);
        alert.setAlignment(Pos.CENTER);
        Scene scene = new Scene(alert);
        alertWindow.setScene(scene);
        Main.timer.stop();
        alertWindow.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        drawTheMap();
        Scene scene = new Scene(app, 1500, 855, Color.DIMGRAY);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> {
            keys.put(event.getCode(), false);
            tentacleHero.animation.stop();
        });
        primaryStage.setTitle("cover lode runner lite");
        primaryStage.setScene(scene);
        primaryStage.show();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    switch (life) {
                        case 2:
                            game.getChildren().remove(life3);
                            break;
                        case 1:
                            game.getChildren().remove(life2);
                            break;
                        case 0:
                            game.getChildren().remove(life1);
                            makeAlertWindow("Ты проиграл!");
                            break;
                    }
                    if (Main.score == Main.maxScore) {
                        makeAlertWindow("Ты победил!");
                    }
                    update();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }
}
