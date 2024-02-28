package ui;

public class Exercise_ExampleInit {

    public static void main(String[] args) {

        /**Para no inicializar el Dao en el Main, tirar del SeContainer
         *
         * Ejemplo:
         *
         * ----------------------------------------------------------
         *
         * SeContainerInitializer seContainerInitializer = SeContainerInitializer.newInstance();
         * final SeContainer container = seContainerInitializer.initialize();
         * ServicioEjemplo servicio = container.select(ServicioEjemplo.class).get();
         *
         * ----------------------------------------------------------
         *
         * Una vez metido, ya se puede usar el Service sin ningún problema de inyección.
         *
         * **/


    }

}
