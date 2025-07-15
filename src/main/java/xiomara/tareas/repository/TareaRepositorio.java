package xiomara.tareas.repository;

import xiomara.tareas.models.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepositorio extends JpaRepository<Tarea, Integer> {
}
