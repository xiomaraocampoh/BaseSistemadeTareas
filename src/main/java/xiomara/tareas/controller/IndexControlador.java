package xiomara.tareas.controller;

import xiomara.tareas.models.Tarea;
import xiomara.tareas.repository.TareaRepositorio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ResourceBundle;

// Anotacion de spring para que reconozca esta clase como un bean
// y podamos hacer inyeccion de dependencias
@Component
public class IndexControlador implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    //Inyectamos la clase de repositorio
    @Autowired
    private TareaRepositorio tareaRepositorio;

    // Agregamos la tabla y sus columnas
    // No se si es necesario mapear las columnas, pero lo voy a hacer
    // Si no se agregan no se pueden configurar, y por tanto no se pueden relacionar con cada columna de la tabla
    // y la clase tarea y cada atributo
    @FXML
    private TableView<Tarea> tareaTabla;

    @FXML
    private TableColumn<Tarea, Integer> idTareaColumna;

    @FXML
    private TableColumn<Tarea, String> nombreTareaColumna;

    @FXML
    private TableColumn<Tarea, String> responsableColumna;

    @FXML
    private TableColumn<Tarea, String> estatusColumna;

    // Agregamos una lista para agregar a la tabla (tipo observable)
    private final ObservableList<Tarea> tareaLista =
            FXCollections.observableArrayList();

    // Agregamos los componentes de texto (no se agregan en automatico)
    @FXML
    private TextField nombreTareaTexto;

    @FXML
    private TextField responsableTexto;

    @FXML
    private TextField estatusTexto;

    private Integer idTareaInterno;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //logger.info("Url " + url.toString()); ruta del archivo .fxml

        // Configuramos la tabla para seleccion simple
        tareaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Configuramos las columnas para poder trabajar por nombres y no por indices
        // Si no se configurar las columnas, al agregar la lista a la tabla en el metodo listarTareas no funciona
        configurarColumnas();

        //Cargamos los tareas
        listarTareas();

        // Agregamos un listener a la tabla, para que cuando se de click sobre un registro de la tabla
        // carguemos los datos en el formulario (ver si esto se puede configurar desde la vista)
    }

    // Este metodo no se bien que hace o para que se necesita, de momento lo voy a agregar
    // seguramente es para cuando se recupera que no sea por index, sino por nombre
    private void configurarColumnas(){
        idTareaColumna.setCellValueFactory(new PropertyValueFactory<>("idTarea"));
        nombreTareaColumna.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        responsableColumna.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        // Si no se usa el mismo nombre del atributo de la clase Tarea,
        // no carga los datos, no los asocia
        estatusColumna.setCellValueFactory(new PropertyValueFactory<>("estatus"));
    }

    // Este metodo se puede acceder desde el boton, por eso se agrego la anotacion @FXML
    private void listarTareas(){
        // Trabajamos con la lista de tipo observable
        tareaLista.clear(); // Primero limpiamos y volvemos a mostrar
        tareaLista.addAll(tareaRepositorio.findAll());
        // Llenamos la tabla con la lista (deben coincidir los nombres con los atributos de la clase Tarea)
        tareaTabla.setItems(tareaLista);
    }

    // se debe agregar la anotacion fxml? hasta el momento no fue necesaria
    // solo se requiere de esta anotacion si el metodo es privado
    // tampoco se necesita agregar el parametro de event, a menos que uno lo quiera trabajar
    public void agregarTarea() {
        // Al menos el nombre es requerido para agregar
        // no funciono validar texto vacio, funciono usar isEmpty, revisar por que ?
        if(nombreTareaTexto.getText().isEmpty()){
            mostrarMensaje("Error Validacion", "Debe proporcionar un nombre");
            nombreTareaTexto.requestFocus();
            return;
        }
        // Cargamos los datos del formulario
        var tarea = new Tarea();
        recolectarDatosFormulario(tarea);// se pasa por referencia
        // Debido a que vamos a agregar, establecemos manualmente el id como null para que agregue y no modifique (update)
        // Esto se descubrio hasta el caso de modificar, por que solo de agregar no pasa nada
        // pero al modificar, implica dar click sobre algun registro y eso cargaba info en el idTarea
        // por eso lo ponemos como null
        tarea.setIdTarea(null);
        // Agregamos el registro
        tareaRepositorio.save(tarea);
        mostrarMensaje("Información", "Tarea agregado: " + tarea );
        // Limpiamos el formulario
        limpiarFormulario();
        // Recargamos la lista
        listarTareas();
    }

    public void eliminarTarea(){
        // Revisamos el objeto seleccionado de la tabla
        // Recordar que solo permitimos un registro seleccionado (metodo initialize de esta clase)
        var tarea = tareaTabla.getSelectionModel().getSelectedItem();
        if(tarea != null){
            logger.info(tarea.toString());
            tareaRepositorio.delete(tarea);
            mostrarMensaje("Información", "Tarea eliminado: " + tarea);
            // Se limpia el formulario por que si se dio click sobre algun registro se carga la informacion en el
            // formulario, y cuando se elimina se quedaba esa info en el form
            limpiarFormulario();
            listarTareas();
        }
        else{
            mostrarMensaje("Error", "No se ha seleccionado un tarea");
        }
    }

    private void recolectarDatosFormulario(Tarea tarea){
        // Recopilamos los datos del formulario, el id se genera automaticamente en la bd en caso de agregar
        // Establecemos el id solo si existe
        if(idTareaInterno != null)
            tarea.setIdTarea(idTareaInterno);
        // Leemos los datos de los campos del formulario
        tarea.setNombreTarea(nombreTareaTexto.getText());
        tarea.setResponsable(responsableTexto.getText());
        tarea.setEstatus(estatusTexto.getText());
    }

    private void mostrarMensaje(String titulo, String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Si se pone como privado se debe anotar con @FXML, si es publico NO se debe anotar
    // por eso lo cambie a publico, como los metodos de agregar, modifcar, eliminar, etc
    // todos los demas metodos son privados, y si se quiere mantener privado se debe agregar la anotacion @FXML
    public void limpiarFormulario(){
        // Reestablecemos cualquier valor que pudiera tener este campo "oculto" del formulario
        idTareaInterno = null;
        // Limpiamos las cajas de texto del formulario
        nombreTareaTexto.clear();
        responsableTexto.clear();
        estatusTexto.clear();
    }

    // No es necesario pasar el evento mouseEvent se puede eliminar
    public void cargarTareaFormulario() {
        // Obtenemos el registro seleccionado (sin revisar el evento)
        var tarea = tareaTabla.getSelectionModel().getSelectedItem();
        // Cargamos el id tarea solo si es distinto de nulo
        // Se agrego esta validacion por que cuando se toca la tabla (ej. hacer mas grande los campos)
        // o para ordenar las columnas se llama este metodo, y como no hay registro seleccionado
        // el tarea es nulo
        if(tarea != null){
            if(tarea.getIdTarea() != null)
                idTareaInterno = tarea.getIdTarea();
            nombreTareaTexto.setText(tarea.getNombreTarea());
            responsableTexto.setText(tarea.getResponsable());
            estatusTexto.setText(tarea.getEstatus());
        }
    }

    // Se parece mucho a agregar, pero tiene algunas variantes, asi que de momento se deja asi
    // Se pueden unir los metodos de agregar y modificar en uno de guardarTarea
    // y revisar si el idTareaInterno es distinto de nulo, lo demas es igual
    public void modificarTarea(){
        // Revisamos que al menos el nombre tenga un valor
        if(idTareaInterno == null){
            mostrarMensaje("Información", "Debe seleccionar un tarea");
            return;
        }
        if(nombreTareaTexto.getText().isEmpty()){
            mostrarMensaje("Error Validacion", "Debe proporcionar un nombre");
            nombreTareaTexto.requestFocus();
            return;
        }
        // Cargamos los datos del formulario
        var tarea = new Tarea();
        // El idTareaInterno ya deberia ser distinto de nulo por que
        // el metodo anterior cargarDatosFormulario inicializo ese valor
        // por lo tanto el tarea.idTarea deberia ser distinto de nulo
        recolectarDatosFormulario(tarea);// se pasa por referencia
        // Modificamos el registro
        tareaRepositorio.save(tarea);
        // Se llama tarea.toString de manera automatica por la concatenacion
        mostrarMensaje("Información", "Tarea modificado: " + tarea );
        // Limpiamos el formulario
        limpiarFormulario();
        // Recargamos la lista para que se vean los datos modificados
        listarTareas();
    }
}
