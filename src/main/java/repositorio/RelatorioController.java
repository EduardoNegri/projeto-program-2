package com.example.sistemapedidosfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import java.time.LocalDate;

public class RelatoriosController {

    // 1. Conexões com os componentes do FXML (fx:id)
    @FXML private DatePicker dataInicioPicker;
    @FXML private DatePicker dataFimPicker;
    @FXML private TextArea txtAreaRelatorios;
    @FXML private Button btnRelatorioVendas;
    @FXML private Button btnItensMaisVendidos;

    // Instância da Fachada para chamar a lógica de negócio
    private final Fachada fachada = new Fachada();

    // 2. Método de Ação para o botão "Gerar Relatório de Vendas"
    @FXML
    private void gerarRelatorioVendasAction() {
        LocalDate dataInicio = dataInicioPicker.getValue();
        LocalDate dataFim = dataFimPicker.getValue();

        if (dataInicio == null || dataFim == null) {
            txtAreaRelatorios.setText("ERRO: Por favor, selecione as duas datas.");
            return;
        }

        // Chama a Fachada e exibe o resultado no TextArea (Conclui o item 3 do checklist)
        String relatorio = fachada.gerarRelatorioVendas(dataInicio, dataFim);
        txtAreaRelatorios.setText(relatorio);
    }

    // 3. Método de Ação para o botão "Itens Mais Vendidos"
    @FXML
    private void gerarItensMaisVendidosAction() {
        LocalDate dataInicio = dataInicioPicker.getValue();
        LocalDate dataFim = dataFimPicker.getValue();

        if (dataInicio == null || dataFim == null) {
            txtAreaRelatorios.setText("ERRO: Por favor, selecione as duas datas.");
            return;
        }

        // Chama a Fachada e exibe o resultado no TextArea (Conclui o item 4 do checklist)
        String relatorio = fachada.gerarRelatorioItensMaisVendidos(dataInicio, dataFim);
        txtAreaRelatorios.setText(relatorio);
    }
}
