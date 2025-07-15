package xiomara.tareas.presentacion;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xiomara.tareas.TareasApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class SistemaTareasFX extends Application {

    private ConfigurableApplicationContext applicationContext;

    //public static void main(String[] args) {
        //launch();
    //}

    @Override
    public void init() {
        // Metodo inicial antes de start
        // applicationContext = new SpringApplicationBuilder(SistemaEstudiantesApplication.class).run();
        // Ejecutamos la clase de Spring para tener las dependencias listas
        // esto es lo que hacia el main de spring
        applicationContext = new SpringApplicationBuilder(TareasApplication.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(TareasApplication.class.getResource("/templates/index.fxml"));
        //Cargamos los beans de spring a javafx (todas las dependencias)
        loader.setControllerFactory(applicationContext::getBean);
        //Scene escena = new Scene(loader.load(), 800, 800,false, SceneAntialiasing.BALANCED);
        Scene escena = new Scene(loader.load());
        stage.setScene(escena);
        stage.show();
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
}

//aqui se agrego la aplicación de javafx