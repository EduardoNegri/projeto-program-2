package gui.controlador;

import gui.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import modelo.MetodoPagamento;
import negocio.Fachada;

public class FechamentoContaController {

    @FXML
    private Label labelValorTotal;

    @FXML
    private ChoiceBox<MetodoPagamento> choiceMetodo;

    @FXML
    private Button btnPagar;

    private int idPedido;

    @FXML
    public void initialize() {
        // Carrega os métodos de pagamento existentes
        choiceMetodo.getItems().setAll(MetodoPagamento.values());

        // Define ação do botão
        btnPagar.setOnAction(e -> pagar());
    }

    // Chamado ao abrir a tela
    public void setPedido(int idPedido) {
        this.idPedido = idPedido;

        try {
            double total = Fachada.getInstance().listarPedidos()
                                  .stream()
                                  .filter(p -> p.getId() == idPedido)
                                  .findFirst()
                                  .get()
                                  .getValorTotal();

            labelValorTotal.setText(String.format("R$ %.2f", total));

        } catch (Exception e) {
            mostrarErro("Erro ao carregar valor total: " + e.getMessage());
        }
    }

    private void pagar() {
        try {
            MetodoPagamento metodo = choiceMetodo.getValue();

            if (metodo == null) {
                mostrarAviso("Selecione um método de pagamento!");
                return;
            }

            Fachada.getInstance().registrarPagamento(idPedido, metodo);

            mostrarSucesso("Pagamento registrado com sucesso!");

            // Fecha a tela e volta para Gestão de Mesas
            GerenciadorTelas.getInstance().trocarTela("/view/GestaoMesas.fxml", "Gestão de Mesas");

        } catch (Exception e) {
            mostrarErro("Erro ao registrar pagamento: " + e.getMessage());
        }
    }

    private void mostrarSucesso(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Sucesso");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void mostrarAviso(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Aviso");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void mostrarErro(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Erro");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
