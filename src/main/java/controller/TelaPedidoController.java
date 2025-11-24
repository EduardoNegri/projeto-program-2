package gui.controlador;

import gui.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import modelo.ItemPedido;
import modelo.Mesa;
import modelo.Pedido;
import modelo.StatusPedido;
import negocio.Fachada;

public class TelaPedidoController {

    @FXML private Label labelMesa;
    @FXML private Label labelTotal;

    @FXML private TableView<ItemPedido> tabelaItens;
    @FXML private TableColumn<ItemPedido, String> colNome;
    @FXML private TableColumn<ItemPedido, Integer> colQtd;
    @FXML private TableColumn<ItemPedido, Double> colPreco;

    @FXML private Button btnAdicionar;
    @FXML private Button btnPagar;
    @FXML private Button btnVoltar;

    private Fachada fachada;
    private Pedido pedidoAtual;

    @FXML
    public void initialize() {
        fachada = GerenciadorTelas.getInstance().getFachada();

        colNome.setCellValueFactory(c -> c.getValue().nomeProperty());
        colQtd.setCellValueFactory(c -> c.getValue().quantidadeProperty().asObject());
        colPreco.setCellValueFactory(c -> c.getValue().precoProperty().asObject());

        btnAdicionar.setOnAction(e -> abrirCardapio());
        btnPagar.setOnAction(e -> abrirFechamentoConta());
        btnVoltar.setOnAction(e -> voltar());
    }

    public void carregarMesa(Mesa mesa) {
        labelMesa.setText("Mesa " + mesa.getNumero());

        // Verifica se mesa já tem pedido aberto
        for (Pedido p : fachada.listarPedidos()) {
            if (p.getNumeroMesa() == mesa.getNumero()
                    && p.getStatus() != StatusPedido.ENTREGUE
                    && p.getStatus() != StatusPedido.CANCELADO) {

                pedidoAtual = p;
                break;
            }
        }

        // Se não existir, cria
        if (pedidoAtual == null) {
            pedidoAtual = fachada.criarPedidoParaMesa(mesa.getNumero());
        }

        atualizarTabela();
    }

    private void atualizarTabela() {
        tabelaItens.getItems().setAll(pedidoAtual.getItens());
        labelTotal.setText(String.format("R$ %.2f", pedidoAtual.getValorTotal()));
    }

    private void abrirCardapio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VisualizacaoCardapio.fxml"));
            Parent root = loader.load();

            // passa o pedido para o controller do cardápio
            VisualizacaoCardapioController controller = loader.getController();
            controller.setPedido(pedidoAtual);

            GerenciadorTelas.getInstance().getStagePrincipal().setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirFechamentoConta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FechamentoConta.fxml"));
            Parent root = loader.load();

            FechamentoContaController controller = loader.getController();
            controller.setPedido(pedidoAtual.getId());

            GerenciadorTelas.getInstance().getStagePrincipal().setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void voltar() {
        GerenciadorTelas.getInstance().trocarTela("/view/GestaoMesas.fxml", "Gestão de Mesas");
    }
}
