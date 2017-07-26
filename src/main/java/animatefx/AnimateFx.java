package animatefx;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Assorted animations on a JavaFx Node.
 */
public class AnimateFx implements EventHandler<ActionEvent> {

    private static Set<Node> animatingNodes = new HashSet<Node>();

    public static void fadeOutLeft(Node node, long millis, int distance, EventHandler<ActionEvent> onFinished) {
        fadeOutRight(node, millis, -distance, onFinished);
    }

    public static void fadeOutRight(Node node, long millis, int distance, EventHandler<ActionEvent> onFinished) {
        animate(
                node,
                millis,
                animation -> animation
                        .fadeTo(0, 1, 0)
                        .parallel()
                        .moveX(distance),
                onFinished);
    }

    public static void flash(Node node, long millis, EventHandler<ActionEvent> onFinished) {
        node.setOpacity(1);
        animate(
                node,
                millis,
                animation -> animation
                        .fadeTo(0, 0.25, 0)
                        .fadeTo(0.25, 0.5, 1)
                        .fadeTo(0.5, 0.75, 0)
                        .fadeTo(0.75, 1, 1),
                onFinished);
    }

    public static void pulse(Node node, long millis, double scale, EventHandler<ActionEvent> onFinished) {
        animate(
                node,
                millis,
                animation -> animation
                        .scaleXY(0, 0.5, scale, scale)
                        .scaleXY(0.5, 1, 1, 1),
                onFinished);
    }

    public static void rubberBand(Node node, long millis, EventHandler<ActionEvent> onFinished) {
        animate(node,
                millis,
                animation -> animation
                        .scaleXY(0, 0.3, 1.25, 0.75)
                        .scaleXY(0.3, 0.4, 0.75, 1.25)
                        .scaleXY(0.4, 0.5, 1.15, 0.85)
                        .scaleXY(0.5, 0.7, 0.95, 1.05)
                        .scaleXY(0.7, 1.0, 1.0, 1.0),
                onFinished);
    }

    public static void zoomIn(Node node, long millis, EventHandler<ActionEvent> onFinished) {
        double targetScaleX = node.getScaleX();
        double targetScaleY = node.getScaleY();
        node.setScaleX(0.1);
        node.setScaleY(0.1);
        animate(
                node,
                millis,
                animation -> animation.scaleXY(0, 1, targetScaleX / 0.1, targetScaleY / 0.1),
                onFinished);
    }

    private static void animate(Node node, long millis, Consumer<? super AnimateFx> action, EventHandler<ActionEvent> onFinished) {
        synchronized (animatingNodes) {
            if (animatingNodes.contains(node)) {
                return;
            }
            animatingNodes.add(node);
        }
        AnimateFx animations = new AnimateFx(node, millis, onFinished);
        action.accept(animations);
        animations.start();
    }

    private final Node node;

    private final EventHandler<ActionEvent> customOnFinished;

    private final ParallelTransition parallel;

    private SequentialTransition sequence;

    private final long duration;

    private AnimateFx(Node node, long duration, EventHandler<ActionEvent> customOnFinished) {
        this.node = node;
        this.duration = duration;
        this.customOnFinished = customOnFinished;
        this.parallel = new ParallelTransition(node);
        this.sequence = new SequentialTransition();
        this.parallel.setOnFinished(this);
    }

    public AnimateFx fadeTo(double startTimeFraction, double endTimeFraction, float opacity) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(duration * endTimeFraction - duration * startTimeFraction));
        fadeTransition.setToValue(opacity);
        sequence.getChildren().add(fadeTransition);
        return this;
    }

    public AnimateFx moveX(int distance) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(duration));
        translateTransition.setByX(distance);
        sequence.getChildren().add(translateTransition);
        return this;
    }

    public AnimateFx scaleX(double startTimeFraction, double endTimeFraction, double scaleX) {
        double unitScaleX = node.getScaleX();
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(duration * endTimeFraction - duration * startTimeFraction));
        scaleTransition.setToX(unitScaleX * scaleX);
        sequence.getChildren().add(scaleTransition);
        return this;
    }

    public AnimateFx scaleY(double startTimeFraction, double endTimeFraction, double scaleY) {
        double unitScaleY = node.getScaleY();
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(duration * endTimeFraction - duration * startTimeFraction));
        scaleTransition.setToY(unitScaleY * scaleY);
        sequence.getChildren().add(scaleTransition);
        return this;
    }

    public AnimateFx scaleXY(double startTimeFraction, double endTimeFraction, double scaleX, double scaleY) {
        double unitScaleX = node.getScaleX();
        double unitScaleY = node.getScaleY();
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(duration * endTimeFraction - duration * startTimeFraction));
        scaleTransition.setToX(unitScaleX * scaleX);
        scaleTransition.setToY(unitScaleY * scaleY);
        sequence.getChildren().add(scaleTransition);
        return this;
    }

    private AnimateFx parallel() {
        commitSequence();
        sequence = new SequentialTransition();
        return this;
    }

    private void start() {
        commitSequence();
        parallel.play();
    }

    private void commitSequence() {
        if (sequence.getChildren().isEmpty()) {
            return;
        }
        parallel.getChildren().add(sequence);
    }

    @Override
    public void handle(ActionEvent event) {
        synchronized (animatingNodes) {
            animatingNodes.remove(this.node);
        }
        if (customOnFinished != null) {
            customOnFinished.handle(event);
        }
    }
}
