package xiomara.tareas;

import xiomara.tareas.presentacion.SistemaTareasFX;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TareasApplication {

	public static void main(String[] args) {
		//SpringApplication.run(SistemaEstudiantesApplication.class, args);
		Application.launch(SistemaTareasFX.class, args);
	}


}
