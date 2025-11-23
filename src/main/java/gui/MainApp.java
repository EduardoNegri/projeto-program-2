package gui;

import javafx.application.Application;
import javafx.stage.Stage;
import negocio.Fachada;
import repositorio.*;
import controller.*;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // 1. Inicialização do Sistema (Repositórios)
            IRepositorioCliente repoCliente = new RepositorioClienteArray();
            IRepositorioReserva repoReserva = new RepositorioReservaArray();
            IRepositorioMesa repoMesa = new RepositorioMesaArray();
            IRepositorioPedido repoPedido = new RepositorioPedidoArray();
            IRepositorioItemCardapio repoItem = new RepositorioItemCardapioArray();
            IRepositorioFuncionario repoFunc = new RepositorioFuncionarioArray();

            // 2. Inicialização dos Controllers
            ClienteController clienteController = new ClienteController(repoCliente);
            FuncionarioController funcionarioController = new FuncionarioController(repoFunc);
            ItemCardapioController itemCardapioController = new ItemCardapioController(repoItem);
            MesaController mesaController = new MesaController(repoMesa);
            PedidoController pedidoController = new PedidoController(repoPedido);
            ReservaController reservaController = new ReservaController(repoReserva);

            // 3. Inicialização da Fachada
            Fachada fachada = new Fachada(
                    clienteController,
                    funcionarioController,
                    itemCardapioController,
                    mesaController,
                    pedidoController,
                    reservaController
            );

            // Cadastra algumas mesas
            fachada.cadastrarMesa(1, 4);
            fachada.cadastrarMesa(2, 2);

            try {
                fachada.cadastrarFuncionario("Admin", "Gerente", "1234");
            } catch (Exception e) {
                System.err.println("Erro ao cadastrar funcionário inicial: " + e.getMessage());
            }

            GerenciadorTelas.getInstance().inicializar(primaryStage, fachada);

            GerenciadorTelas.getInstance().trocarTela("/view/Login.fxml", "Login - Restaurante");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}