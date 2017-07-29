package animatefx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AnimateFxApplication extends Application {

    private StackPane animationNodeContainer = new StackPane();

    private Node animationNode = new Button("AnimateFx");

    private final EventHandler<ActionEvent> onFinished = actionEvent -> addAnimationNode();

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage stage) {
        stage.setTitle("AnimateFx Application");

        addAnimationNode();

        Button btnBounceIn = new Button("bounceIn");
        btnBounceIn.setOnAction(e -> AnimateFx.bounceIn(animationNode, 500, onFinished));

        Button btnBounceInDown = new Button("bounceInDown");
        btnBounceInDown.setOnAction(e -> AnimateFx.bounceInDown(animationNode, 500, 100, onFinished));

        Button btnBounceInLeft = new Button("bounceInLeft");
        btnBounceInLeft.setOnAction(e -> AnimateFx.bounceInLeft(animationNode, 500, 300, onFinished));

        Button btnBounceInRight = new Button("bounceInRight");
        btnBounceInRight.setOnAction(e -> AnimateFx.bounceInRight(animationNode, 500, 300, onFinished));

        Button btnBounceInUp = new Button("btnBounceInUp");
        btnBounceInUp.setOnAction(e -> AnimateFx.bounceInUp(animationNode, 500, 100, onFinished));

        Button btnFadeIn = new Button("fadeIn");
        btnFadeIn.setOnAction(e -> AnimateFx.fadeIn(animationNode, 1000, onFinished));

        Button btnFadeInDown = new Button("fadeInDown");
        btnFadeInDown.setOnAction(e -> AnimateFx.fadeInDown(animationNode, 1000, 100, onFinished));

        Button btnFadeInUp = new Button("fadeInUp");
        btnFadeInUp.setOnAction(e -> AnimateFx.fadeInUp(animationNode, 1000, 100, onFinished));

        Button btnFadeOutLeft = new Button("fadeOutLeft");
        btnFadeOutLeft.setOnAction(e -> AnimateFx.fadeOutLeft(animationNode, 500, 100, onFinished));

        Button btnFadeOutRight = new Button("fadeOutRight");
        btnFadeOutRight.setOnAction(e -> AnimateFx.fadeOutRight(animationNode, 500, 100, onFinished));

        Button btnFlash = new Button("flash");
        btnFlash.setOnAction(e -> AnimateFx.flash(animationNode, 500, onFinished));

        Button btnPulse = new Button("pulse");
        btnPulse.setOnAction(e -> AnimateFx.pulse(animationNode, 500, 1.2, onFinished));

        Button btnRotateIn = new Button("rotateIn");
        btnRotateIn.setOnAction(e -> AnimateFx.rotateIn(animationNode, 1000, 180, onFinished));

        Button btnRubberBand = new Button("rubberBand");
        btnRubberBand.setOnAction(e -> AnimateFx.rubberBand(animationNode, 500, onFinished));

        Button btnZoomIn = new Button("zoomIn");
        btnZoomIn.setOnAction(e -> AnimateFx.zoomIn(animationNode, 500, onFinished));

        VBox controls = new VBox();
        controls.getChildren().addAll(btnBounceIn, btnBounceInDown, btnBounceInUp, btnBounceInLeft, btnBounceInRight);
        controls.getChildren().addAll(btnFadeIn, btnFadeInDown, btnFadeInUp, btnFadeOutLeft, btnFadeOutRight);
        controls.getChildren().addAll(btnRotateIn);
        controls.getChildren().addAll(btnFlash, btnPulse, btnRubberBand);
        controls.getChildren().addAll(btnZoomIn);

        BorderPane bp = new BorderPane();
        bp.setRight(controls);
        bp.setCenter(animationNodeContainer);

        stage.setScene(new Scene(bp, 500, 500));
        stage.show();
    }

    private void addAnimationNode() {
        animationNodeContainer.getChildren().clear();
        animationNode = new Button("AnimateFx");
        animationNodeContainer.getChildren().add(animationNode);
    }
}