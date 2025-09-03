package flyingnugget.ui;

import java.io.IOException;

import flyingnugget.FlyingNugget;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for FlyingNugget using FXML.
 */
public class MainApp extends Application {

    private FlyingNugget flyingNugget = new FlyingNugget();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setFlyingNugget(flyingNugget);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
