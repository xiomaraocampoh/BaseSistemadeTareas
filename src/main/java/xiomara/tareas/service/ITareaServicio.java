package xiomara.tareas.service;

import xiomara.tareas.models.Tarea;

import java.util.List;

public interface ITareaServicio {
    public List<Tarea> listarTareas();

    public Tarea buscarTareaPorId(Integer idTarea);

    public void guardarTarea(Tarea Tarea);

    public void eliminarTarea(Tarea Tarea);
}
