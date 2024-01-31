package ui.pantallas.common;

public enum Pantallas {

    /*MAIN*/
    PRINCIPAL("/fxml/principal.fxml"),      //base de todas las pantallas, no tocar
    LOGIN("/fxml/login.fxml"),
    WELCOME("/fxml/welcome.fxml"),

    /*CUSTOMERS*/
    CUS_LIST("/fxml/customer-list.fxml"),
    CUS_ADD("/fxml/add-customer.fxml"),
    CUS_UPDATE("/fxml/update-customer.fxml"),
    CUS_DEL("/fxml/del-customer.fxml"),

    /*ORDERS*/
    OR_LIST("/fxml/order-list.fxml"),
    OR_ADD("/fxml/add-order.fxml"),
    OR_UPDATE("/fxml/update-order.fxml"),
    OR_DEL("/fxml/del-order.fxml");

    private String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }


}
