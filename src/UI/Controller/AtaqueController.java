package UI.Controller;

import CL.ataque.Ataque;
import CL.ataque.Elemento;
import CL.ataque.Poder;
import CL.computadora.AdaptadorComputadora;
import CL.dado.Dado;
import CL.dado.Fabrica_Dados;
import CL.misc.Ficha;
import CL.misc.Personaje;
import CL.tablero.Tablero;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.text.Normalizer;
import java.util.ResourceBundle;

public class AtaqueController implements Initializable {
    public static Ficha token;

    @FXML
    private ProgressBar healthBar;
    @FXML
    private Label indication, healthPoints, player, position, initialHealthPoints;
    @FXML
    private HBox dialogue, charactersMenu, elementsMenu, powersMenu, tokensMenu, blocker;
    @FXML
    private Button character1, character2, character3, element1, element2, element3, power1, power2, power3, token1,
            token2, token3, ca1, ca2, ca3, pa1, pa2, pa3, ta1, ta2, ta3, extraPoints1, extraPoints2, extraPoints3,
            totalPoints1, totalPoints2, totalPoints3, tokenPosition1, tokenPosition2, tokenPosition3;
    @FXML
    private ToggleButton attackOption, activateOption;
    @FXML
    private Tooltip powerHint1, powerHint2, powerHint3, pah1, pah2, pah3;
    @FXML
    private ImageView stone;

    /**
     * Ed Dados
     */
    @FXML
    private ToggleButton throwOption;
    @FXML
    private ImageView imagenDado;
    private Dado dAtaque;
    /**
     * Ed Dados
     */

    private Button personajePtsExtraAplicados;

    private Ataque attack;
    private Poder power;
    private Tablero board;
    private int permisosAtacar = 0, permisosActivar = 0;
    private boolean poderActivado, ptsExtraAplicados, stoneVencido, characterDisabled1, characterDisabled2,
            characterDisabled3;

    private AdaptadorComputadora computadora;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int cont = 0;
        Line linea = new Line();
        PathTransition transition = new PathTransition();

        board = Tablero.getTablero();

        player.setText(token.getNombre());
        position.setText(Integer.toString(token.getPosicion()));

        if (token.getPersonajes()[0].getElemento() == Elemento.ROCA || token.getPersonajes()[1].getElemento() ==
                Elemento.ROCA || token.getPersonajes()[2].getElemento() == Elemento.ROCA) {
            for (Ficha token : board.getFichas()) {
                if (token.getStone() != null && token != AtaqueController.token) {
                    cont++;
                }
            }

            if (cont == board.getFichas().length - 1) {
                if (token.getPersonajes()[0].getElemento() == Elemento.ROCA) {
                    pa1.setDisable(true);
                    power1.setDisable(true);
                } else if (token.getPersonajes()[1].getElemento() == Elemento.ROCA) {
                    pa2.setDisable(true);
                    power2.setDisable(true);
                } else {
                    pa3.setDisable(true);
                    power3.setDisable(true);
                }
            }
        }

        if ((token.getPersonajes()[0].getConPoder() != 0 && token.getPersonajes()[1].getConPoder() != 0 &&
                token.getPersonajes()[2].getConPoder() != 0) || (power1.isDisable() && power2.isDisable() &&
                power3.isDisable())) {
            activateOption.setDisable(true);
        }

        if (attack == null) {
            attack = new Ataque();

            if (token.getStone() == null) {
                Elemento[] elementosTriada = new Elemento[3];

                elementosTriada[0] = token.getPersonajes()[0].getElemento();
                elementosTriada[1] = token.getPersonajes()[1].getElemento();
                elementosTriada[2] = token.getPersonajes()[2].getElemento();

                //attack.setPiedra(new StoneP(100, elementosTriada));
                attack.setPiedra(Tablero.getTablero().getPiedra().clonar(100, 100, elementosTriada));
            } else {
                attack.setPiedra(token.getStone());
            }
        }
        if (power == null) {
            power = new Poder();
        }
        if (attack.getPiedra().getVidaInicial() == 100) {
            healthBar.setProgress(attack.getPiedra().getVida() * 0.01);
        } else {
            healthBar.setProgress((1 / (0.06 / (attack.getPiedra().getVida() * 0.01))) * 0.1);
        }
        healthPoints.setText(Integer.toString(attack.getPiedra().getVida()));
        initialHealthPoints.setText("/ " + attack.getPiedra().getVidaInicial());

