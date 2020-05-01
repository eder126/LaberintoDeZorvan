package UI.Controller;

import CL.misc.Ficha;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GanoController implements Initializable {
    @FXML
    private Text ganadorJuego;

    public static Ficha ganador;
    public static TableroController tableroController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ganadorJuego.setText("¡Felicidades, " + ganador.getNombre() + "! Logró escapar.");
    }

    @FXML
    public void reiniciar(MouseEvent event) {
        tableroController.VolverJugar();
        Stage battleWindow = (Stage) ganadorJuego.getScene().getWindow();
        battleWindow.close();
    }

    @FXML
    public void inicio(MouseEvent event) {
        Stage stage = (Stage) ganadorJuego.getScene().getWindow();
        stage.close();
        tableroController.VolverInicio(event);
    }
}
