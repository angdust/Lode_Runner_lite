package game;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class DeadBlock {
    public Label labelForCounterOfTap;
    Unit thisUnit;
    Unit unit;

    public DeadBlock() {
    }

    public void delete() throws InterruptedException {
        if (unit != null) {
            Main.counterOfTap++;
            Main.textures.remove(unit);
            Main.game.getChildren().remove(unit);
            this.sleep1();
        }
    }

    private void sleep1() {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void findTheRightDeadBlock(Hero hero) throws InterruptedException {
        for (int i = 0; i < Main.textures.size(); i++) {
            if (hero.getBoundsInParent().intersects(Main.textures.get(i).getBoundsInParent())) {
                if (Main.textures.get(i).commonBlock && Main.counterOfTap < 3) {
                    thisUnit = Main.textures.get(i);
                    unit = Main.textures.get(i + 1);
                }
            }
        }
    }

    public void findTheLeftDeadBlock(Hero hero) throws InterruptedException {
        for (int i = 0; i < Main.textures.size(); i++) {
            if (hero.getBoundsInParent().intersects(Main.textures.get(i).getBoundsInParent())) {
                if (Main.textures.get(i).commonBlock && Main.counterOfTap < 3) {
                    thisUnit = Main.textures.get(i);
                    unit = Main.textures.get(i - 1);
                }
            }
        }
    }
}
