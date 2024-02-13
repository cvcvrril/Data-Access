module coffeeteria {

    /*Se requieren*/

    requires javafx.graphics;
    requires javafx.fxml;
    requires io.vavr;
    requires lombok;
    requires org.apache.logging.log4j;
    requires javafx.controls;
    requires jakarta.xml.bind;
    requires org.glassfish.jaxb.runtime;
    requires MaterialFX;
    requires java.logging;
    requires java.sql;
    requires commons.dbcp2;
    requires com.zaxxer.hikari;
    requires jakarta.persistence;
    requires jakarta.inject;
    requires jakarta.annotation;
    requires spring.jdbc;
    requires spring.tx;
    requires jakarta.cdi;
    requires mongo.java.driver;
    requires com.google.gson;
    requires com.sun.el;

    /*Pantallas*/

    exports ui.pantallas.common;
    exports ui.pantallas.login;
    exports ui.pantallas.principal;
    exports ui.pantallas.order.orderupdate;
    exports ui.pantallas.order.orderlist;
    exports ui.pantallas.order.orderdelete;
    exports ui.pantallas.customer.customerupdate;
    exports ui.pantallas.customer.customerdelete;
    exports ui.pantallas.customer.customerlist;
    exports ui.pantallas.welcome;
    exports ui.pantallas.customer.customeradd;
    exports ui.pantallas.order.orderadd;

    /*Otros exports*/

    exports model.errors;
    exports dao.imp;
    exports common;
    exports services;
    exports services.mongo;
    exports model;
    exports ui.main to javafx.graphics;
    exports dao;
    exports dao.db;
    exports dao.mappers;
    exports dao.spring;
    exports dao.hibernate;
    exports dao.converters;
    exports dao.mongo;
    exports dao.connection;
    exports model.xml;
    exports services.mongo.aggregations;
    exports ui.mongo.countries;
    exports ui.mongo.restaurant;
    exports dao.aggregations;

    /*Abrir módulos*/

    opens fxml;
    opens config;
    opens ui.main;
    opens services;
    opens common;
    opens model.mongo;
    opens ui.pantallas.login;
    opens ui.pantallas.principal;
    opens ui.pantallas.welcome;
    opens ui.pantallas.order.orderdelete to javafx.fxml;
    opens ui.pantallas.order.orderlist to javafx.fxml;
    opens ui.pantallas.customer.customerupdate to javafx.fxml;
    opens ui.pantallas.customer.customerlist to javafx.fxml;
    opens ui.pantallas.order.orderupdate to javafx.fxml;
    opens ui.pantallas.customer.customeradd to javafx.fxml;
    opens ui.pantallas.customer.customerdelete to javafx.fxml;
    opens ui.pantallas.order.orderadd to javafx.fxml;
    opens model.xml to jakarta.xml.bind;
    opens model to org.hibernate.orm.core;
}