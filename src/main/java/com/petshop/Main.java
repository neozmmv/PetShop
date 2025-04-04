package com.petshop;

import java.sql.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private List<Cliente> clientes = new ArrayList<>();
    private List<Pet> pets = new ArrayList<>();
    private List<Servico> servicos = new ArrayList<>();
    private List<PacoteServicos> pacotes = new ArrayList<>();

    // Método original main
    public static void main(String[] args) {
        // Inicia a aplicação JavaFX
        launch(args);
    }

    // Código original do Main
    public static void executarCodigoOriginal() {
        try {
            //ler o arquivo com as configs do banco
            FileReader fileReader = new FileReader("server.conf");
            Connect connection = new Connect();
            connection.setUser(fileReader.get_User());
            connection.setPassword(fileReader.get_Password());
            connection.mount_Url(fileReader.get_Host(), fileReader.get_Port(), fileReader.get_Database());

            System.out.println(connection.getConnection());

            int test = connection.getIntByQuery("SELECT id from tabela2 where nome = 'Joao';");
            System.out.println("VALOR DE TEST:" + test);

            String nome = connection.getStringByQuery("Select nome from tabela2 where id = 1");
            System.out.println("VALOR DE NOME: " + nome);

            connection.executeUpdate("(COMANDO PARA INSERIR ALGO NO BANCO)", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Novo código JavaFX
    @Override
    public void start(Stage primaryStage) {
        // Executa o código original em uma thread separada
        new Thread(() -> executarCodigoOriginal()).start();

        primaryStage.setTitle("Sistema PetShop");

        // Menu Principal
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        Label titulo = new Label("Sistema de Gerenciamento PetShop");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Botões principais
        Button btnCliente = new Button("Gerenciar Clientes");
        Button btnPet = new Button("Gerenciar Pets");
        Button btnServico = new Button("Agendar Serviços");
        Button btnPacotes = new Button("Gerenciar Pacotes");
        Button btnRelatorios = new Button("Relatórios");
        Button btnSair = new Button("Sair");

        // Estilização dos botões
        String buttonStyle = "-fx-font-size: 14px; -fx-min-width: 200px; -fx-min-height: 40px;";
        btnCliente.setStyle(buttonStyle);
        btnPet.setStyle(buttonStyle);
        btnServico.setStyle(buttonStyle);
        btnPacotes.setStyle(buttonStyle);
        btnRelatorios.setStyle(buttonStyle);
        btnSair.setStyle(buttonStyle);

        mainLayout.getChildren().addAll(titulo, btnCliente, btnPet, btnServico, btnPacotes, btnRelatorios, btnSair);

        // Ações dos botões
        btnCliente.setOnAction(e -> abrirTelaClientes());
        btnPet.setOnAction(e -> abrirTelaPets());
        btnServico.setOnAction(e -> abrirTelaServicos());
        btnPacotes.setOnAction(e -> abrirTelaPacotes());
        btnRelatorios.setOnAction(e -> abrirTelaRelatorios());
        btnSair.setOnAction(e -> primaryStage.close());

        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void abrirTelaClientes() {
        Stage stage = new Stage();
        stage.setTitle("Gerenciamento de Clientes");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome do Cliente");
        TextField txtCpf = new TextField();
        txtCpf.setPromptText("CPF");
        TextField txtTelefone = new TextField();
        txtTelefone.setPromptText("Telefone");
        TextField txtEndereco = new TextField();
        txtEndereco.setPromptText("Endereço");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");

        Button btnSalvar = new Button("Salvar Cliente");
        Button btnListar = new Button("Listar Clientes");
        Button btnVoltar = new Button("Voltar");

        btnSalvar.setOnAction(e -> {
            try {
                Cliente novoCliente = new Cliente(
                    txtNome.getText(),
                    txtCpf.getText(),
                    txtTelefone.getText(),
                    txtEndereco.getText()
                );
                novoCliente.setEmail(txtEmail.getText());
                clientes.add(novoCliente);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setContentText("Cliente cadastrado com sucesso!");
                alert.showAndWait();
                
                limparCampos(txtNome, txtCpf, txtTelefone, txtEndereco, txtEmail);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Erro ao cadastrar cliente: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        btnListar.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Lista de Clientes:\n\n");
            for (Cliente cliente : clientes) {
                sb.append(cliente.toString()).append("\n\n");
            }
            
            TextArea textArea = new TextArea(sb.toString());
            textArea.setEditable(false);
            textArea.setPrefRowCount(10);
            
            Stage listStage = new Stage();
            listStage.setTitle("Lista de Clientes");
            VBox listLayout = new VBox(10);
            listLayout.setPadding(new Insets(20));
            listLayout.getChildren().add(textArea);
            
            Scene listScene = new Scene(listLayout, 400, 300);
            listStage.setScene(listScene);
            listStage.show();
        });

        btnVoltar.setOnAction(e -> stage.close());

        layout.getChildren().addAll(
            new Label("Cadastro de Cliente"),
            txtNome,
            txtCpf,
            txtTelefone,
            txtEndereco,
            txtEmail,
            btnSalvar,
            btnListar,
            btnVoltar
        );

        Scene scene = new Scene(layout, 400, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void abrirTelaPets() {
        Stage stage = new Stage();
        stage.setTitle("Gerenciamento de Pets");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome do Pet");
        TextField txtRaca = new TextField();
        txtRaca.setPromptText("Raça");
        TextField txtPeso = new TextField();
        txtPeso.setPromptText("Peso");
        TextField txtIdade = new TextField();
        txtIdade.setPromptText("Idade");
        TextField txtEspecie = new TextField();
        txtEspecie.setPromptText("Espécie");
        ComboBox<Cliente> cboDono = new ComboBox<>();
        cboDono.setPromptText("Selecione o Dono");

        Button btnSalvar = new Button("Salvar Pet");
        Button btnListar = new Button("Listar Pets");
        Button btnVoltar = new Button("Voltar");

        // Atualiza a lista de clientes no ComboBox
        cboDono.getItems().addAll(clientes);

        btnSalvar.setOnAction(e -> {
            try {
                Pet novoPet = new Pet(
                    txtNome.getText(),
                    txtRaca.getText(),
                    Double.parseDouble(txtPeso.getText()),
                    Integer.parseInt(txtIdade.getText())
                );
                novoPet.setEspecie(txtEspecie.getText());
                
                Cliente donoSelecionado = cboDono.getValue();
                if (donoSelecionado != null) {
                    novoPet.setDono(donoSelecionado);
                    donoSelecionado.adicionarPet(novoPet);
                }
                
                pets.add(novoPet);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setContentText("Pet cadastrado com sucesso!");
                alert.showAndWait();
                
                limparCampos(txtNome, txtRaca, txtPeso, txtIdade, txtEspecie);
                cboDono.setValue(null);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Erro ao cadastrar pet: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        btnListar.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Lista de Pets:\n\n");
            for (Pet pet : pets) {
                sb.append(pet.toString()).append("\n\n");
            }
            
            TextArea textArea = new TextArea(sb.toString());
            textArea.setEditable(false);
            textArea.setPrefRowCount(10);
            
            Stage listStage = new Stage();
            listStage.setTitle("Lista de Pets");
            VBox listLayout = new VBox(10);
            listLayout.setPadding(new Insets(20));
            listLayout.getChildren().add(textArea);
            
            Scene listScene = new Scene(listLayout, 400, 300);
            listStage.setScene(listScene);
            listStage.show();
        });

        btnVoltar.setOnAction(e -> stage.close());

        layout.getChildren().addAll(
            new Label("Cadastro de Pet"),
            txtNome,
            txtRaca,
            txtPeso,
            txtIdade,
            txtEspecie,
            cboDono,
            btnSalvar,
            btnListar,
            btnVoltar
        );

        Scene scene = new Scene(layout, 400, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void abrirTelaServicos() {
        Stage stage = new Stage();
        stage.setTitle("Agendamento de Serviços");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        ComboBox<String> cboTipoServico = new ComboBox<>();
        cboTipoServico.getItems().addAll("Banho e Tosa", "Consulta Veterinária", "Hospedagem", "Adestramento");
        cboTipoServico.setPromptText("Selecione o serviço");

        ComboBox<Cliente> cboCliente = new ComboBox<>();
        cboCliente.setPromptText("Selecione o Cliente");
        cboCliente.getItems().addAll(clientes);

        ComboBox<Pet> cboPet = new ComboBox<>();
        cboPet.setPromptText("Selecione o Pet");

        // Atualiza a lista de pets quando um cliente é selecionado
        cboCliente.setOnAction(e -> {
            Cliente clienteSelecionado = cboCliente.getValue();
            if (clienteSelecionado != null) {
                cboPet.getItems().clear();
                cboPet.getItems().addAll(clienteSelecionado.getPets());
            }
        });

        TextField txtPreco = new TextField();
        txtPreco.setPromptText("Preço Base");
        DatePicker dtpData = new DatePicker();
        dtpData.setPromptText("Data do Serviço");

        // Campos específicos para Banho e Tosa
        CheckBox chkTosaProfunda = new CheckBox("Tosa Profunda");
        CheckBox chkPerfume = new CheckBox("Perfume");
        CheckBox chkLacoGravata = new CheckBox("Laço/Gravata");

        // Campos específicos para Consulta
        TextField txtVeterinario = new TextField();
        txtVeterinario.setPromptText("Veterinário");
        CheckBox chkUrgente = new CheckBox("Consulta Urgente");

        // Campos específicos para Hospedagem
        CheckBox chkSuiteLuxo = new CheckBox("Suíte Luxo");
        CheckBox chkServicoSpa = new CheckBox("Serviço Spa");
        CheckBox chkAlimentacaoEspecial = new CheckBox("Alimentação Especial");
        TextField txtValorDiaria = new TextField();
        txtValorDiaria.setPromptText("Valor da Diária");

        // Campos específicos para Adestramento
        TextField txtNumeroSessoes = new TextField();
        txtNumeroSessoes.setPromptText("Número de Sessões");
        TextField txtTipoTreinamento = new TextField();
        txtTipoTreinamento.setPromptText("Tipo de Treinamento");
        CheckBox chkAtendimentoDomiciliar = new CheckBox("Atendimento Domiciliar");

        Button btnCalcular = new Button("Calcular Preço");
        Button btnAgendar = new Button("Agendar Serviço");
        Button btnVoltar = new Button("Voltar");

        btnCalcular.setOnAction(e -> {
            try {
                Servico servico = criarServico(
                    cboTipoServico.getValue(),
                    cboCliente.getValue(),
                    cboPet.getValue(),
                    Double.parseDouble(txtPreco.getText()),
                    dtpData.getValue().atStartOfDay(),
                    chkTosaProfunda.isSelected(),
                    chkPerfume.isSelected(),
                    chkLacoGravata.isSelected(),
                    txtVeterinario.getText(),
                    chkUrgente.isSelected(),
                    chkSuiteLuxo.isSelected(),
                    chkServicoSpa.isSelected(),
                    chkAlimentacaoEspecial.isSelected(),
                    Double.parseDouble(txtValorDiaria.getText()),
                    Integer.parseInt(txtNumeroSessoes.getText()),
                    txtTipoTreinamento.getText(),
                    chkAtendimentoDomiciliar.isSelected()
                );

                if (servico != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Preço Calculado");
                    alert.setContentText("Preço total do serviço: R$ " + servico.calcularPreco());
                    alert.showAndWait();
                }
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Erro ao calcular preço: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        btnAgendar.setOnAction(e -> {
            try {
                Servico servico = criarServico(
                    cboTipoServico.getValue(),
                    cboCliente.getValue(),
                    cboPet.getValue(),
                    Double.parseDouble(txtPreco.getText()),
                    dtpData.getValue().atStartOfDay(),
                    chkTosaProfunda.isSelected(),
                    chkPerfume.isSelected(),
                    chkLacoGravata.isSelected(),
                    txtVeterinario.getText(),
                    chkUrgente.isSelected(),
                    chkSuiteLuxo.isSelected(),
                    chkServicoSpa.isSelected(),
                    chkAlimentacaoEspecial.isSelected(),
                    Double.parseDouble(txtValorDiaria.getText()),
                    Integer.parseInt(txtNumeroSessoes.getText()),
                    txtTipoTreinamento.getText(),
                    chkAtendimentoDomiciliar.isSelected()
                );

                if (servico != null) {
                    servicos.add(servico);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucesso");
                    alert.setContentText("Serviço agendado com sucesso!");
                    alert.showAndWait();
                    stage.close();
                }
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Erro ao agendar serviço: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        btnVoltar.setOnAction(e -> stage.close());

        layout.getChildren().addAll(
            new Label("Agendamento de Serviço"),
            cboTipoServico,
            cboCliente,
            cboPet,
            txtPreco,
            dtpData,
            chkTosaProfunda,
            chkPerfume,
            chkLacoGravata,
            txtVeterinario,
            chkUrgente,
            chkSuiteLuxo,
            chkServicoSpa,
            chkAlimentacaoEspecial,
            txtValorDiaria,
            txtNumeroSessoes,
            txtTipoTreinamento,
            chkAtendimentoDomiciliar,
            btnCalcular,
            btnAgendar,
            btnVoltar
        );

        Scene scene = new Scene(layout, 400, 600);
        stage.setScene(scene);
        stage.show();
    }

    private Servico criarServico(String tipo, Cliente cliente, Pet pet, double preco, 
                                LocalDateTime data, boolean tosaProfunda, boolean perfume, 
                                boolean lacoGravata, String veterinario, boolean urgente,
                                boolean suiteLuxo, boolean servicoSpa, boolean alimentacaoEspecial,
                                double valorDiaria, int numeroSessoes, String tipoTreinamento,
                                boolean atendimentoDomiciliar) {
        if (cliente == null || pet == null) {
            throw new IllegalArgumentException("Cliente e Pet são obrigatórios");
        }

        switch (tipo) {
            case "Banho e Tosa":
                return new BanhoETosa("Banho e Tosa", preco, data, cliente, pet, 
                                     tosaProfunda, perfume, lacoGravata);
            case "Consulta Veterinária":
                return new Consulta("Consulta Veterinária", preco, data, cliente, pet,
                                  veterinario, urgente);
            case "Hospedagem":
                return new Hospedagem("Hospedagem", preco, data, cliente, pet,
                                    suiteLuxo, servicoSpa, alimentacaoEspecial, valorDiaria);
            case "Adestramento":
                return new Adestramento("Adestramento", preco, data, cliente, pet,
                                      numeroSessoes, tipoTreinamento, atendimentoDomiciliar);
            default:
                return null;
        }
    }

    private void abrirTelaPacotes() {
        Stage stage = new Stage();
        stage.setTitle("Gerenciamento de Pacotes");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome do Pacote");
        TextField txtDesconto = new TextField();
        txtDesconto.setPromptText("Percentual de Desconto (%)");

        ComboBox<Cliente> cboCliente = new ComboBox<>();
        cboCliente.setPromptText("Selecione o Cliente");
        cboCliente.getItems().addAll(clientes);

        ComboBox<Pet> cboPet = new ComboBox<>();
        cboPet.setPromptText("Selecione o Pet");

        // Atualiza a lista de pets quando um cliente é selecionado
        cboCliente.setOnAction(e -> {
            Cliente clienteSelecionado = cboCliente.getValue();
            if (clienteSelecionado != null) {
                cboPet.getItems().clear();
                cboPet.getItems().addAll(clienteSelecionado.getPets());
            }
        });

        ListView<Servico> lstServicos = new ListView<>();
        lstServicos.setPrefHeight(200);

        Button btnAdicionarServico = new Button("Adicionar Serviço");
        Button btnRemoverServico = new Button("Remover Serviço");
        Button btnSalvar = new Button("Salvar Pacote");
        Button btnListar = new Button("Listar Pacotes");
        Button btnVoltar = new Button("Voltar");

        btnAdicionarServico.setOnAction(e -> {
            abrirTelaServicosParaPacote(lstServicos);
        });

        btnRemoverServico.setOnAction(e -> {
            Servico servicoSelecionado = lstServicos.getSelectionModel().getSelectedItem();
            if (servicoSelecionado != null) {
                lstServicos.getItems().remove(servicoSelecionado);
            }
        });

        btnSalvar.setOnAction(e -> {
            try {
                Cliente cliente = cboCliente.getValue();
                Pet pet = cboPet.getValue();
                double desconto = Double.parseDouble(txtDesconto.getText());

                if (cliente == null || pet == null || lstServicos.getItems().isEmpty()) {
                    throw new IllegalArgumentException("Preencha todos os campos obrigatórios");
                }

                PacoteServicos pacote = new PacoteServicos(txtNome.getText(), desconto, cliente, pet);
                
                for (Servico servico : lstServicos.getItems()) {
                    pacote.adicionarServico(servico);
                }

                pacotes.add(pacote);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setContentText("Pacote criado com sucesso! Preço total: R$ " + pacote.calcularPrecoTotal());
                alert.showAndWait();
                
                stage.close();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Erro ao criar pacote: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        btnListar.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Lista de Pacotes:\n\n");
            for (PacoteServicos pacote : pacotes) {
                sb.append(pacote.toString()).append("\n\n");
            }
            
            TextArea textArea = new TextArea(sb.toString());
            textArea.setEditable(false);
            textArea.setPrefRowCount(10);
            
            Stage listStage = new Stage();
            listStage.setTitle("Lista de Pacotes");
            VBox listLayout = new VBox(10);
            listLayout.setPadding(new Insets(20));
            listLayout.getChildren().add(textArea);
            
            Scene listScene = new Scene(listLayout, 400, 300);
            listStage.setScene(listScene);
            listStage.show();
        });

        btnVoltar.setOnAction(e -> stage.close());

        layout.getChildren().addAll(
            new Label("Criação de Pacote de Serviços"),
            txtNome,
            txtDesconto,
            cboCliente,
            cboPet,
            new Label("Serviços do Pacote:"),
            lstServicos,
            btnAdicionarServico,
            btnRemoverServico,
            btnSalvar,
            btnListar,
            btnVoltar
        );

        Scene scene = new Scene(layout, 400, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void abrirTelaServicosParaPacote(ListView<Servico> lstServicos) {
        Stage stage = new Stage();
        stage.setTitle("Adicionar Serviço ao Pacote");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        ListView<Servico> lstServicosDisponiveis = new ListView<>();
        lstServicosDisponiveis.setPrefHeight(200);
        lstServicosDisponiveis.getItems().addAll(servicos);

        Button btnAdicionar = new Button("Adicionar ao Pacote");
        Button btnCancelar = new Button("Cancelar");

        btnAdicionar.setOnAction(e -> {
            Servico servicoSelecionado = lstServicosDisponiveis.getSelectionModel().getSelectedItem();
            if (servicoSelecionado != null) {
                lstServicos.getItems().add(servicoSelecionado);
                stage.close();
            }
        });

        btnCancelar.setOnAction(e -> stage.close());

        layout.getChildren().addAll(
            new Label("Selecione um Serviço:"),
            lstServicosDisponiveis,
            btnAdicionar,
            btnCancelar
        );

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void abrirTelaRelatorios() {
        Stage stage = new Stage();
        stage.setTitle("Relatórios");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Button btnRelatorioClientes = new Button("Relatório de Clientes");
        Button btnRelatorioPets = new Button("Relatório de Pets");
        Button btnRelatorioServicos = new Button("Relatório de Serviços");
        Button btnRelatorioPacotes = new Button("Relatório de Pacotes");
        Button btnVoltar = new Button("Voltar");

        btnRelatorioClientes.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("RELATÓRIO DE CLIENTES\n");
            sb.append("====================\n\n");
            
            for (Cliente cliente : clientes) {
                sb.append("Cliente: ").append(cliente.getNome()).append("\n");
                sb.append("CPF: ").append(cliente.getCpf()).append("\n");
                sb.append("Telefone: ").append(cliente.getTelefone()).append("\n");
                sb.append("Endereço: ").append(cliente.getEndereco()).append("\n");
                sb.append("Email: ").append(cliente.getEmail()).append("\n");
                sb.append("Pets: ").append(cliente.getPets().size()).append("\n");
                sb.append("-------------------\n\n");
            }
            
            mostrarRelatorio("Relatório de Clientes", sb.toString());
        });

        btnRelatorioPets.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("RELATÓRIO DE PETS\n");
            sb.append("=================\n\n");
            
            for (Pet pet : pets) {
                sb.append("Pet: ").append(pet.getNome()).append("\n");
                sb.append("Espécie: ").append(pet.getEspecie()).append("\n");
                sb.append("Raça: ").append(pet.getRaca()).append("\n");
                sb.append("Idade: ").append(pet.getIdade()).append(" anos\n");
                sb.append("Peso: ").append(pet.getPeso()).append(" kg\n");
                sb.append("Dono: ").append(pet.getDono() != null ? pet.getDono().getNome() : "Não cadastrado").append("\n");
                sb.append("-------------------\n\n");
            }
            
            mostrarRelatorio("Relatório de Pets", sb.toString());
        });

        btnRelatorioServicos.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("RELATÓRIO DE SERVIÇOS\n");
            sb.append("=====================\n\n");
            
            for (Servico servico : servicos) {
                sb.append("Serviço: ").append(servico.getDescricao()).append("\n");
                sb.append("Preço: R$ ").append(servico.calcularPreco()).append("\n");
                sb.append("Data: ").append(servico.getDataAgendamento()).append("\n");
                sb.append("Cliente: ").append(servico.getCliente().getNome()).append("\n");
                sb.append("Pet: ").append(servico.getPet().getNome()).append("\n");
                sb.append("-------------------\n\n");
            }
            
            mostrarRelatorio("Relatório de Serviços", sb.toString());
        });

        btnRelatorioPacotes.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("RELATÓRIO DE PACOTES\n");
            sb.append("====================\n\n");
            
            for (PacoteServicos pacote : pacotes) {
                sb.append("Pacote: ").append(pacote.getNome()).append("\n");
                sb.append("Cliente: ").append(pacote.getCliente().getNome()).append("\n");
                sb.append("Pet: ").append(pacote.getPet().getNome()).append("\n");
                sb.append("Desconto: ").append(pacote.getPercentualDesconto()).append("%\n");
                sb.append("Preço Total: R$ ").append(pacote.calcularPrecoTotal()).append("\n");
                sb.append("Serviços incluídos: ").append(pacote.getServicos().size()).append("\n");
                sb.append("-------------------\n\n");
            }
            
            mostrarRelatorio("Relatório de Pacotes", sb.toString());
        });

        btnVoltar.setOnAction(e -> stage.close());

        layout.getChildren().addAll(
            new Label("Relatórios"),
            btnRelatorioClientes,
            btnRelatorioPets,
            btnRelatorioServicos,
            btnRelatorioPacotes,
            btnVoltar
        );

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarRelatorio(String titulo, String conteudo) {
        Stage stage = new Stage();
        stage.setTitle(titulo);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        TextArea textArea = new TextArea(conteudo);
        textArea.setEditable(false);
        textArea.setPrefRowCount(20);
        textArea.setPrefColumnCount(50);

        Button btnFechar = new Button("Fechar");
        btnFechar.setOnAction(e -> stage.close());

        layout.getChildren().addAll(textArea, btnFechar);

        Scene scene = new Scene(layout, 500, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void limparCampos(TextField... campos) {
        for (TextField campo : campos) {
            campo.clear();
        }
    }
}
