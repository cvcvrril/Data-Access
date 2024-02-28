package ui.primera;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import model.primera.jdbc.Battle;
import service.primera.jdbc.ServiceBattleJ;

import java.time.LocalDate;

public class Exercise2complex {

    /**
     * (JDBC) Insert a new battle, inserting faction if it does not exist.
     * **/

    /**
     * Para insertar objetos, el id tiene que ir a 0 o sino el Hikari se queja de que el id está duplicado.
     * Si ya hay un 0 metido, usar otro número.
     * **/

    public static void main(String[] args) {

        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        ServiceBattleJ service = container.select(ServiceBattleJ.class).get();
        Battle newBattle = new Battle(0, "Prueba", "Prueba", "Empire", "Hoth", LocalDate.now(), 1);
        service.add(newBattle);
    }

}
