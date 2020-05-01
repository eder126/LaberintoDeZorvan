package UI.Controller;

import CL.ataque.Elemento;
import CL.misc.Ficha;
import CL.misc.Personaje;
import CL.tablero.GenerarTablero;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InicioController implements Initializable {
    @FXML
    private Slider jugadoresSlider;
    @FXML
    private TextField nombre1, nombre2, nombre3, nombre4;
    private int contador1 = 0, contador2 = 0, contador3 = 0, contador4 = 0;
    @FXML
    private CheckBox fuego1, agua1, planta1, roca1, electrico1, hielo1, fuego2, agua2, planta2, roca2, electrico2,
            hielo2, fuego3, agua3, planta3, roca3, electrico3, hielo3, fuego4, agua4, planta4, roca4, electrico4,
            hielo4, pc2, pc3, pc4, no2, no3, no4;
    @FXML
    private Pane panelJuego, jugador1, jugador2, jugador3, jugador4;
    @FXML
    private Button yesPC2, noPC2, yesPC3, noPC3, yesPC4, noPC4, asterisk;
    @FXML
    private HBox error;
    @FXML
    private Label indication;

    @FXML
    public void iniciar(MouseEvent event) {
        Ocultar();
    }

    @FXML
    public void salir(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void jugadoresCantidad(MouseEvent event) {
        switch ((int) ((Slider) event.getSource()).getValue()) {
            case 2:
                jugador2.setVisible(true);
                jugador3.setVisible(false);
                jugador4.setVisible(false);
                contador3 = 0;
                Desactivar(nombre3, fuego3, agua3, planta3, roca3, electrico3, hielo3);
                contador4 = 0;
                Desactivar(nombre4, fuego4, agua4, planta4, roca4, electrico4, hielo4);
                pc3.setSelected(false);
                pc3.setDisable(false);
                pc4.setSelected(false);
                pc4.setDisable(false);
                break;
            case 3:
                jugador2.setVisible(true);
                jugador3.setVisible(true);
                jugador4.setVisible(false);
                contador4 = 0;
                Desactivar(nombre4, fuego4, agua4, planta4, roca4, electrico4, hielo4);
                pc4.setSelected(false);
                pc4.setDisable(false);
                break;
            case 4:
                jugador2.setVisible(true);
                jugador3.setVisible(true);
                jugador4.setVisible(true);
                break;
            default:
                break;
        }
    }

    @FXML
    public void poderCasilla(MouseEvent event) {
        switch (((CheckBox) event.getSource()).getId().charAt(((CheckBox) event.getSource()).getId().length() - 1)) {
            case '1':
                if (((CheckBox) event.getSource()).isSelected()) {
                    contador1++;
                    if (contador1 >= 3) {
                        DeshabilitarCasillas(fuego1, agua1, planta1, roca1, electrico1, hielo1);
                    }
                } else if (!((CheckBox) event.getSource()).isSelected()) {
                    contador1--;
                    if (contador1 < 3) {
                        HabilitarCasillas(fuego1, agua1, planta1, roca1, electrico1, hielo1);
                    }
                }
                break;
            case '2':
                if (((CheckBox) event.getSource()).isSelected()) {
                    contador2++;
                    if (contador2 >= 3) {
                        DeshabilitarCasillas(fuego2, agua2, planta2, roca2, electrico2, hielo2);
                    }
                } else if (!((CheckBox) event.getSource()).isSelected()) {
                    contador2--;
                    if (contador2 < 3) {
                        HabilitarCasillas(fuego2, agua2, planta2, roca2, electrico2, hielo2);
                    }
                }
                break;
            case '3':
                if (((CheckBox) event.getSource()).isSelected()) {
                    contador3++;
                    if (contador3 >= 3) {
                        DeshabilitarCasillas(fuego3, agua3, planta3, roca3, electrico3, hielo3);
                    }
                } else if (!((CheckBox) event.getSource()).isSelected()) {
                    contador3--;
                    if (contador3 < 3) {
                        HabilitarCasillas(fuego3, agua3, planta3, roca3, electrico3, hielo3);
                    }
                }
                break;
            case '4':
                if (((CheckBox) event.getSource()).isSelected()) {
                    contador4++;
                    if (contador4 >= 3) {
                        DeshabilitarCasillas(fuego4, agua4, planta4, roca4, electrico4, hielo4);
                    }
                } else if (!((CheckBox) event.getSource()).isSelected()) {
                    contador4--;
                    if (contador4 < 3) {
                        HabilitarCasillas(fuego4, agua4, planta4, roca4, electrico4, hielo4);
                    }
                }
                break;
            default:
                break;
        }
    }

    @FXML
    public void IniciarJuego(MouseEvent event) {
        if (VerificarJuego()) {
            Ficha[] fichas = new Ficha[(int) jugadoresSlider.getValue()];
            switch ((int) jugadoresSlider.getValue()) {
                case 2:
                    fichas[0] = new Ficha(nombre1.getText(), personajesJ(fuego1, agua1, planta1, roca1, electrico1, hielo1), Color.BLUE, false);
                    fichas[1] = new Ficha(nombre2.getText(), personajesJ(fuego2, agua2, planta2, roca2, electrico2, hielo2), Color.RED, pc2.isSelected());
                    /*fichas[0].setPosicion(95);
                    fichas[1].setPosicion(96);*/
                    break;
                case 3:
                    fichas[0] = new Ficha(nombre1.getText(), personajesJ(fuego1, agua1, planta1, roca1, electrico1, hielo1), Color.BLUE, false);
                    fichas[1] = new Ficha(nombre2.getText(), personajesJ(fuego2, agua2, planta2, roca2, electrico2, hielo2), Color.RED,  pc2.isSelected());
                    fichas[2] = new Ficha(nombre3.getText(), personajesJ(fuego3, agua3, planta3, roca3, electrico3, hielo3), Color.GREEN,  pc3.isSelected());
                    /*fichas[0].setPosicion(95);
                    fichas[1].setPosicion(96);
                    fichas[2].setPosicion(96);*/
                    break;
                case 4:
                    fichas[0] = new Ficha(nombre1.getText(), personajesJ(fuego1, agua1, planta1, roca1, electrico1, hielo1), Color.BLUE, false);
                    fichas[1] = new Ficha(nombre2.getText(), personajesJ(fuego2, agua2, planta2, roca2, electrico2, hielo2), Color.RED,  pc2.isSelected());
                    fichas[2] = new Ficha(nombre3.getText(), personajesJ(fuego3, agua3, planta3, roca3, electrico3, hielo3), Color.GREEN, pc3.isSelected());
                    fichas[3] = new Ficha(nombre4.getText(), personajesJ(fuego4, agua4, planta4, roca4, electrico4, hielo4), Color.YELLOW, pc4.isSelected());
                    /*fichas[0].setPosicion(95);
                    fichas[1].setPosicion(96);
                    fichas[2].setPosicion(96);
                    fichas[3].setPosicion(96);*/
                    break;
                default:
                    break;
            }

            GenerarTablero.generar(fichas);

            try {
                Stage storyWindow = (Stage) panelJuego.getScene().getWindow();
                storyWindow.hide();

                FXMLLoader loader = new FXMLLoader();

                loader.setLocation(getClass().getResource("../FXML/FXMLTablero.fxml"));

                Parent tableViewP = loader.load();

                Scene tableViewS = new Scene(tableViewP);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(tableViewS);
                window.setResizable(false);
                window.setFullScreen(true);
                window.setAlwaysOnTop(true);
                window.show();

            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {

        }
    }

    @FXML
    public void changePC2Color(MouseEvent event) {
        if (((CheckBox) event.getSource()) == pc2 && pc2.isSelected()) {
            yesPC2.setTextFill(Color.YELLOW);
            noPC2.setTextFill(Color.WHITE);
            no2.setSelected(false);
        } else if (((CheckBox) event.getSource()) == pc2 && !pc2.isSelected()) {
            noPC2.setTextFill(Color.YELLOW);
            yesPC2.setTextFill(Color.WHITE);
            no2.setSelected(true);
        } else if (pc2.isSelected()) {
            noPC2.setTextFill(Color.YELLOW);
            yesPC2.setTextFill(Color.WHITE);
            pc2.setSelected(false);
        } else {
            noPC2.setTextFill(Color.YELLOW);
            yesPC2.setTextFill(Color.WHITE);
            pc2.setSelected(false);
        }
    }

    @FXML
    public void changePC3Color(MouseEvent event) {
        if (((CheckBox) event.getSource()) == pc3 && pc3.isSelected()) {
            yesPC3.setTextFill(Color.YELLOW);
            noPC3.setTextFill(Color.WHITE);
            no3.setSelected(false);
        } else if (((CheckBox) event.getSource()) == pc3 && !pc3.isSelected()) {
            noPC3.setTextFill(Color.YELLOW);
            yesPC3.setTextFill(Color.WHITE);
            no3.setSelected(true);
        } else if (pc3.isSelected()) {
            noPC3.setTextFill(Color.YELLOW);
            yesPC3.setTextFill(Color.WHITE);
            pc3.setSelected(false);
        } else {
            noPC3.setTextFill(Color.YELLOW);
            yesPC3.setTextFill(Color.WHITE);
            pc3.setSelected(false);
        }
    }

    @FXML
    public void changePC4Color(MouseEvent event) {
        if (((CheckBox) event.getSource()) == pc4 && pc4.isSelected()) {
            yesPC4.setTextFill(Color.YELLOW);
            noPC4.setTextFill(Color.WHITE);
            no4.setSelected(false);
        } else if (((CheckBox) event.getSource()) == pc4 && !pc4.isSelected()) {
            noPC4.setTextFill(Color.YELLOW);
            yesPC4.setTextFill(Color.WHITE);
            no4.setSelected(true);
        } else if (pc4.isSelected()) {
            noPC4.setTextFill(Color.YELLOW);
            yesPC4.setTextFill(Color.WHITE);
            pc4.setSelected(false);
        } else {
            yesPC4.setTextFill(Color.YELLOW);
            noPC4.setTextFill(Color.WHITE);
            pc4.setSelected(false);
        }
    }

    /**
     * Verifica que todos los datos se hayan registrado
     *
     * @return si el juego es valido para comenzar o no
     */
    private boolean VerificarJuego() {
        asterisk.setStyle("-fx-font-size: 16px;");
        indication.setStyle("-fx-font-size: 16px;");
        error.setVisible(false);
        boolean continuar = false;
        switch ((int) jugadoresSlider.getValue()) {
            case 2:
                if ((!(nombre1.getText().equals("")) && (contador1 == 3)) && (!(nombre2.getText().equals("")) &&
                        (contador2 == 3)) && (!(nombre1.getText().equals(nombre2.getText()))))
                    continuar = true;
                else {
                    asterisk.setStyle("-fx-font-size: 14px;");
                    indication.setStyle("-fx-font-size: 14px;");
                    error.setVisible(true);
                    animarTexto("Debe nombrar los personajes de manera única y asignarles tres poderes.");
                }
                break;
            case 3:
                if ((!(nombre1.getText().equals("")) && (contador1 == 3)) && (!(nombre2.getText().equals("")) &&
                        (contador2 == 3)) && (!(nombre3.getText().equals("")) && (contador3 == 3)) &&
                        (!(nombre1.getText().equals(nombre2.getText()))) &&
                        (!(nombre2.getText().equals(nombre3.getText()))) &&
                        (!(nombre1.getText().equals(nombre3.getText()))))
                    continuar = true;
                else {
                    asterisk.setStyle("-fx-font-size: 14px;");
                    indication.setStyle("-fx-font-size: 14px;");
                    error.setVisible(true);
                    animarTexto("Debe nombrar los personajes de manera única y asignarles tres poderes.");
                }
                break;
            case 4:
                if ((!(nombre1.getText().equals("")) && (contador1 == 3)) && (!(nombre2.getText().equals("")) &&
                        (contador2 == 3)) && (!(nombre3.getText().equals("")) && (contador3 == 3)) &&
                        (!(nombre4.getText().equals("")) && (contador4 == 3)) &&
                        (!(nombre1.getText().equals(nombre2.getText()))) &&
                        (!(nombre2.getText().equals(nombre3.getText()))) &&
                        (!(nombre1.getText().equals(nombre3.getText()))) &&
                        (!(nombre2.getText().equals(nombre4.getText()))) &&
                        (!(nombre1.getText().equals(nombre4.getText()))) &&
                        (!(nombre3.getText().equals(nombre4.getText()))))
                    continuar = true;
                else {
                    asterisk.setStyle("-fx-font-size: 14px;");
                    indication.setStyle("-fx-font-size: 14px;");
                    error.setVisible(true);
                    animarTexto("Debe nombrar los personajes de manera única y asignarles tres poderes.");
                }
                break;
            default:
                break;
        }

        if (continuar) {
            switch ((int) jugadoresSlider.getValue()) {
                case 2:
                    if (!(nombre1.getText().length() < 5 && nombre2.getText().length() < 5)) {
                        error.setVisible(true);
                        animarTexto("Los nombres solo pueden contener máximo 4 caracteres.");
                        continuar = false;
                    }
                    break;
                case 3:
                    if (!(nombre1.getText().length() < 5 && nombre2.getText().length() < 5 &&
                            nombre3.getText().length() < 5)) {
                        error.setVisible(true);
                        animarTexto("Los nombres solo pueden contener máximo 4 caracteres.");
                        continuar = false;
                    }
                    break;
                case 4:
                    if (!(nombre1.getText().length() < 5 && nombre2.getText().length() < 5 &&
                            nombre3.getText().length() < 5 && nombre4.getText().length() < 5)) {
                        error.setVisible(true);
                        animarTexto("Los nombres solo pueden contener máximo 4 caracteres.");
                        continuar = false;
                    }
                    break;
                default:
                    break;
            }
        }

        if (continuar) {
            switch ((int) jugadoresSlider.getValue()) {
                case 2:
                    if (!pc2.isSelected() && !no2.isSelected()) {
                        error.setVisible(true);
                        animarTexto("Debe indicar si el jugador es una computadora.");
                        continuar = false;
                    }
                    break;
                case 3:
                    if ((!pc2.isSelected() && !no2.isSelected()) || (!pc3.isSelected() && !no3.isSelected())) {
                        error.setVisible(true);
                        animarTexto("Debe indicar si los jugadores son computadoras.");
                        continuar = false;
                    }
                    break;
                case 4:
                    if ((!pc2.isSelected() && !no2.isSelected()) || (!pc3.isSelected() && !no3.isSelected()) ||
                            (!pc4.isSelected() && !no4.isSelected())) {
                        error.setVisible(true);
                        animarTexto("Debe indicar si los jugadores son computadoras.");
                        continuar = false;
                    }
                    break;
                default:
                    break;
            }
        }

        return continuar;
    }

    private void animarTexto(String contenido) {
        indication.setText("");

        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(2000));
            }

            protected void interpolate(double frac) {
                final int length = contenido.length();
                final int n = Math.round(length * (float) frac);
                indication.setText(contenido.substring(0, n));
            }
        };

        animation.play();
    }

    /**
     * Verifica los CheckBox especificos del jugador que activo una casilla, se activa si el contador llega a 3
     *
     * @param ch1
     * @param ch2
     * @param ch3
     * @param ch4
     * @param ch5
     * @param ch6
     */
    private void DeshabilitarCasillas(CheckBox ch1, CheckBox ch2, CheckBox ch3, CheckBox ch4, CheckBox ch5, CheckBox ch6) {
        if (!ch1.isSelected())
            ch1.setDisable(true);
        if (!ch2.isSelected())
            ch2.setDisable(true);
        if (!ch3.isSelected())
            ch3.setDisable(true);
        if (!ch4.isSelected())
            ch4.setDisable(true);
        if (!ch5.isSelected())
            ch5.setDisable(true);
        if (!ch6.isSelected())
            ch6.setDisable(true);
    }

    /**
     * Verifica los CheckBox especificos del jugador que activo una casilla,habilita los CheckBox si el contador es menor a 3
     *
     * @param ch1
     * @param ch2
     * @param ch3
     * @param ch4
     * @param ch5
     * @param ch6
     */
    private void HabilitarCasillas(CheckBox ch1, CheckBox ch2, CheckBox ch3, CheckBox ch4, CheckBox ch5, CheckBox ch6) {
        ch1.setDisable(false);
        ch2.setDisable(false);
        ch3.setDisable(false);
        ch4.setDisable(false);
        ch5.setDisable(false);
        ch6.setDisable(false);
    }

    /**
     * Retorna la triada seleccionada por el jugador que llame la funcion
     *
     * @param ch1
     * @param ch2
     * @param ch3
     * @param ch4
     * @param ch5
     * @param ch6
     * @return
     */
    private Personaje[] personajesJ(CheckBox ch1, CheckBox ch2, CheckBox ch3, CheckBox ch4, CheckBox ch5, CheckBox ch6) {
        Personaje[] personajes = new Personaje[3];
        int cont = 0;
        if (ch1.isSelected()) {
            personajes[cont] = new Personaje(Elemento.FUEGO);
            cont++;
        }
        if (ch2.isSelected()) {
            personajes[cont] = new Personaje(Elemento.AGUA);
            cont++;
        }
        if (ch3.isSelected()) {
            personajes[cont] = new Personaje(Elemento.PLANTA);
            cont++;
        }
        if (ch4.isSelected()) {
            personajes[cont] = new Personaje(Elemento.ROCA);
            cont++;
        }
        if (ch5.isSelected()) {
            personajes[cont] = new Personaje(Elemento.ELECTRICO);
            cont++;
        }
        if (ch6.isSelected()) {
            personajes[cont] = new Personaje(Elemento.HIELO);
            cont++;
        }
        return personajes;
    }

    /**
     * Reinicia los CheckBox del jugador que lo llame.
     *
     * @param text
     * @param ch1
     * @param ch2
     * @param ch3
     * @param ch4
     * @param ch5
     * @param ch6
     */
    private void Desactivar(TextField text, CheckBox ch1, CheckBox ch2, CheckBox ch3, CheckBox ch4, CheckBox ch5, CheckBox ch6) {
        text.clear();
        ch1.setSelected(false);
        ch1.setDisable(false);
        ch2.setSelected(false);
        ch2.setDisable(false);
        ch3.setSelected(false);
        ch3.setDisable(false);
        ch4.setSelected(false);
        ch4.setDisable(false);
        ch5.setSelected(false);
        ch5.setDisable(false);
        ch6.setSelected(false);
        ch6.setDisable(false);
    }

    /**
     * Funcion que va del menu principal a la configuracion de la partida.
     */
    private void Ocultar() {
        panelJuego.setVisible(true);
        contador1 = 0;
        contador2 = 0;
        contador3 = 0;
        contador4 = 0;
        Desactivar(nombre1, fuego1, agua1, planta1, roca1, electrico1, hielo1);
        Desactivar(nombre2, fuego2, agua2, planta2, roca2, electrico2, hielo2);
        Desactivar(nombre3, fuego3, agua3, planta3, roca3, electrico3, hielo3);
        Desactivar(nombre4, fuego4, agua4, planta4, roca4, electrico4, hielo4);
        pc3.setSelected(false);
        pc3.setDisable(false);
        pc4.setSelected(false);
        pc4.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}