        attackOption.setDisable(true);
        activateOption.setDisable(true);

        if (dAtaque == null) {
            Fabrica_Dados fabrica = new Fabrica_Dados();
            dAtaque = fabrica.crearDado(false);
        }

        linea.setStartX(69);
        linea.setEndX(76);
        linea.setStartY(50);
        linea.setEndY(51);

        transition.setNode(stone);
        transition.setDuration(Duration.seconds(0.65));
        transition.setPath(linea);
        transition.setCycleCount(PathTransition.INDEFINITE);
        transition.setAutoReverse(true);
        transition.play();

        if (token.getComputadora()) {
            blocker.toFront();
            computadora = new AdaptadorComputadora();

            PauseTransition delay = new PauseTransition(Duration.seconds(3));

            delay.setOnFinished(event -> {
                realizarPostRutinaActivarPoder();
            });

            delay.play();
        } else {
            realizarPostRutinaActivarPoder();
        }
    }

    /**
     * Ed Dados
     * Convierte el ImageView de Dado en visible
     * Llama a la funcion girar dado
     * Convierte el ImageView de Dado en Invisible
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void tirar(MouseEvent event) throws Exception {
        throwOption.setDisable(true);
        imagenDado.setVisible(true);
        attackOption.setOnMouseClicked(null);
        activateOption.setOnMouseClicked(null);
        throwOption.setOnMouseClicked(null);
        girarDado();
    }

    /**
     * Ed Dados
     * Funcion que lanza el dado de ataque.
     */
    private void girarDado() {
        RotateTransition rt = new RotateTransition(Duration.seconds(2), imagenDado); /** 2 segundos */
        rt.setFromAngle(0);
        rt.setToAngle(720); /** 2 Vueltas */
        rt.setOnFinished(eventT -> {
            try {
                ca1.setDisable(false);
                character1.setDisable(false);
                ca2.setDisable(false);
                character2.setDisable(false);
                ca3.setDisable(false);
                character3.setDisable(false);
                switch (dAtaque.girar()) {
                    case 1:
                        if (!stoneVencido) {
                            permisosAtacar += 1;
                            attackOption.setDisable(false);
                        }
                        break;
                    case 2:
                        if (!stoneVencido) {
                            permisosAtacar += 2;
                            attackOption.setDisable(false);
                        }
                        break;
                    case 3:
                        if (!stoneVencido) {
                            permisosAtacar += 3;
                            attackOption.setDisable(false);
                        }
                        break;
                    case 4:
                        if (!stoneVencido) {
                            permisosAtacar += 1;
                            attackOption.setDisable(false);
                        }

                        permisosActivar += 1;
                        activateOption.setDisable(false);
                        break;
                    case 5:
                        if (!stoneVencido) {
                            permisosAtacar += 2;
                            attackOption.setDisable(false);
                        }
                        permisosActivar += 1;
                        activateOption.setDisable(false);
                        break;
                    case 6:
                        if (!stoneVencido) {
                            permisosAtacar += 3;
                            attackOption.setDisable(false);
                        }
                        permisosActivar += 2;
                        activateOption.setDisable(false);
                        break;
                    default:
                        attackOption.setDisable(true);
                        activateOption.setDisable(true);
                        break;
                }
                imagenDado.setImage(new Image(getClass().getResource(dAtaque.imagenDado()).toExternalForm()));

                realizarPostRutinaActivarPoder();
            } catch (Exception e) {
                e.getMessage();
                e.printStackTrace();
            }
        });
        rt.play();
    }

    @FXML
    void atacar(MouseEvent event) throws Exception {
        imagenDado.setVisible(false);
        attackOption.setOnMouseClicked(null);
        activateOption.setOnMouseClicked(null);
        throwOption.setOnMouseClicked(null);
        mostrarEnunciadoPersonajes();
    }

    @FXML
    void activar(MouseEvent event) throws Exception {
        imagenDado.setVisible(false);
        attackOption.setOnMouseClicked(null);
        activateOption.setOnMouseClicked(null);
        throwOption.setOnMouseClicked(null);
        mostrarEnunciadoPoderes();
    }

    private void actualizarPuntosDeVida() {
        int vidaActual = Integer.parseInt(healthPoints.getText()), nuevaVida = attack.getPiedra().getVida();
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(500));
            }

            protected void interpolate(double frac) {
                if (healthBar.getProgress() > 0 || !healthPoints.getText().equals("0")) {
                    final int length = vidaActual - nuevaVida;
                    final int n = Math.round(length * (float) frac);
                    if (attack.getPiedra().getVidaInicial() == 100) {
                        healthBar.setProgress((vidaActual - n) * 0.01);
                    } else {
                        healthBar.setProgress((1 / (0.06 / ((vidaActual - n) * 0.01))) * 0.1);
                    }
                    healthPoints.setText(Integer.toString(vidaActual - n));
                } else {
                    attackOption.setDisable(true);

                    stoneVencido = true;
                }
            }
        };

        animation.statusProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue == Animation.Status.STOPPED) {
                PauseTransition delay = new PauseTransition(Duration.seconds(1.25));

                permisosAtacar--;
                if (permisosAtacar <= 0) {
                    attackOption.setDisable(true);
                }

                permitirUtilizarBotones();

                delay.setOnFinished(event -> {
                    if (Integer.parseInt(healthPoints.getText()) == 0) {
                        int cont = 0;

                        token.getStonesDerrotados().add(token.getPosicion());

                        AtaqueController.token.setStone(null);

                        for (Ficha token : board.getFichas()) {
                            if (token.getNombre().equals(AtaqueController.token.getNombre())) {
                                board.getFichas()[cont] = AtaqueController.token;
                                Tablero.setTableroS(board);
                                break;
                            }

                            cont++;
                        }

                        attackOption.setDisable(true);
                    }
                    if (attackOption.isDisable() && activateOption.isDisable() &&
                            throwOption.isDisable()) {
                        Stage battleWindow = (Stage) attackOption.getScene().getWindow();
                        battleWindow.hide();
                    } else {
                        if (computadora != null) {
                            jugarComputadora(attackOption, activateOption, throwOption);
                        }
                    }
                });

                delay.play();
            }
        });

        animation.play();
    }

    private void animarTexto(String contenido, char opcion) {
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

        animation.statusProperty().addListener(new ChangeListener<Animation.Status>() {
            @Override
            public void changed(ObservableValue<? extends Animation.Status> observableValue, Animation.Status oldValue,
                                Animation.Status newValue) {
                if (newValue == Animation.Status.STOPPED) {
                    switch (opcion) {
                        case 'p':
                            mostrarOpcionesPersonajes();
                            break;
                        case 'e':
                            mostrarOpcionesElementos();
                            break;
                        case 'o':
                            mostrarOpcionesPoderes();
                            break;
                        case '1':
                            poderActivado = true;
                            mostrarOpcionesPersonajes();
                            break;
                        case 'a':
                            dialogue.setVisible(false);
                            permisosActivar--;
                            realizarPostRutinaActivarPoder();
                            break;
                        case '2':
                            break;
                        case '3':
                            mostrarOpcionesJugadores();
                            break;
                        case '4':
                            mostrarOpcionesJugadores();
                            break;
                        case '5':
                            mostrarOpcionesJugadores();
                            break;
                        case '6':
                            mostrarOpcionesJugadores();
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        animation.play();
    }

    private void mostrarEnunciadoPersonajes() {
        dialogue.setVisible(true);

        animarTexto("¿Cuál personaje de la triada desea utilizar?      \n", 'p');
    }

    private void mostrarEnunciadoElementos() {
        dialogue.setVisible(true);

        animarTexto("¿A cuál elemento del stone desea atacar?      \n", 'e');
    }

    private void mostrarEnunciadoPoderes() {
        dialogue.setVisible(true);

        animarTexto("¿Cuál poder especial desea activar?      \n", 'o');
    }

    private String[] obtenerElementosPersonajes(boolean verificarContPoder) {
        String[] elements = new String[3];

        elements[0] = token.getPersonajes()[0].getElemento().toString();
        elements[1] = token.getPersonajes()[1].getElemento().toString();
        elements[2] = token.getPersonajes()[2].getElemento().toString();
        if (elements[0].equals("ELECTRICO")) {
            elements[0] = "ELÉCTRICO";
        } else if (elements[1].equals("ELECTRICO")) {
            elements[1] = "ELÉCTRICO";
        } else if (elements[2].equals("ELECTRICO")) {
            elements[2] = "ELÉCTRICO";
        }

        if (verificarContPoder) {
            if (token.getPersonajes()[0].getConPoder() != 0) {
                pa1.setDisable(true);
                power1.setDisable(true);
            }
            if (token.getPersonajes()[1].getConPoder() != 0) {
                pa2.setDisable(true);
                power2.setDisable(true);
            }
            if (token.getPersonajes()[2].getConPoder() != 0) {
                pa3.setDisable(true);
                power3.setDisable(true);
            }
        }

        return elements;
    }

    private void mostrarOpcionesPersonajes() {
        int characterExtraPoints1 = token.getPersonajes()[0].getContPtsExtra(),
                characterExtraPoints2 = token.getPersonajes()[1].getContPtsExtra(),
                characterExtraPoints3 = token.getPersonajes()[2].getContPtsExtra();

        dialogue.setVisible(false);
        extraPoints1.setVisible(false);
        extraPoints2.setVisible(false);
        extraPoints3.setVisible(false);

        String[] elementos = obtenerElementosPersonajes(false);
        String option1 = elementos[0], option2 = elementos[1], option3 = elementos[2];

        character1.setText("PJ " + option1.substring(0, 1).toUpperCase() + option1.substring(1).toLowerCase());
        character2.setText("PJ " + option2.substring(0, 1).toUpperCase() + option2.substring(1).toLowerCase());
        character3.setText("PJ " + option3.substring(0, 1).toUpperCase() + option3.substring(1).toLowerCase());

        if ((characterExtraPoints1 > 0 || (ptsExtraAplicados && personajePtsExtraAplicados == character1)) &&
                !character1.isDisable()) {
            extraPoints1.setVisible(true);
        }
        if ((characterExtraPoints2 > 0 || (ptsExtraAplicados && personajePtsExtraAplicados == character2)) &&
                !character2.isDisable()) {
            extraPoints2.setVisible(true);
        }
        if ((characterExtraPoints3 > 0 || (ptsExtraAplicados && personajePtsExtraAplicados == character3)) &&
                !character3.isDisable()) {
            extraPoints3.setVisible(true);
        }

        if (poderActivado) {
            characterDisabled1 = character1.isDisable();
            characterDisabled2 = character2.isDisable();
            characterDisabled3 = character3.isDisable();

            character1.setDisable(false);
            character2.setDisable(false);
            character3.setDisable(false);
        }

        charactersMenu.setVisible(true);

        if (computadora != null) {
            jugarComputadora(character1, character2, character3);
        }
    }

    private void mostrarOpcionesElementos() {
        Elemento element1 = attack.getPiedra().getElementos()[0], element2 = attack.getPiedra().getElementos()[1],
                element3 = attack.getPiedra().getElementos()[2];
        int totalPoints1 = attack.calcularPuntos(attack.getCharacter(), element1),
                totalPoints2 = attack.calcularPuntos(attack.getCharacter(), element2),
                totalPoints3 = attack.calcularPuntos(attack.getCharacter(), element3);
        Personaje personajePtsExtra = obtenerPersonajePtsExtra();

        dialogue.setVisible(false);

        String option1, option2, option3;

        option1 = element1.toString();
        option2 = element2.toString();
        option3 = element3.toString();
        if (option1.equals("ELECTRICO")) {
            option1 = "ELÉCTRICO";
        } else if (option2.equals("ELECTRICO")) {
            option2 = "ELÉCTRICO";
        } else if (option3.equals("ELECTRICO")) {
            option3 = "ELÉCTRICO";
        }
        this.element1.setText(option1.substring(0, 1).toUpperCase() + option1.substring(1).toLowerCase());
        this.element2.setText(option2.substring(0, 1).toUpperCase() + option2.substring(1).toLowerCase());
        this.element3.setText(option3.substring(0, 1).toUpperCase() + option3.substring(1).toLowerCase());

        if (attack.getCharacter().getContPtsExtra() > 0 || (ptsExtraAplicados && attack.getCharacter().getElemento() ==
                personajePtsExtra.getElemento())) {
            totalPoints1 += 5;
            totalPoints2 += 5;
            totalPoints3 += 5;
        }

        this.totalPoints1.setText("  " + totalPoints1 + " pts");
        this.totalPoints2.setText("  " + totalPoints2 + " pts");
        this.totalPoints3.setText("  " + totalPoints3 + " pts");

        elementsMenu.setVisible(true);

        if (computadora != null) {
            jugarComputadora(this.element1, this.element2, this.element3);
        }
    }

    private void mostrarOpcionesPoderes() {
        int cont = 0;

        dialogue.setVisible(false);

        String[] elementos = obtenerElementosPersonajes(true);
        String option1 = elementos[0], option2 = elementos[1], option3 = elementos[2],
                powerHint1 = obtenerHintPoder(token.getPersonajes()[0].getElemento()),
                powerHint2 = obtenerHintPoder(token.getPersonajes()[1].getElemento()),
                powerHint3 = obtenerHintPoder(token.getPersonajes()[2].getElemento());

        power1.setText("PE " + option1.substring(0, 1).toUpperCase() + option1.substring(1).toLowerCase());
        power2.setText("PE " + option2.substring(0, 1).toUpperCase() + option2.substring(1).toLowerCase());
        power3.setText("PE " + option3.substring(0, 1).toUpperCase() + option3.substring(1).toLowerCase());

        pah1.setText(powerHint1);
        this.powerHint1.setText(powerHint1);
        pah2.setText(powerHint2);
        this.powerHint2.setText(powerHint2);
        pah3.setText(powerHint3);
        this.powerHint3.setText(powerHint3);

        if (option1.equals("ROCA") || option2.equals("ROCA") || option3.equals("ROCA")) {
            for (Ficha token : board.getFichas()) {
                if (token.getStone() != null && token != AtaqueController.token) {
                    cont++;
                }
            }
            if (cont == board.getFichas().length - 1) {
                if (option1.equals("ROCA")) {
                    pa1.setDisable(true);
                    power1.setDisable(true);
                } else if (option2.equals("ROCA")) {
                    pa2.setDisable(true);
                    power2.setDisable(true);
                } else {
                    pa3.setDisable(true);
                    power3.setDisable(true);
                }
            }
        }

        powersMenu.setVisible(true);

        if (computadora != null) {
            jugarComputadora(power1, power2, power3);
        }
    }

    private String obtenerHintPoder(Elemento element) {
        String hint;

        switch (element) {
            case FUEGO:
                hint = "Le otorga cinco puntos extra a un\npersonaje de su triada por dos turnos";
                break;
            case AGUA:
                hint = "Habilita el dado de ataque\npara realizar un tiro extra";
                break;
            case PLANTA:
                hint = "No deja que un jugador saque más de tres\nen su dado de movimientos por dos turnos";
                break;
            case ELECTRICO:
                hint = "Causa una parálisis que puede evitar\nque un jugador tire el dado\nde movimiento por tres " +
                        "turnos";
                break;
            case ROCA:
                hint = "Crea un stone en la casilla\ndonde se encuentra un contrincante";
                break;
            case HIELO:
                hint = "Congela a un jugador por un turno";
                break;
            default:
                hint = "";
                break;
        }

        return hint;
    }

    private void mostrarOpcionesJugadores() {
        int cont = 1;

        dialogue.setVisible(false);
        ta1.setVisible(false);
        token1.setDisable(true);
        ta2.setVisible(false);
        token2.setDisable(true);
        ta3.setVisible(false);
        token3.setDisable(true);

        for (Ficha token : board.getFichas()) {
            if (token != null) {
                if (!token.getNombre().equals(AtaqueController.token.getNombre())) {
                    switch (cont) {
                        case 1:
                            ta1.setVisible(true);
                            token1.setText(token.getNombre());
                            ta1.setDisable(false);
                            token1.setDisable(false);
                            if (token.getStone() != null && power.getElement() == Elemento.ROCA) {
                                ta1.setDisable(true);
                                token1.setDisable(true);
                                tokenPosition1.setDisable(true);
                            }
                            tokenPosition1.setText("  PS " + token.getPosicion());
                            break;
                        case 2:
                            ta2.setVisible(true);
                            token2.setText(token.getNombre());
                            ta2.setDisable(false);
                            token2.setDisable(false);
                            if (token.getStone() != null && power.getElement() == Elemento.ROCA) {
                                ta2.setDisable(true);
                                token2.setDisable(true);
                                tokenPosition2.setDisable(true);
                            }
                            tokenPosition2.setText("  PS " + token.getPosicion());
                            break;
                        case 3:
                            ta3.setVisible(true);
                            token3.setText(token.getNombre());
                            ta3.setDisable(false);
                            token3.setDisable(false);
                            if (token.getStone() != null && power.getElement() == Elemento.ROCA) {
                                ta3.setDisable(true);
                                token3.setDisable(true);
                                tokenPosition3.setDisable(true);
                            }
                            tokenPosition3.setText("  PS " + token.getPosicion());
                            break;
                        default:
                            break;
                    }
                    cont++;
                }
            }
        }

        tokensMenu.setVisible(true);

        if (computadora != null) {
            jugarComputadora(token1, token2, token3);
        }
    }

    private void almacenarPersonaje(String option, int button) {
        if (!poderActivado) {
            switch (button) {
                case 1:
                    if (this.character2.isDisable() && this.character3.isDisable()) {
                        this.ca1.setDisable(false);
                        this.character1.setDisable(false);
                        this.ca2.setDisable(false);
                        this.character2.setDisable(false);
                        this.ca3.setDisable(false);
                        this.character3.setDisable(false);
                    } else {
                        this.ca1.setDisable(true);
                        this.character1.setDisable(true);
                    }
                    break;
                case 2:
                    if (this.character1.isDisable() && this.character3.isDisable()) {
                        this.ca1.setDisable(false);
                        this.character1.setDisable(false);
                        this.ca2.setDisable(false);
                        this.character2.setDisable(false);
                        this.ca3.setDisable(false);
                        this.character3.setDisable(false);
                    } else {
                        this.ca2.setDisable(true);
                        this.character2.setDisable(true);
                    }
                    break;
                case 3:
                    if (this.character1.isDisable() && this.character2.isDisable()) {
                        this.ca1.setDisable(false);
                        this.character1.setDisable(false);
                        this.ca2.setDisable(false);
                        this.character2.setDisable(false);
                        this.ca3.setDisable(false);
                        this.character3.setDisable(false);
                    } else {
                        this.ca3.setDisable(true);
                        this.character3.setDisable(true);
                    }
                    break;
                default:
                    break;
            }

            option = Normalizer.normalize(option, Normalizer.Form.NFD);
            option = option.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

            for (Personaje character : token.getPersonajes()) {
                if (character.getElemento() == Elemento.valueOf(option)) {
                    attack.setCharacter(character);
                    break;
                }
            }

            charactersMenu.setVisible(false);

            mostrarEnunciadoElementos();
        } else {
            int cont = 0, contFicha = 0;

            option = Normalizer.normalize(option, Normalizer.Form.NFD);
            option = option.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

            for (Personaje character : token.getPersonajes()) {
                if (character.getElemento() == Elemento.valueOf(option)) {
                    power.setCharacter(character);
                    break;
                }

                cont++;
            }

            power.activar();

            token.getPersonajes()[cont] = power.getCharacter();

            int contFuego = 0;

            for (Personaje character : token.getPersonajes()) {
                if (character.getElemento() == Elemento.FUEGO) {
                    character.setConPoder(3);
                    token.getPersonajes()[contFuego] = character;
                    break;
                }

                contFuego++;
            }

            for (Ficha token : board.getFichas()) {
                if (token.getNombre().equals(AtaqueController.token.getNombre())) {
                    board.getFichas()[contFicha] = AtaqueController.token;
                    Tablero.setTableroS(board);
                    break;
                }

                contFicha++;
            }

            poderActivado = false;
            charactersMenu.setVisible(false);

            character1.setDisable(characterDisabled1);
            character2.setDisable(characterDisabled2);
            character3.setDisable(characterDisabled3);

            dialogue.setVisible(true);

            animarTexto("Poder activado satisfactoriamente.", 'a');
        }
    }

    private void almacenarElemento(String option) {
        int cont = 0;
        Personaje personajePtsExtra = obtenerPersonajePtsExtra();

        option = Normalizer.normalize(option, Normalizer.Form.NFD);
        option = option.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        attack.setElementoObjetivo(Elemento.valueOf(option));

        elementsMenu.setVisible(false);

        if (attack.getCharacter().getContPtsExtra() > 0 || (ptsExtraAplicados && attack.getCharacter().getElemento() ==
                personajePtsExtra.getElemento())) {
            if (!ptsExtraAplicados) {
                attack.getCharacter().setContPtsExtra(attack.getCharacter().getContPtsExtra() - 1);
                ptsExtraAplicados = true;

                if (token.getPersonajes()[0].getElemento() == attack.getCharacter().getElemento()) {
                    personajePtsExtraAplicados = character1;
                } else if (token.getPersonajes()[1].getElemento() == attack.getCharacter().getElemento()) {
                    personajePtsExtraAplicados = character2;
                } else if (token.getPersonajes()[2].getElemento() == attack.getCharacter().getElemento()) {
                    personajePtsExtraAplicados = character3;
                }
            }
            attack.ejecutarAtaque(true);
        } else {
            attack.ejecutarAtaque(false);
        }

        Ataque newAttack = new Ataque();
        newAttack.setPiedra(attack.getPiedra());
        token.setStone(attack.getPiedra());
        attack = newAttack;

        for (Ficha token : board.getFichas()) {
            if (token.getNombre().equals(this.token.getNombre())) {
                board.getFichas()[cont] = this.token;
                Tablero.setTableroS(board);
                break;
            }

            cont++;
        }

        actualizarPuntosDeVida();
    }

    private Personaje obtenerPersonajePtsExtra() {
        Personaje personajePtsExtra = new Personaje();

        if (personajePtsExtraAplicados != null) {
            if (personajePtsExtraAplicados == character1) {
                personajePtsExtra = token.getPersonajes()[0];
            } else if (personajePtsExtraAplicados == character2) {
                personajePtsExtra = token.getPersonajes()[1];
            } else if (personajePtsExtraAplicados == character3) {
                personajePtsExtra = token.getPersonajes()[2];
            }
        }

        return personajePtsExtra;
    }

    private void activarPoder(String option) {
        int cont = 0;

        option = Normalizer.normalize(option, Normalizer.Form.NFD);
        option = option.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        power.setElement(Elemento.valueOf(option));

        for (Personaje character : token.getPersonajes()) {
            if (character.getElemento() == Elemento.valueOf(option)) {
                character.setConPoder(1);
                token.getPersonajes()[cont] = character;
                break;
            }

            cont++;
        }

        powersMenu.setVisible(false);

        switch (Elemento.valueOf(option)) {
            case FUEGO:
                dialogue.setVisible(true);

                animarTexto("¿A cuál personaje le desea otorgar los puntos?      \n",
                        '1');
                break;
            case AGUA:
                int contPersonaje = 0, contFicha = 0;

                for (Personaje character : token.getPersonajes()) {
                    if (character.getElemento() == power.getElement()) {
                        character.setConPoder(3);
                        token.getPersonajes()[contPersonaje] = character;
                        break;
                    }

                    contPersonaje++;
                }

                for (Ficha token : board.getFichas()) {
                    if (token.getNombre().equals(AtaqueController.token.getNombre())) {
                        board.getFichas()[contFicha] = AtaqueController.token;
                        Tablero.setTableroS(board);
                        break;
                    }

                    contFicha++;
                }

                throwOption.setDisable(false);

                dialogue.setVisible(true);

                animarTexto("Poder activado satisfactoriamente.", 'a');
                break;
            case PLANTA:
                dialogue.setVisible(true);

                animarTexto("¿A cuál jugador desea no dejar que saque más de tres?      \n", '3');
                break;
            case ELECTRICO:
                dialogue.setVisible(true);

                animarTexto("¿A cuál jugador desea causarle una parálisis?      \n",
                        '4');
                break;
            case ROCA:
                dialogue.setVisible(true);

                animarTexto("¿En cuál jugador desea crear un stone?      \n", '5');
                break;
            case HIELO:
                dialogue.setVisible(true);

                animarTexto("¿A cuál jugador desea congelar?      \n", '6');
                break;
            default:
                break;
        }
    }

    private void almacenarJugador(String option) {
        int contPersonaje = 0, contFichaReceptora = 0, contFichaEmisora = 0;

        for (Personaje character : token.getPersonajes()) {
            if (character.getElemento() == power.getElement()) {
                character.setConPoder(3);
                token.getPersonajes()[contPersonaje] = character;
                break;
            }

            contPersonaje++;
        }

        for (Ficha token : board.getFichas()) {
            if (token.getNombre().equals(option)) {
                power.setToken(token);
                break;
            }
            contFichaReceptora++;
        }

        tokensMenu.setVisible(false);

        power.activar();

        board.getFichas()[contFichaReceptora] = power.getToken();

        for (Ficha token : board.getFichas()) {
            if (token.getNombre().equals(AtaqueController.token.getNombre())) {
                board.getFichas()[contFichaEmisora] = AtaqueController.token;
                Tablero.setTableroS(board);
                break;
            }

            contFichaEmisora++;
        }

        dialogue.setVisible(true);

        animarTexto("Poder activado satisfactoriamente.", 'a');
    }

    private void realizarPostRutinaActivarPoder() {
        if ((permisosActivar <= 0) || (token.getPersonajes()[0].getConPoder() != 0 &&
                token.getPersonajes()[1].getConPoder() != 0 && token.getPersonajes()[2].getConPoder() != 0) ||
                (power1.isDisable() && power2.isDisable() && power3.isDisable()) || (power1.isDisable() &&
                token.getPersonajes()[1].getConPoder() != 0 && token.getPersonajes()[2].getConPoder() != 0) ||
                (power2.isDisable() && token.getPersonajes()[0].getConPoder() != 0 &&
                        token.getPersonajes()[2].getConPoder() != 0) || (power3.isDisable() &&
                token.getPersonajes()[0].getConPoder() != 0 && token.getPersonajes()[1].getConPoder() != 0)) {
            activateOption.setDisable(true);
        }

        if (attackOption.isDisable() && activateOption.isDisable() && throwOption.isDisable()) {
            Stage battleWindow = (Stage) activateOption.getScene().getWindow();
            battleWindow.hide();
        } else {
            permitirUtilizarBotones();

            if (computadora != null) {
                jugarComputadora(attackOption, activateOption, throwOption);
            }
        }
    }

    private void permitirUtilizarBotones() {
        attackOption.setOnMouseClicked(event -> {
            try {
                atacar(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        activateOption.setOnMouseClicked(event -> {
            try {
                activar(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        throwOption.setOnMouseClicked(event -> {
            try {
                tirar(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void procesarPoder(String option, int button) {
        activarPoder(option);
    }

    private void habilitarPoderes(Button btn1, Button btn2) {
        if (btn1.isDisable() && btn2.isDisable()) {
            pa1.setDisable(false);
            power1.setDisable(false);
            pa2.setDisable(false);
            power2.setDisable(false);
            pa3.setDisable(false);
            power3.setDisable(false);
        }
    }

    private void jugarComputadora(Node boton1, Node boton2, Node boton3) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));

        delay.setOnFinished(event -> {
            try {
                computadora.jugarAtaques(boton1, boton2, boton3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        delay.play();
    }

    @FXML
    void almacenarPersonaje1(MouseEvent event) throws Exception {
        String option1 = this.character1.getText().substring(3).toUpperCase();

        almacenarPersonaje(option1, 1);
    }

    @FXML
    void almacenarPersonaje2(MouseEvent event) throws Exception {
        String option2 = this.character2.getText().substring(3).toUpperCase();

        almacenarPersonaje(option2, 2);
    }

    @FXML
    void almacenarPersonaje3(MouseEvent event) throws Exception {
        String option3 = this.character3.getText().substring(3).toUpperCase();

        almacenarPersonaje(option3, 3);
    }

    @FXML
    void almacenarElemento1(MouseEvent event) throws Exception {
        String option1 = this.element1.getText().toUpperCase();

        almacenarElemento(option1);
    }

    @FXML
    void almacenarElemento2(MouseEvent event) throws Exception {
        String option2 = this.element2.getText().toUpperCase();

        almacenarElemento(option2);
    }

    @FXML
    void almacenarElemento3(MouseEvent event) throws Exception {
        String option3 = this.element3.getText().toUpperCase();

        almacenarElemento(option3);
    }

    @FXML
    void activarPoder1(MouseEvent event) throws Exception {
        String option1 = this.power1.getText().substring(3).toUpperCase();

        procesarPoder(option1, 1);
    }

    @FXML
    void activarPoder2(MouseEvent event) throws Exception {
        String option2 = this.power2.getText().substring(3).toUpperCase();

        procesarPoder(option2, 2);
    }

    @FXML
    void activarPoder3(MouseEvent event) throws Exception {
        String option3 = this.power3.getText().substring(3).toUpperCase();

        procesarPoder(option3, 3);
    }

    @FXML
    void almacenarJugador1(MouseEvent event) throws Exception {
        String option1 = this.token1.getText();

        almacenarJugador(option1);
    }

    @FXML
    void almacenarJugador2(MouseEvent event) throws Exception {
        String option2 = this.token2.getText();

        almacenarJugador(option2);
    }

    @FXML
    void almacenarJugador3(MouseEvent event) throws Exception {
        String option3 = this.token3.getText();

        almacenarJugador(option3);
    }
}
