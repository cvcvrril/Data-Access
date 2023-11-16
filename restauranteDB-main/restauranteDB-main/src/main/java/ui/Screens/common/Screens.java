package ui.Screens.common;

import lombok.Getter;

@Getter
public enum Screens {

    PRINCIPAL (ScreenConstants.PRINCIPAL),
    LOGIN (ScreenConstants.LOGIN),
    WELCOME (ScreenConstants.WELCOME),
    CUSTOMERADD (ScreenConstants.CUSTOMERADD),
    CUSTOMERDELETE (ScreenConstants.CUSTOMERDELETE),
    CUSTOMERLIST (ScreenConstants.CUSTOMERLIST),
    CUSTOMERUPDATE (ScreenConstants.CUSTOMERUPDATE),
    ORDERADD (ScreenConstants.ORDERADD),
    ORDERDELETE (ScreenConstants.ORDERDELETE),
    ORDERLIST (ScreenConstants.ORDERLIST),
    ORDERUPDATE (ScreenConstants.ORDERUPDATE);

    private String ruta;
    Screens(String ruta) {
        this.ruta=ruta;
    }


}
