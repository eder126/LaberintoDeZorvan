package UI.Controller;

import CL.checkpoint.Caretaker;
import CL.misc.Criatura;
import CL.misc.Ficha;
import CL.misc.Observado;
import CL.movimiento.excepciones.ConstructorIncorrectoException;
import CL.movimiento.excepciones.MenorAUnoException;
import CL.movimiento.implementacion.Movimiento;
import CL.tablero.GenerarTablero;
import CL.tablero.Tablero;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class TableroController {

    @FXML
    private ImageView imagenDado;
    @FXML
    private TableView tableJugadores;
    @FXML
    private TableColumn<Ficha, String> columnNombre;
    @FXML
    private TableColumn<Ficha, Integer> columnPosicion;
    @FXML
    private HBox hbox;
    @FXML
    private VBox vj1, vj2, vj3, vj4;
    @FXML
    private Text j1, j2, j3, j4;
    @FXML
    private ToggleButton botonDado, botonSiguiente, hackBtn;
    @FXML
    private Pane turno1, turno2, turno3, turno4;
    @FXML
    private Label detalles;
    @FXML
    private GridPane gameGrid;

    private Caretaker caretaker;

    public void VolverJugar() {
        tableJugadores.getItems().clear();
        tableJugadores.refresh();
        Tablero.getTablero().setMemento(caretaker.getMemento());
        initialize();
    }

    public void VolverInicio(MouseEvent event) {
        try {
            Tablero.setTableroS(null);
            GenerarTablero.getDiablitosPos().clear();
            GenerarTablero.getQuerubinesPos().clear();
            GenerarTablero.getStonesPos().clear();
            Stage storyWindow = (Stage) turno1.getScene().getWindow();
            storyWindow.hide();

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("../FXML/FXMLInicio.fxml"));

            Parent tableViewP = loader.load();

            Scene tableViewS = new Scene(tableViewP);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewS);
            window.setResizable(false);
            window.setAlwaysOnTop(true);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Gano(Ficha ganador) {
        botonDado.setDisable(true);
        botonSiguiente.setDisable(true);
        GanoController.ganador = ganador;
        GanoController.tableroController = this;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/FXMLGano.fxml"));
            Scene sce = new Scene(root);
            Stage stg = new Stage();
            stg.initStyle(StageStyle.UNDECORATED);
            stg.setScene(sce);
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        render();
        TablaJugadores();
        for (Ficha f : Tablero.getTablero().getFichas()) {
            f.addObserver(new Observado(f, this));
        }

        tableJugadores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private Image stoneImg = new Image(getClass().getResource("../img/Stone.png").toExternalForm());
    private Image querubinImg = new Image(getClass().getResource("../img/angelcat.png").toExternalForm());
    private Image diablitoImg = new Image(getClass().getResource("../img/devilcat.png").toExternalForm());
    private Image fichaAz = new Image(getClass().getResource("../img/fichas/fichaAz.png").toExternalForm());
    private Image fichaA = new Image(getClass().getResource("../img/fichas/fichaA.png").toExternalForm());
    private Image fichaR = new Image(getClass().getResource("../img/fichas/fichaR.png").toExternalForm());
    private Image fichaV = new Image(getClass().getResource("../img/fichas/fichaV.png").toExternalForm());
    private Image fichaErr = new Image(getClass().getResource("../img/fichas/fichaError.png").toExternalForm());

    void render() {
        gameGrid.setGridLinesVisible(true);
        int contad = 1;
        for (int i = 0; i < Tablero.getTablero().getCasillas().length; i++) {
            for (int j = 0; j < Tablero.getTablero().getCasillas()[i].length; j++) {
                int[] coordenadas = Tablero.getCoordenadas(contad);
                Pane result = getPane(coordenadas);
                result.getStyleClass().add("borde");
                StackPane stackPane = new StackPane();
                Text txt = new Text(contad + "");
                txt.setTextAlignment(TextAlignment.CENTER);
                txt.setFill(Color.WHITE);
                txt.setVisible(false);
                result.getChildren().clear();
                switch (Tablero.getTablero().getCasillas()[i][j].getCreature()) {
                    case STONE:
                        ImageView stone = new ImageView();
                        stone.setImage(stoneImg);
                        stone.fitWidthProperty().bind(result.widthProperty());
                        stone.fitHeightProperty().bind(result.heightProperty());
                        result.getChildren().add(stone);
                        //txt.setFill(Color.BLACK);
                        break;
                    case VACIA:
                        txt.setVisible(true);
                        break;
                    case QUERUBIN:
                        ImageView querubin = new ImageView();
                        querubin.setImage(querubinImg);
                        querubin.fitWidthProperty().bind(result.widthProperty());
                        querubin.fitHeightProperty().bind(result.heightProperty());
                        result.getChildren().add(querubin);
                        break;
                    case DIABLITO:
                        ImageView diablito = new ImageView();
                        diablito.setImage(diablitoImg);
                        diablito.fitWidthProperty().bind(result.widthProperty());
                        diablito.fitHeightProperty().bind(result.heightProperty());
                        result.getChildren().add(diablito);
                        break;
                    default:
                        break;
                }


                for (Ficha f : Tablero.getTablero().getFichas()) {
                    if (f.getPosicion() == contad) {
                        ImageView jugador = new ImageView();
                        jugador.setImage(getImagenFicha(f.getColor()));
                        //jugador.fitWidthProperty().bind(result.widthProperty());
                        //jugador.fitHeightProperty().bind(result.heightProperty());
                        stackPane.getChildren().add(jugador);
                        stackPane.setAlignment(jugador, getPosFicha(f.getColor()));
                    }
                }
                txt.getStyleClass().add("nums");
                stackPane.getChildren().add(txt);
                stackPane.setAlignment(txt, Pos.CENTER);
                result.getChildren().add(stackPane);
                stackPane.prefWidthProperty().bind(result.widthProperty());
                stackPane.prefHeightProperty().bind(result.heightProperty());
                contad++;
            }
        }
    }

    Image getImagenFicha(Color c) {
        if (Color.BLUE.equals(c)) {
            return fichaAz;
        } else if (Color.RED.equals(c)) {
            return fichaR;
        } else if (Color.GREEN.equals(c)) {
            return fichaV;
        } else if (Color.YELLOW.equals(c)) {
            return fichaA;
        }
        return fichaErr;
    }

    Pos getPosFicha(Color c) {
        if (Color.BLUE.equals(c)) {
            return Pos.TOP_LEFT;
        } else if (Color.RED.equals(c)) {
            return Pos.TOP_RIGHT;
        } else if (Color.GREEN.equals(c)) {
            return Pos.BOTTOM_LEFT;
        } else if (Color.YELLOW.equals(c)) {
            return Pos.BOTTOM_RIGHT;
        }
        return Pos.CENTER;
    }

    @FXML
    void test(ActionEvent event) {
        Ficha f = Tablero.getTablero().getFichas()[new Random().nextInt(Tablero.getTablero().getFichas().length)];
        f.setPosicion(new Random().nextInt(99) + 1);
        render();
    }

    Pane getPane(int coordenadas[]) {
        Pane result = null;
        ObservableList<Node> childrens = gameGrid.getChildren();
        for (Node node : childrens) {
            if (gameGrid.getRowIndex(node) == coordenadas[0] && gameGrid.getColumnIndex(node) == coordenadas[1]) {
                result = (Pane) node;
                break;
            }
        }
        return result;
    }

    /**
     * Continua con el turno del siguiete jugador.
     * Reduce contadores, y muestro lo que el jugador puede realizar.
     *
     * @param event
     */
    @FXML
    public void siguienteTurno(MouseEvent event) {
        sTurno();
    }


    @FXML
    void hack(ActionEvent event) {
        Tablero.getTablero().getFichas()[Tablero.getTablero().getTurno()].setPosicion(98);
        botonDado.setDisable(true);
        botonSiguiente.setDisable(false);
        render();
    }


    private void sTurno() {
        botonDado.setDisable(true);
        botonSiguiente.setDisable(true);
        Tablero.getTablero().getFichas()[Tablero.getTablero().getTurno()].ReducirContadores();
        Tablero.getTablero().siguienteTurno();
        ActualizarJugador();
        VerificarTurno();
    }

    @FXML
    void tirar(MouseEvent event) throws Exception {
        botonDado.setDisable(true);
        botonSiguiente.setDisable(true);
        girarDado();
    }

    private void tirarPC() {
        if (Tablero.getTablero().getFichas()[Tablero.getTablero().getTurno()].getComputadora()) {
            botonDado.setDisable(true);
            botonSiguiente.setDisable(true);
            girarDado();
        }
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
            Tablero.getTablero().getMovementDice().girar();
            imagenDado.setImage(new Image(getClass().getResource(Tablero.getTablero().getMovementDice().imagenDado()).toExternalForm()));
            mover(Tablero.getTablero().getFichas()[Tablero.getTablero().getTurno()], Tablero.getTablero().getMovementDice().getValor());
            botonSiguiente.setDisable(false);
        });
        rt.play();
    }

    private void mover(Ficha f, int num) {
        Tablero t = Tablero.getTablero();
        if (getCriatura(f.getPosicion()) == Criatura.STONE) {

            boolean derrotado = f.getStonesDerrotados().contains(f.getPosicion());

            if (derrotado) {
                f.setPosicion(calcularMovimiento(f.getPosicion(), num));
            }
        } else {
            f.setPosicion(calcularMovimiento(f.getPosicion(), num));
        }

        switch (getCriatura(f.getPosicion())) {
            case VACIA:
                if (f.getPosicion() == 100) {

                }
                tableJugadores.refresh();
                render();
                break;
            case STONE:
                if (!f.getStonesDerrotados().contains(f.getPosicion())) {
                    AtaqueController.token = f;
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../FXML/FXMLAtaque.fxml"));
                        Scene sce = new Scene(root);
                        Stage stg = new Stage();
                        stg.initStyle(StageStyle.UNDECORATED);
                        stg.setScene(sce);
                        stg.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                tableJugadores.refresh();
                render();
                break;
            case DIABLITO:
                render();

                PauseTransition diablitoDelay = new PauseTransition(Duration.seconds(2.5));
                diablitoDelay.setOnFinished(event -> mover(f,-10));
                diablitoDelay.play();
                break;
            case QUERUBIN:
                render();

                PauseTransition querubinDelay = new PauseTransition(Duration.seconds(2.5));
                querubinDelay.setOnFinished(event -> mover(f, 11));
                querubinDelay.play();
                break;
            default:
                tableJugadores.refresh();
                render();
                break;
        }
    }

    public int calcularMovimiento(int posActual, int mover) {
        Movimiento nuevoMov = null;
        try {
            nuevoMov = new Movimiento(posActual, mover);
            nuevoMov.estableceFormula();
            return nuevoMov.getNuevaPos();
        } catch (MenorAUnoException e) {
            e.printStackTrace();
        } catch (ConstructorIncorrectoException e) {
            e.printStackTrace();
        }
        //No debe de llegar acá. Esto es en caso de que por A o por B llegara... Pero no debería.
        return 1;
    }

    public Criatura getCriatura(int pos) {
        int contad = 1;
        for (int i = 0; i < Tablero.getTablero().getCasillas().length; i++) {
            for (int j = 0; j < Tablero.getTablero().getCasillas()[i].length; j++) {
                if (contad == pos) return Tablero.getTablero().getCasillas()[i][j].getCreature();
                contad++;
            }
        }
        return Criatura.VACIA;
    }

    /**
     * (Visual) Muestra al jugador cuyo turno debe jugar.
     */
    public void ActualizarJugador() {
        switch (Tablero.getTablero().getTurno()) {
            case 0:
                turno2.getStyleClass().remove("turno");
                turno3.getStyleClass().remove("turno");
                turno4.getStyleClass().remove("turno");
                turno1.getStyleClass().add("turno");
                break;
            case 1:
                turno1.getStyleClass().remove("turno");
                turno3.getStyleClass().remove("turno");
                turno4.getStyleClass().remove("turno");
                turno2.getStyleClass().add("turno");
                break;
            case 2:
                turno1.getStyleClass().remove("turno");
                turno2.getStyleClass().remove("turno");
                turno4.getStyleClass().remove("turno");
                turno3.getStyleClass().add("turno");
                break;
            case 3:
                turno1.getStyleClass().remove("turno");
                turno2.getStyleClass().remove("turno");
                turno3.getStyleClass().remove("turno");
                turno4.getStyleClass().add("turno");
                break;
            default:
                break;
        }
    }

    /**
     * Inicializa y llena la tabla de jugadores con los datos respectivos
     */
    public void TablaJugadores() {
        tableJugadores.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        tableJugadores.getItems().clear();
        caretaker = new Caretaker();
        caretaker.setMemento(Tablero.getTablero().createMemento());

        columnNombre.setText("Nombre");
        columnNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        columnPosicion.setText("Posición");
        columnPosicion.setCellValueFactory(new PropertyValueFactory<>("posicion"));

        for (Ficha datos : Tablero.getTablero().getFichas()) {
            tableJugadores.getItems().add(datos);
        }

        j1.setText((Tablero.getTablero().getFichas()[0].getNombre()).toUpperCase());
        j2.setText((Tablero.getTablero().getFichas()[1].getNombre()).toUpperCase());

        if (Tablero.getTablero().getFichas().length > 2) {
            vj3.setVisible(true);
            j3.setText((Tablero.getTablero().getFichas()[2].getNombre()).toUpperCase());
        } else {
            hbox.getChildren().remove(vj3);
        }
        if (Tablero.getTablero().getFichas().length > 3) {
            vj4.setVisible(true);
            j4.setText((Tablero.getTablero().getFichas()[3].getNombre()).toUpperCase());
        } else {
            hbox.getChildren().remove(vj4);
        }

        botonDado.setDisable(true);
        botonSiguiente.setDisable(true);
        //Tablero.getTablero().getFichas()[Tablero.getTablero().getTurno()].setContDado(3);
        //Tablero.getTablero().getFichas()[Tablero.getTablero().getTurno()].setContCongelado(2);
        //Tablero.getTablero().getFichas()[Tablero.getTablero().getTurno()].setContParalisis(4);
        turno1.getStyleClass().remove("turno");
        turno2.getStyleClass().remove("turno");
        turno3.getStyleClass().remove("turno");
        turno4.getStyleClass().remove("turno");

        ActualizarJugador();
        VerificarTurno();
    }

    /**
     * El metodo se encarga de revisar que esta afectando al jugador actualmente, en caso de que
     */
    private void VerificarTurno() {
        int opcion = 2;
        int opcionTexto = new Random().nextInt((4 - 1) + 1) + 1;

        Ficha Temp = Tablero.getTablero().getFichas()[Tablero.getTablero().getTurno()];
        String texto = "Turno de " + Temp.getNombre() + ".";

        if (Tablero.getTablero().getMovementDice().getMaxValor() == 3) {
            Tablero.getTablero().getMovementDice().setMaxValor(6);
        }

        if (Temp.getStone() != null) {
            if (Congelado(Temp)) {
                //texto += "\nDebe derrotar a su enemigo, pero lo han congelado.";
                switch (opcionTexto) {
                    case 1:
                        texto += "\nDebe derrotar a su enemigo, pero lo han congelado.";
                        break;
                    case 2:
                        texto += "\nHa caído en una trampa de hielo. No se puede mover.";
                        break;
                    case 3:
                        texto += "\nIntenta atacar a su enemigo, pero el hielo le impide moverse.";
                        break;
                    case 4:
                        texto += "\nSiente el frío en su cuerpo, y se da cuenta que lo han congelado.";
                        break;
                }
                opcion = 1;
            } else {
                if (Paralizado(Temp)) {
                    if ((new Random().nextInt((2 - 1) + 1) + 1) == 1) {
                        //texto += "\nDebe derrotar a su enemigo, pero lo han paralizado.";
                        switch (opcionTexto) {
                            case 1:
                                texto += "\nDebe derrotar a su enemigo, pero lo han paralizado.";
                                break;
                            case 2:
                                texto += "\nNo puede sentir sus piernas. No puede pelear.";
                                break;
                            case 3:
                                texto += "\nEsta inconsciente. No puede pelear.";
                                break;
                            case 4:
                                texto += "\nSu corazón tiene temor. No puede luchar contra su enemigo.";
                                break;
                        }
                        opcion = 1;
                    } else {
                        AtaqueController.token = Temp;
                        //texto += "\nLo han paralizado, pero debe derrotar a su enemigo.";
                        switch (opcionTexto) {
                            case 1:
                                texto += "\nLo han paralizado, pero quiere derrotar a su enemigo.";
                                break;
                            case 2:
                                texto += "\nSe le duerme el cuerpo, pero su valentía lo deja pelear.";
                                break;
                            case 3:
                                texto += "\nSus compañeros lo apoyan en el combate. Puede pelear.";
                                break;
                            case 4:
                                texto += "\nUna parálisis no lo va a detener. Puede pelear.";
                                break;
                        }
                        opcion = 3;
                    }
                } else {
                    AtaqueController.token = Temp;
                    //texto += "\nDebe derrotar a su enemigo.";
                    switch (opcionTexto) {
                        case 1:
                            texto += "\nDebe derrotar a su enemigo. Inicie el combate.";
                            break;
                        case 2:
                            texto += "\nSus compañeros le apoyan. Entran juntos al combate.";
                            break;
                        case 3:
                            texto += "\nSienta la adrenalina correr en su cuerpo. Entre al combate.";
                            break;
                        case 4:
                            texto += "\nEs la hora de terminar la pelea. Entre en combate.";
                            break;
                    }
                    opcion = 3;
                }
            }
        } else if (Congelado(Temp)) {
            //texto += "\nLo han congelado. No puede moverse.";
            switch (opcionTexto) {
                case 1:
                    texto += "\nLo han congelado. No puede moverse.";
                    break;
                case 2:
                    texto += "\nHa caído en una trampa de hielo. No se puede mover.";
                    break;
                case 3:
                    texto += "\nSu corazón quiere seguir, pero su cuerpo está congelado.";
                    break;
                case 4:
                    texto += "\nSiente el frío en su cuerpo, y se da cuenta que lo han congelado.";
                    break;
            }
            opcion = 1;
        } else if (Paralizado(Temp)) {
            if ((new Random().nextInt((2 - 1) + 1) + 1) == 1) {
                //texto += "\nLo han paralizado. No puede moverse.";
                switch (opcionTexto) {
                    case 1:
                        texto += "\nLo han paralizado. No puede moverse.";
                        break;
                    case 2:
                        texto += "\nNo puede sentir sus piernas. No puede moverse.";
                        break;
                    case 3:
                        texto += "\nEstá inconsciente. No puede moverse.";
                        break;
                    case 4:
                        texto += "\nSu corazón tiene temor. No puede moverse.";
                        break;
                }
                opcion = 1;
            } else {
                if (Maldecido(Temp)) {
                    //texto += "\nLo paralizaron y maldicieron, pero puede moverse.";
                    switch (opcionTexto) {
                        case 1:
                            texto += "\nLo paralizaron y maldicieron, pero puede moverse.";
                            break;
                        case 2:
                            texto += "\nSe siente lento y maldito, pero puede avanzar máximo 3 espacios.";
                            break;
                        case 3:
                            texto += "\nEl camino se ha vuelto tormentoso. Puede avanzar máximo 3 espacios.";
                            break;
                        case 4:
                            texto += "\nTiene un calambre. Se podrá mover un máximo de 3 espacios.";
                            break;
                    }
                    Tablero.getTablero().getMovementDice().setMaxValor(3);
                    opcion = 2;
                } else {
                    //texto += "\nLo paralizaron, pero puede moverse.";
                    switch (opcionTexto) {
                        case 1:
                            texto += "\nLo han paralizado, pero puede moverse.";
                            break;
                        case 2:
                            texto += "\nSe le duerme el cuerpo, pero su valentía lo deja avanzar.";
                            break;
                        case 3:
                            texto += "\nSus compañeros le ayudan a moverse. Puede avanzar.";
                            break;
                        case 4:
                            texto += "\nUna parálisis no lo va a detener. Puede avanzar.";
                            break;
                    }
                    opcion = 2;
                }
            }
        } else if (Maldecido(Temp)) {
            //texto += "\nLo han maldecido. No puede sacar mas de 3 en su movimiento.";
            switch (opcionTexto) {
                case 1:
                    texto += "\nLo han maldecido. No puede sacar más de 3 en su movimiento.";
                    break;
                case 2:
                    texto += "\nEs más precavido al moverse. Puede avanzar máximo 3 espacios.";
                    break;
                case 3:
                    texto += "\nEl camino se ha vuelto tormentoso. No puede sacar más de 3 en su movimiento.";
                    break;
                case 4:
                    texto += "\nSe siente agotado. Se podrá mover un máximo de 3 espacios.";
                    break;
            }
            Tablero.getTablero().getMovementDice().setMaxValor(3);
            opcion = 2;
        } else {
            //texto += "\nPuede moverse.";
            switch (opcionTexto) {
                case 1:
                    texto += "\nSu espíritu y valentía le permiten avanzar.";
                    break;
                case 2:
                    texto += "\nNo hay obstáculo que lo detenga. Puede avanzar.";
                    break;
                case 3:
                    texto += "\nSu objetivo es claro. Puede moverse.";
                    break;
                case 4:
                    texto += "\nEl camino está despejado. Puede moverse.";
                    break;
            }
        }
        animarTexto(texto, opcion);
    }

    /**
     * Indica si la ficha esta congelada
     *
     * @param ficha
     * @return
     */
    private boolean Congelado(Ficha ficha) {
        return (ficha.getContCongelado() > 0) ? true : false;
    }

    /**
     * Indica si la ficha esta paralizada
     *
     * @param ficha
     * @return
     */
    private boolean Paralizado(Ficha ficha) {
        return (ficha.getContParalisis() > 0) ? true : false;
    }

    /**
     * Indica si la ficha esta maldecida (No puede sacar mas de 3 en el dado)
     *
     * @param ficha
     * @return
     */
    private boolean Maldecido(Ficha ficha) {
        return (ficha.getContDado() > 0) ? true : false;
    }

    /**
     * Copia sinverguenza y barata del codigo de Miranda de la clase AtaqueController
     *
     * @param contenido
     * @param opcion
     */
    private void animarTexto(String contenido, int opcion) {
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(2000));
            }

            protected void interpolate(double frac) {
                final int length = contenido.length();
                final int n = Math.round(length * (float) frac);
                detalles.setText(contenido.substring(0, n));
            }
        };
        animation.statusProperty().addListener(new ChangeListener<Animation.Status>() {
            @Override
            public void changed(ObservableValue<? extends Animation.Status> observableValue, Animation.Status oldValue,
                                Animation.Status newValue) {
                if (newValue == Animation.Status.STOPPED) {
                    switch (opcion) {
                        case 1:
                            botonDado.setDisable(true);
                            botonSiguiente.setDisable(false);
                            break;
                        case 2:
                            botonDado.setDisable(false);
                            botonSiguiente.setDisable(true);
                            tirarPC();
                            break;
                        case 3:
                            botonDado.setDisable(true);
                            botonSiguiente.setDisable(true);
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("../FXML/FXMLAtaque.fxml"));
                                Scene sce = new Scene(root);
                                Stage stg = new Stage();
                                stg.initStyle(StageStyle.UNDECORATED);
                                stg.setScene(sce);
                                stg.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            botonDado.setDisable(true);
                            botonSiguiente.setDisable(false);
                            break;
                        default:
                            tirarPC();
                            break;
                    }
                }
            }
        });
        animation.play();
    }
}