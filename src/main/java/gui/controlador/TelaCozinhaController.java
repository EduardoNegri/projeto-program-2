package gui.controlador;

import negocio.Fachada;

public class TelaCozinhaController implements IControlador {

    private Fachada fachada;

    @Override
    public void setFachada(Fachada fachada) {
        this.fachada = fachada;
    }

    // Futuramente, métodos para carregar pedidos em aberto aqui
}