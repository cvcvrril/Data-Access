package ui.Screens.common;

import lombok.Getter;
import ui.Screens.PrincipalController;


@Getter
public abstract class BaseScreenController {

    private PrincipalController principalController;
    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }
    abstract public void principalCargado();
}
