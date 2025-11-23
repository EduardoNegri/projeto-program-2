package gui.controlador;

import gui.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField; // Importante
import javafx.scene.control.TextField;
import modelo.Funcionario;
import negocio.Fachada;

public class LoginController implements IControlador {

    private Fachada fachada;

    @FXML
    private TextField campoNome;

    @FXML
    private PasswordField campoSenha; // Novo campo

    @Override
    public void setFachada(Fachada fachada) {
        this.fachada = fachada;
    }

    @FXML
    public void fazerLogin() {
        String nome = campoNome.getText();
        String senha = campoSenha.getText();

        if (nome == null || nome.isEmpty() || senha == null || senha.isEmpty()) {
            mostrarAlerta("Atenção", "Preencha usuário e senha.");
            return;
        }

        Funcionario funcionarioLogado = fachada.loginFuncionario(nome, senha);

        if (funcionarioLogado != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bem-vindo");
            alert.setHeaderText("Login realizado com sucesso!");
            alert.setContentText("Olá, " + funcionarioLogado.getNome());
            alert.showAndWait();

            GerenciadorTelas.getInstance().trocarTela("/view/TelaPrincipal.fxml", "Menu Principal");
        } else {
            mostrarAlerta("Erro de Login", "Usuário ou senha incorretos.");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}