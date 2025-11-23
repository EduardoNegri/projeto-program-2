package negocio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import modelo.*;
import controller.*;

public class Fachada {
    // ... (Atributos e Construtor mantidos iguais) ...
    private ClienteController clienteController;
    private FuncionarioController funcionarioController;
    private ItemCardapioController itemCardapioController;
    private MesaController mesaController;
    private PedidoController pedidoController;
    private ReservaController reservaController;
    private Relatorio relatorio;

    public Fachada(ClienteController clienteController, FuncionarioController funcionarioController,
                   ItemCardapioController itemCardapioController, MesaController mesaController,
                   PedidoController pedidoController, ReservaController reservaController) {
        this.clienteController = clienteController;
        this.funcionarioController = funcionarioController;
        this.itemCardapioController = itemCardapioController;
        this.mesaController = mesaController;
        this.pedidoController = pedidoController;
        this.reservaController = reservaController;
        this.relatorio = new Relatorio();
    }

    // === Operações de Funcionário ===
    public boolean cadastrarFuncionario(String nome, String cargo, String senha) {
        Funcionario novo = new Funcionario(nome, cargo, senha);
        return funcionarioController.cadastrarFuncionario(novo);
    }

    public Funcionario loginFuncionario(String nome, String senha) {
        return funcionarioController.login(nome, senha);
    }

    // === Relatórios ===
    public void gerarRelatorioVendas(LocalDate inicio, LocalDate fim) {
        relatorio.gerarVendasPorPeriodo(pedidoController.listarTodosPedidos(), inicio, fim);
    }

    public void gerarRelatorioItensMaisVendidos() {
        relatorio.gerarItensMaisVendidos(pedidoController.listarTodosPedidos());
    }

    public boolean cadastrarCliente(String nome, String telefone, String email) { return clienteController.cadastrarCliente(new Cliente(nome, telefone, email)); }
    public boolean cadastrarMesa(int numero, int capacidade) { return mesaController.cadastrarMesa(new Mesa(numero, capacidade, StatusMesa.LIVRE)); }
    public boolean cadastrarItemCardapio(String nome, String desc, double preco, CategoriaItem categoria) { return itemCardapioController.cadastrarItemCardapio(new ItemCardapio(nome, desc, preco, categoria)); }
    public Pedido criarPedido(int numeroMesa, String telefoneCliente) { return pedidoController.criarPedido(numeroMesa, telefoneCliente, mesaController, clienteController); }
    public boolean adicionarItemPedido(int idPedido, String nomeItem, int quantidade) { return pedidoController.adicionarItemAoPedido(idPedido, nomeItem, quantidade, itemCardapioController); }
    public boolean registrarPagamento(int idPedido, MetodoPagamento metodo) { return pedidoController.registrarPagamento(idPedido, metodo, this.mesaController); }
    public Mesa buscarMesa(int numero) { return mesaController.buscarMesaPorNumero(numero); }
}