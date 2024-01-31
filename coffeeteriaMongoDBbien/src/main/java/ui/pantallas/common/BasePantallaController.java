package ui.pantallas.common;


import lombok.Data;
import ui.pantallas.principal.PrincipalController;

@Data
public abstract class BasePantallaController {

    private PrincipalController principalController;

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    abstract public void principalCargado();
}
