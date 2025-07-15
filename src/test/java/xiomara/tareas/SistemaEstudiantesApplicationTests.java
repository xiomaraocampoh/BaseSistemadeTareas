package xiomara.tareas;

import xiomara.tareas.models.Tarea;
import xiomara.tareas.repository.TareaRepositorio;
import org.junit.jupiter.api.Test;
//import org.junit.platform.commons.logging.Logger;
//import org.junit.platform.commons.logging.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SistemaEstudiantesApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(SistemaEstudiantesApplicationTests.class);

	@Autowired
	private TareaRepositorio estudianteRepositorio;

	@Test
	void contextLoads() {
	}

	// Prueba del repositorio estudiante
	@Test
	void pruebaListarEstudiantes(){
		// Listar estudiantes
		logger.info("Listado de Estudiantes...");
		List<Tarea> estudiantes = estudianteRepositorio.findAll();
		estudiantes.forEach((estudiante)->logger.info(estudiante.toString()));
		// codigo de log de junit
		//		for (var estudiante: estudiantes) {
//			logger.info(()->estudiante.toString());
//			logger.info(estudiante::toString);
//		}
	}

	@Test
	void pruebaAgregarEstudiante(){
		var nuevoEstudiante = new Tarea();
		nuevoEstudiante.setNombre("nombre spring");
		nuevoEstudiante.setApellido("apellido spring");
		nuevoEstudiante.setTelefono("telefono spring");
		nuevoEstudiante.setEmail("email spring");
		// Salvamos la nueva entidad
		estudianteRepositorio.save(nuevoEstudiante);
		// codigo de log de junit
		//logger.info(()-> "Estudiante agregado: " + nuevoEstudiante);
		logger.info("Estudiante agregado: " + nuevoEstudiante);
	}

	@Test
	void pruebaModificarEstudiante(){
		// ver por que se debe agregar findBy la implementacion
		var idEstudiante = 38;
		// Primero buscamos el estudiante por id
		var estudiante = estudianteRepositorio.findById(idEstudiante).orElse(null);
		if(estudiante != null){
			estudiante.setNombre("Juan1");
			estudiante.setApellido("Perez2");
			estudiante.setTelefono("3333333333");
			estudiante.setEmail("juan4@mail.com");
			// Salvamos la entidad modificada
			// es el mismo metodo que para agregar o actualizar
			estudianteRepositorio.save(estudiante);
			logger.info("Estudiante modificado: " + estudiante);
		}
		else{
			logger.info("No se encontro el estudiante a modificar: " + idEstudiante);
		}
	}

	@Test
	void pruebaBuscarEstudiantePorNombre(){
		// ver por que se debe agregar findBy la implementacion
		String nombre = "Marce";
		var estudiante = estudianteRepositorio.findByNombre(nombre);
		logger.info("Estudiante encontrado?: " + estudiante);
	}

	@Test
	void pruebaEliminarEstudiante(){
		// Eliminar estudiante, primero lo recuperamos (manejo de objetos)
		var idEstudiante = 80;
		// Primero buscamos el estudiante por id
		var estudiante = estudianteRepositorio.findById(idEstudiante).orElse(null);
		if(estudiante != null){
			estudianteRepositorio.delete(estudiante);
			logger.info("Estudiante eliminado: " + estudiante);
		}
		else{
			logger.info("No se encontro el estudiante a eliminar: " + idEstudiante);
		}
	}

}
