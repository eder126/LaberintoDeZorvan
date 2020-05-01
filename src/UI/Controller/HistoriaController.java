package UI.Controller;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HistoriaController implements Initializable {
    @FXML
    private ImageView traveler, sphere, battle, trap, ally, enemy, partners;
    @FXML
    private HBox components;
    @FXML
    private Label story, videoGameName, instruction;
    @FXML
    private ToggleButton skipButton;
    @FXML
    private AnchorPane screen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instruction.setOnKeyTyped(null);

        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> tellStory("En una época lejana, un \nviajero se encaminó en \nbúsqueda "
                + "del tesoro perdido \nde Ahrman.", 2));
        delay.play();
    }

    private void tellStory(String pContent, int pNextContent) {
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(6000));
            }

            protected void interpolate(double frac) {
                final int length = pContent.length();
                final int n = Math.round(length * (float) frac);
                story.setText(pContent.substring(0, n));
            }
        };

        animation.statusProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue == Animation.Status.STOPPED) {
                PauseTransition delay;

                if (pNextContent != 6) {
                    delay = new PauseTransition(Duration.seconds(2));
                } else {
                    delay = new PauseTransition(Duration.seconds(5));
                }

                delay.setOnFinished(event -> {
                    FadeTransition fadeTransitionOldImage = new FadeTransition(),
                            fadeTransitionNewImage = new FadeTransition();
                    String newContent;
                    int nextContent;

                    fadeTransitionOldImage.setDuration(Duration.millis(250));
                    fadeTransitionOldImage.setFromValue(1);
                    fadeTransitionOldImage.setToValue(0);

                    fadeTransitionNewImage.setDuration(Duration.millis(250));
                    fadeTransitionNewImage.setFromValue(0);
                    fadeTransitionNewImage.setToValue(1);

                    switch (pNextContent) {
                        case 2:
                            fadeTransitionOldImage.setNode(traveler);
                            fadeTransitionNewImage.setNode(sphere);

                            newContent = "Una mítica esfera que \npermite a su poseedor ver el futuro cercano.";
                            nextContent = 3;
                            break;
                        case 3:
                            fadeTransitionOldImage.setNode(sphere);
                            fadeTransitionNewImage.setNode(battle);

                            newContent = "En su jornada, el viajero se encontró atrapado en un \ncampo de batalla " +
                                    "lleno de:";
                            nextContent = 4;
                            break;
                        case 4:
                            fadeTransitionOldImage.setNode(battle);
                            fadeTransitionNewImage.setNode(trap);

                            newContent = "TRAMPAS, ALIADOS y ENEMIGOS.";
                            nextContent = 5;
                            break;
                        case 5:
                            fadeTransitionOldImage.setNode(components);
                            fadeTransitionNewImage.setNode(partners);

                            newContent = "Solo uniendo fuerza con sus \ncompañeros podrá elaborar \nuna estrategia para"
                                    + " escapar \ndel laberinto abismal.";
                            nextContent = 6;
                            break;
                        case 6:
                            fadeTransitionOldImage.setNode(partners);
                        default:
                            newContent = " ";
                            nextContent = 0;
                            break;
                    }

                    fadeTransitionNewImage.statusProperty().addListener((observableValor, oldValor, newValor) -> {
                        if (newValor == Animation.Status.STOPPED) {
                            if (pNextContent == 4) {
                                FadeTransition fadeTransitionAllyImage = new FadeTransition(),
                                        fadeTransitionEnemyImage = new FadeTransition();
                                PauseTransition delayAllyImage = new PauseTransition(Duration.seconds(2));

                                fadeTransitionAllyImage.setDuration(Duration.millis(250));
                                fadeTransitionAllyImage.setFromValue(0);
                                fadeTransitionAllyImage.setToValue(1);
                                fadeTransitionAllyImage.setNode(ally);

                                fadeTransitionEnemyImage.setDuration(Duration.millis(250));
                                fadeTransitionEnemyImage.setFromValue(0);
                                fadeTransitionEnemyImage.setToValue(1);
                                fadeTransitionEnemyImage.setNode(enemy);

                                fadeTransitionAllyImage.statusProperty().addListener((valorObservable, valorAntiguo,
                                                                                      nuevoValor) -> {
                                    if (nuevoValor == Animation.Status.STOPPED) {
                                        PauseTransition delayEnemyImage = new PauseTransition(Duration.seconds(2));

                                        delayEnemyImage.setOnFinished(eventImages -> fadeTransitionEnemyImage.play());
                                        delayEnemyImage.play();
                                    }
                                });

                                delayAllyImage.setOnFinished(eventImages -> fadeTransitionAllyImage.play());
                                delayAllyImage.play();
                            }

                            tellStory(newContent, nextContent);
                        }
                    });

                    fadeTransitionOldImage.statusProperty().addListener((observableValor, oldValor, newValor) -> {
                        if (newValor == Animation.Status.RUNNING && pNextContent == 6) {
                            FadeTransition fadeTransitionText = new FadeTransition();

                            fadeTransitionText.setDuration(Duration.millis(250));
                            fadeTransitionText.setFromValue(1);
                            fadeTransitionText.setToValue(0);
                            fadeTransitionText.setNode(story);

                            fadeTransitionText.statusProperty().addListener((valorObservable, valorAntiguo,
                                                                             nuevoValor) -> {
                                if (nuevoValor == Animation.Status.RUNNING) {
                                    skipButton.setOnMouseClicked(null);
                                    skipButton.setCursor(Cursor.DEFAULT);

                                    FadeTransition fadeTransitionSkipButton = new FadeTransition();

                                    fadeTransitionSkipButton.setDuration(Duration.millis(250));
                                    fadeTransitionSkipButton.setFromValue(0.5);
                                    fadeTransitionSkipButton.setToValue(0);
                                    fadeTransitionSkipButton.setNode(skipButton);

                                    fadeTransitionSkipButton.play();
                                }
                                if (nuevoValor == Animation.Status.STOPPED) {
                                    story.toBack();
                                }
                            });

                            fadeTransitionText.play();
                        }
                        if (newValor == Animation.Status.STOPPED) {
                            if (nextContent == 6) {
                                components.setVisible(false);
                            }

                            if (pNextContent != 6) {
                                PauseTransition delayBetweenImages = new PauseTransition(Duration.seconds(0.25));

                                delayBetweenImages.setOnFinished(eventImages -> fadeTransitionNewImage.play());
                                delayBetweenImages.play();
                            } else {
                                videoGameName.setOpacity(1);

                                PauseTransition delayAfterVideoGameName = new PauseTransition(Duration.seconds(5));

                                delayAfterVideoGameName.setOnFinished(eventImages -> {
                                    instruction.setOpacity(0.5);
                                    screen.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<>() {
                                        @Override
                                        public void handle(KeyEvent event) {
                                            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DIGIT2) {
                                                Stage window = new Stage();
                                                Scene scene = null;
                                                try {
                                                    scene = new Scene(FXMLLoader.load(getClass().getResource("../FXML/FXMLInicio.fxml")));
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                scene.setFill(Color.TRANSPARENT);
                                                window.setScene(scene);
                                                window.initStyle(StageStyle.TRANSPARENT);
                                                window.show();

                                                Stage storyWindow = (Stage) story.getScene().getWindow();
                                                storyWindow.hide();
                                            }
                                        }
                                    });
                                });

                                delayAfterVideoGameName.play();
                            }
                        }
                    });

                    fadeTransitionOldImage.play();
                });

                delay.play();
            }
        });

        animation.play();
    }

    @FXML
    void skip(MouseEvent event) throws Exception {
        Stage window = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../FXML/FXMLInicio.fxml")));
        scene.setFill(Color.TRANSPARENT);
        window.setScene(scene);
        window.initStyle(StageStyle.TRANSPARENT);
        window.show();

        Stage storyWindow = (Stage) story.getScene().getWindow();
        storyWindow.hide();
    }
}
