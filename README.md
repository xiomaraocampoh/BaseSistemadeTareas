# Base Sistema de Tareas
Una base de aprendizaje para el desarrollo de un sistema de gestión de tareas, implementado en Java. Este repositorio sirve como punto de partida y referencia para futuras expansiones.

## Tabla de Contenidos
- [Acerca del Proyecto](#acerca-del-proyecto)
- [Funcionalidades](#funcionalidades)
- [Primeros Pasos](#primeros-pasos)
- [Uso](#uso)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Contribuciones](#contribuciones)
- [Licencia](#licencia)
- [Contacto](#contacto)

---
---
## Acerca del Proyecto

Este proyecto es una implementación fundamental de un sistema de gestión de tareas. Su principal objetivo es proporcionar una **estructura sólida y un entorno de aprendizaje** para entender los principios básicos de la persistencia de datos y la lógica de negocio en una aplicación Java.

Aunque es una versión inicial, está diseñada para ser fácilmente extensible, permitiendo la adición de características como:

* **Autenticación de Usuarios:** Gestión de usuarios y roles.
* **Estados de Tareas:** Definición de estados (pendiente, en progreso, completada).
* **Asignación de Tareas:** Asignar tareas a diferentes usuarios.
* **Fechas Límite y Recordatorios:** Gestión de plazos.

---
## Tecnologías Usadas

* **Java:** Lenguaje de programación principal (se recomienda **JDK 11** o superior).
* **Maven:** Herramienta para la gestión de dependencias y construcción del proyecto (versión **3.8.x** o superior).
* **Base de Datos:**
    * **H2 Database (Recomendado para desarrollo):** Una base de datos ligera, en memoria o basada en archivo, ideal para desarrollo y pruebas por su facilidad de configuración.
    * **Opcional: MySQL / PostgreSQL:** Si planeas una solución de persistencia más robusta para producción.
* **(Opcional) Spring Boot:** Si estás utilizando este framework para simplificar la configuración y el desarrollo de aplicaciones basadas en Java.
* **(Opcional) Spring Data JPA / Hibernate:** Si estás usando un ORM para la persistencia de datos.
  
---
---
## Funcionalidades

Actualmente, esta base de sistema de tareas incluye las siguientes funcionalidades básicas:

* [ ] **Definición de Entidades:** Estructura de la entidad `Tarea` (título, descripción, fecha).
* [ ] **Conexión a Base de Datos:** Establecimiento de la conexión y configuración de la persistencia.
* [ ] **Operaciones CRUD Básicas:** Ejemplos de cómo **C**rear, **R**eer, **U**pdate (actualizar) y **D**elete (eliminar) tareas en la base de datos (puedes marcar con `[x]` las que ya tengas implementadas).
* [ ] **(Si aplica) Manejo de Excepciones:** Gestión básica de errores en las operaciones de la base de datos.
---
## Primeros Pasos

Para obtener una copia local operativa de este proyecto, sigue estos sencillos pasos.

### Prerrequisitos

Asegúrate de tener instalado:
* **Java Development Kit (JDK) 11 o superior**
* **Apache Maven 3.8.x o superior**

### Instalación y Ejecución

1.  **Clona el repositorio:**
    ```bash
    git clone [https://github.com/xiomaraocampoh/BaseSistemadeTareas.git](https://github.com/xiomaraocampoh/BaseSistemadeTareas.git)
    ```
2.  **Navega al directorio del proyecto:**
    ```bash
    cd BaseSistemadeTareas
    ```
3.  **Compila el proyecto usando Maven:**
    ```bash
    mvn clean install
    ```
4.  **Configura la base de datos:**

    * **Si usas H2 Database (Recomendado para empezar):**
        * No necesitas una configuración compleja. Puedes añadir la dependencia de H2 en tu `pom.xml` y configurar la URL de conexión en `src/main/resources/application.properties` (o el archivo de configuración equivalente) para que sea una base de datos en archivo (persistente) o en memoria.
        * **Ejemplo en `src/main/resources/application.properties` (para Spring Boot):**
            ```properties
            spring.datasource.url=jdbc:h2:file:./data/tareas_db
            spring.datasource.driverClassName=org.h2.Driver
            spring.datasource.username=sa
            spring.datasource.password=
            spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
            spring.jpa.hibernate.ddl-auto=update # o create para que se cree el esquema automáticamente
            ```

    * **Si usas MySQL / PostgreSQL (Opcional):**
        * Asegúrate de tener el servidor de la base de datos instalado y funcionando.
        * Crea una base de datos, por ejemplo, `tareas_db`.
        * Actualiza el archivo de configuración de la base de datos (ej. `src/main/resources/application.properties` o `database.properties`) con tus credenciales.
        * **Ejemplo en `src/main/resources/application.properties` (para MySQL con Spring Boot):**
            ```properties
            spring.datasource.url=jdbc:mysql://localhost:3306/tareas_db?useSSL=false&serverTimezone=UTC
            spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
            spring.datasource.username=tu_usuario_db
            spring.datasource.password=tu_contraseña_db
            spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
            spring.jpa.hibernate.ddl-auto=update
            ```

5.  **Ejecuta la aplicación:**
    * **Si usas Spring Boot:**
        ```bash
        mvn spring-boot:run
        ```
---
## Estructura del Proyecto

* `src/main/java`: Contiene el código fuente principal de la aplicación Java. Aquí encontrarás las clases de entidades (`Tarea.java`), repositorios o DAOs para la interacción con la base de datos, y la lógica de negocio.
* `src/main/resources`: Archivos de configuración de la aplicación (ej. `application.properties` para la conexión a la base de datos) y scripts SQL si los hay.
* `src/test`: Contiene los tests unitarios y de integración para asegurar la funcionalidad del código.
* `.mvn`: Archivos de configuración y scripts wrapper de Maven.
* `pom.xml`: El Project Object Model de Maven, donde se gestionan las dependencias del proyecto, los plugins y la información general del proyecto.
