package com.template;

import com.template.controller.FabricaController;
import com.template.controller.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FabricaController fabrica = new FabricaController();
        MainController controller = fabrica.criarMainController();
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource(
                        "/com/template/main.fxml"
                )
        );
        loader.setController(controller);
        Scene scene = new Scene(
                loader.load(),
                800,
                600
        );

        stage.setTitle("Cadastro de Players - Valorant");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
