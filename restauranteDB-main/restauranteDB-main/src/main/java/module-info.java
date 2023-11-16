module restaurante {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.apache.logging.log4j;
    requires jakarta.inject;
    requires jakarta.cdi;
    requires jakarta.xml.bind;
    requires io.vavr;
    requires java.sql;
    requires com.zaxxer.hikari;

    exports ui.main to javafx.graphics;
    exports model to javafx.graphics;
    exports ui.Screens;
    exports ui.Screens.common;
    exports common;
    exports dao;
    exports services;
    exports dao.implJDBC;
    exports ui.Screens.CustomerScreens;
    exports ui.Screens.OrderScreens;
    exports model.xml;

    opens ui.Screens to javafx.fxml;
    opens ui.main;
    opens fxml;
    opens model;
    opens common;
    opens services;
    opens dao.implJDBC;
    opens configFiles;
    opens ui.Screens.CustomerScreens to javafx.fxml;
    opens ui.Screens.OrderScreens to javafx.fxml;
    opens model.xml to jakarta.xml.bind;
}
