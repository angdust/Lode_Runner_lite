package game;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;




public class HEAnimation extends Transition {
    private final javafx.scene.image.ImageView imageView;
    private final int frames;
    private final int width;
    private final int height;
    private int xOffset;
    private int yOffset;

    public HEAnimation(ImageView imageView, Duration duration, int frames,
                         int xOffset, int yOffset, int width, int height) {
        this.imageView = imageView;
        this.frames = frames;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
        setCycleDuration(duration);
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
        this.imageView.setViewport(new Rectangle2D(xOffset, yOffset, width, height));

    }

    public void setxOffset(int x) {
        this.xOffset = x;
    }

    public void setyOffset(int y) {
        this.yOffset = y;
    }

    @Override
    protected void interpolate(double frac) {
        final int frameNumber = Math.min((int) Math.floor(frames * frac), frames - 1);
        final int x = (frameNumber % frames) * width + xOffset;
        final int y = (frameNumber / frames) * height + yOffset;
        imageView.setViewport(new Rectangle2D(x, y, width, height));

    }
}
