package com.petshop;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

public class Main extends Application {
    private List<Cliente> clientes = new ArrayList<>();
    private List<Pet> pets = new ArrayList<>();
    private List<Servico> servicos = new ArrayList<>();
    private List<PacoteServicos> pacotes = new ArrayList<>();
    private List<Veterinario> veterinarios = new ArrayList<>();
    private static Connect connection;

    public Main() {
        // Inicializa os veterinários padrão
        veterinarios.add(new Veterinario("Luiza", "CRMV-1234", "Clínica Geral", 150.0));
        veterinarios.add(new Veterinario("Pedro", "CRMV-5678", "Ortopedia", 150.0));
        veterinarios.add(new Veterinario("João", "CRMV-9012", "Dermatologia", 150.0));
    }

    // Método original main
    public static void main(String[] args) {
        // Inicia a aplicação JavaFX
        launch(args);
    }

    // Código original do Main
    public static void executarCodigoOriginal() {
        try {
            FileReader fileReader = new FileReader("server.conf");
            connection = new Connect();
            connection.setUser(fileReader.get_User());
            connection.setPassword(fileReader.get_Password());
            connection.mount_Url(fileReader.get_Host(), fileReader.get_Port(), fileReader.get_Database());

            Connection conn = connection.getConnection();
            if (conn != null) {
                System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            } else {
                System.err.println("Não foi possível estabelecer conexão com o banco de dados.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
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
        Button btnVeterinarios = new Button("Gerenciar Veterinários");
        Button btnRelatorios = new Button("Relatórios");
        Button btnSair = new Button("Sair");

        // Estilização dos botões
        String buttonStyle = "-fx-font-size: 14px; -fx-min-width: 200px; -fx-min-height: 40px;";
        btnCliente.setStyle(buttonStyle);
        btnPet.setStyle(buttonStyle);
        btnServico.setStyle(buttonStyle);
        btnPacotes.setStyle(buttonStyle);
        btnVeterinarios.setStyle(buttonStyle);
        btnRelatorios.setStyle(buttonStyle);
        btnSair.setStyle(buttonStyle);

        mainLayout.getChildren().addAll(titulo, btnCliente, btnPet, btnServico, btnPacotes, btnVeterinarios, btnRelatorios, btnSair);

        // Ações dos botões
        btnCliente.setOnAction(e -> abrirTelaClientes());
        btnPet.setOnAction(e -> abrirTelaPets());
        btnServico.setOnAction(e -> abrirTelaServicos());
        btnPacotes.setOnAction(e -> abrirTelaPacotes());
        btnVeterinarios.setOnAction(e -> abrirTelaVeterinarios());
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
                // Validação dos campos obrigatórios
                if (txtNome.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("O nome do cliente é obrigatório");
                }
                if (txtCpf.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("O CPF do cliente é obrigatório");
                }
                if (txtTelefone.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("O telefone do cliente é obrigatório");
                }
                if (txtEndereco.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("O endereço do cliente é obrigatório");
                }

                Cliente novoCliente = new Cliente(
                    txtNome.getText(),
                    txtCpf.getText(),
                    txtTelefone.getText(),
                    txtEndereco.getText()
                );
                novoCliente.setEmail(txtEmail.getText());
                clientes.add(novoCliente);

                connection.inserirCliente(novoCliente); //adiciona no banco de dados.
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setContentText("Cliente cadastrado com sucesso!");
                alert.showAndWait();
                
                limparCampos(txtNome, txtCpf, txtTelefone, txtEndereco, txtEmail);
            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
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

            List<Cliente> listaClientes = connection.getTodosClientes();

            for (Cliente cliente : listaClientes) {
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
        List<Cliente> listaClientes = connection.getTodosClientes();
        cboDono.setPromptText("Selecione o Dono");

        Button btnSalvar = new Button("Salvar Pet");
        Button btnListar = new Button("Listar Pets");
        Button btnVoltar = new Button("Voltar");

        // Atualiza a lista de clientes no ComboBox
        //cboDono.getItems().addAll(clientes);
        cboDono.setItems(FXCollections.observableArrayList(listaClientes));

        btnSalvar.setOnAction(e -> {
            try {
                // Validação dos campos obrigatórios
                if (txtNome.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("O nome do pet é obrigatório");
                }
                if (txtRaca.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("A raça do pet é obrigatória");
                }
                if (txtPeso.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("O peso do pet é obrigatório");
                }
                if (txtIdade.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("A idade do pet é obrigatória");
                }
                if (txtEspecie.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("A espécie do pet é obrigatória");
                }
                if (cboDono.getValue() == null) {
                    throw new IllegalArgumentException("É necessário selecionar o dono do pet");
                }

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

                connection.inserirPet(novoPet);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setContentText("Pet cadastrado com sucesso!");
                alert.showAndWait();
                
                limparCampos(txtNome, txtRaca, txtPeso, txtIdade, txtEspecie);
                cboDono.setValue(null);
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Peso e idade devem ser números válidos");
                alert.showAndWait();
            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Erro ao cadastrar pet: " + ex.getMessage());
                alert.showAndWait();
            }
        });


        //LISTAR OS PETS ESTÁ COM PROBLEMA!!!!!!!


        btnListar.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Lista de Pets:\n\n");

            List<Pet> petsList = connection.getTodosPets();

            for (Pet pet : petsList) {
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

        // Seleção inicial do tipo de serviço
        ComboBox<String> cboTipoServico = new ComboBox<>();
        cboTipoServico.getItems().addAll("Banho e Tosa", "Consulta Veterinária", "Hospedagem", "Adestramento");
        cboTipoServico.setPromptText("Selecione o serviço");

        // Container para campos específicos do serviço
        VBox camposServico = new VBox(10);
        camposServico.setVisible(false);

        // Campos comuns
        ComboBox<Cliente> cboCliente = new ComboBox<>();
        cboCliente.setPromptText("Selecione o Cliente");
        cboCliente.getItems().addAll(clientes);

        ComboBox<Pet> cboPet = new ComboBox<>();
        cboPet.setPromptText("Selecione o Pet");

        DatePicker dtpData = new DatePicker();
        dtpData.setPromptText("Data do Serviço");

        // Campos específicos para Banho e Tosa
        VBox camposBanhoTosa = new VBox(10);
        CheckBox chkTosaProfunda = new CheckBox("Tosa Profunda (+R$ 50)");
        CheckBox chkPerfume = new CheckBox("Perfume (+R$ 30)");
        CheckBox chkLacoGravata = new CheckBox("Laço/Gravata (+R$ 20)");
        camposBanhoTosa.getChildren().addAll(chkTosaProfunda, chkPerfume, chkLacoGravata);
        camposBanhoTosa.setVisible(false);

        // Campos específicos para Consulta
        VBox camposConsulta = new VBox(10);
        ComboBox<Veterinario> cboVeterinario = new ComboBox<>();
        cboVeterinario.setPromptText("Selecione o Veterinário");
        cboVeterinario.getItems().addAll(veterinarios);
        CheckBox chkUrgente = new CheckBox("Consulta Urgente (+50%)");
        camposConsulta.getChildren().addAll(cboVeterinario, chkUrgente);
        camposConsulta.setVisible(false);

        // Campos específicos para Hospedagem
        VBox camposHospedagem = new VBox(10);
        CheckBox chkSuiteLuxo = new CheckBox("Suíte Luxo (+R$ 100/dia)");
        CheckBox chkServicoSpa = new CheckBox("Serviço Spa (+R$ 80/dia)");
        CheckBox chkAlimentacaoEspecial = new CheckBox("Alimentação Especial (+R$ 50/dia)");
        TextField txtDiasHospedagem = new TextField();
        txtDiasHospedagem.setPromptText("Número de Dias");
        camposHospedagem.getChildren().addAll(chkSuiteLuxo, chkServicoSpa, chkAlimentacaoEspecial, txtDiasHospedagem);
        camposHospedagem.setVisible(false);

        // Campos específicos para Adestramento
        VBox camposAdestramento = new VBox(10);
        TextField txtNumeroSessoes = new TextField();
        txtNumeroSessoes.setPromptText("Número de Sessões");
        TextField txtTipoTreinamento = new TextField();
        txtTipoTreinamento.setPromptText("Tipo de Treinamento");
        CheckBox chkAtendimentoDomiciliar = new CheckBox("Atendimento Domiciliar (+R$ 100/sessão)");
        camposAdestramento.getChildren().addAll(txtNumeroSessoes, txtTipoTreinamento, chkAtendimentoDomiciliar);
        camposAdestramento.setVisible(false);

        // Label para mostrar o preço total
        Label lblPrecoTotal = new Label("Preço Total: R$ 0,00");
        lblPrecoTotal.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button btnCalcular = new Button("Calcular Preço");
        Button btnAgendar = new Button("Agendar Serviço");
        Button btnVoltar = new Button("Voltar");

        // Atualiza a lista de pets quando um cliente é selecionado
        cboCliente.setOnAction(e -> {
            Cliente clienteSelecionado = cboCliente.getValue();
            if (clienteSelecionado != null) {
                cboPet.getItems().clear();
                cboPet.getItems().addAll(clienteSelecionado.getPets());
            }
        });

        // Mostra/esconde campos específicos baseado no tipo de serviço selecionado
        cboTipoServico.setOnAction(e -> {
            String tipoSelecionado = cboTipoServico.getValue();
            camposBanhoTosa.setVisible(false);
            camposConsulta.setVisible(false);
            camposHospedagem.setVisible(false);
            camposAdestramento.setVisible(false);
            camposServico.setVisible(true);

            if (tipoSelecionado != null) {
                switch (tipoSelecionado) {
                    case "Banho e Tosa":
                        camposBanhoTosa.setVisible(true);
                        break;
                    case "Consulta Veterinária":
                        camposConsulta.setVisible(true);
                        break;
                    case "Hospedagem":
                        camposHospedagem.setVisible(true);
                        break;
                    case "Adestramento":
                        camposAdestramento.setVisible(true);
                        break;
                }
            }
        });

        // Calcula o preço quando o botão é clicado
        btnCalcular.setOnAction(e -> {
            try {
                String tipoServico = cboTipoServico.getValue();
                double precoTotal = 0;

                switch (tipoServico) {
                    case "Banho e Tosa":
                        precoTotal = 100; // Preço base
                        if (chkTosaProfunda.isSelected()) precoTotal += 50;
                        if (chkPerfume.isSelected()) precoTotal += 30;
                        if (chkLacoGravata.isSelected()) precoTotal += 20;
                        break;

                    case "Consulta Veterinária":
                        Veterinario vet = cboVeterinario.getValue();
                        if (vet != null) {
                            precoTotal = vet.getValorConsulta();
                            if (chkUrgente.isSelected()) precoTotal *= 1.5;
                        }
                        break;

                    case "Hospedagem":
                        int dias = Integer.parseInt(txtDiasHospedagem.getText());
                        precoTotal = 100 * dias; // Preço base por dia
                        if (chkSuiteLuxo.isSelected()) precoTotal += 100 * dias;
                        if (chkServicoSpa.isSelected()) precoTotal += 80 * dias;
                        if (chkAlimentacaoEspecial.isSelected()) precoTotal += 50 * dias;
                        break;

                    case "Adestramento":
                        int sessoes = Integer.parseInt(txtNumeroSessoes.getText());
                        precoTotal = 150 * sessoes; // Preço base por sessão
                        if (chkAtendimentoDomiciliar.isSelected()) precoTotal += 100 * sessoes;
                        break;
                }

                lblPrecoTotal.setText(String.format("Preço Total: R$ %.2f", precoTotal));
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Erro ao calcular preço: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        btnAgendar.setOnAction(e -> {
            try {
                // Validação dos campos obrigatórios
                if (cboTipoServico.getValue() == null) {
                    throw new IllegalArgumentException("Selecione o tipo de serviço");
                }
                if (cboCliente.getValue() == null) {
                    throw new IllegalArgumentException("Selecione o cliente");
                }
                if (cboPet.getValue() == null) {
                    throw new IllegalArgumentException("Selecione o pet");
                }
                if (dtpData.getValue() == null) {
                    throw new IllegalArgumentException("Selecione a data");
                }

                // Validações específicas por tipo de serviço
                String tipoServico = cboTipoServico.getValue();
                double valorDiaria = 0;
                int numeroSessoes = 0;
                String tipoTreinamento = "";

                switch (tipoServico) {
                    case "Consulta Veterinária":
                        if (cboVeterinario.getValue() == null) {
                            throw new IllegalArgumentException("Selecione o veterinário");
                        }
                        break;
                    case "Hospedagem":
                        if (txtDiasHospedagem.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("Informe o número de dias");
                        }
                        try {
                            valorDiaria = Double.parseDouble(txtDiasHospedagem.getText());
                            if (valorDiaria <= 0) {
                                throw new IllegalArgumentException("Número de dias deve ser maior que zero");
                            }
                        } catch (NumberFormatException ex) {
                            throw new IllegalArgumentException("Número de dias inválido");
                        }
                        break;
                    case "Adestramento":
                        if (txtNumeroSessoes.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("Informe o número de sessões");
                        }
                        if (txtTipoTreinamento.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("Informe o tipo de treinamento");
                        }
                        try {
                            numeroSessoes = Integer.parseInt(txtNumeroSessoes.getText());
                            if (numeroSessoes <= 0) {
                                throw new IllegalArgumentException("Número de sessões deve ser maior que zero");
                            }
                        } catch (NumberFormatException ex) {
                            throw new IllegalArgumentException("Número de sessões inválido");
                        }
                        tipoTreinamento = txtTipoTreinamento.getText();
                        break;
                }

                Servico servico = criarServico(
                    tipoServico,
                    cboCliente.getValue(),
                    cboPet.getValue(),
                    0, // Preço inicial zero
                    dtpData.getValue().atStartOfDay(),
                    chkTosaProfunda.isSelected(),
                    chkPerfume.isSelected(),
                    chkLacoGravata.isSelected(),
                    cboVeterinario.getValue() != null ? cboVeterinario.getValue().getNome() : "",
                    chkUrgente.isSelected(),
                    chkSuiteLuxo.isSelected(),
                    chkServicoSpa.isSelected(),
                    chkAlimentacaoEspecial.isSelected(),
                    valorDiaria,
                    numeroSessoes,
                    tipoTreinamento,
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
            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Erro ao agendar serviço: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        btnVoltar.setOnAction(e -> stage.close());

        // Adiciona todos os campos ao layout
        camposServico.getChildren().addAll(
            cboCliente,
            cboPet,
            dtpData,
            camposBanhoTosa,
            camposConsulta,
            camposHospedagem,
            camposAdestramento,
            lblPrecoTotal
        );

        layout.getChildren().addAll(
            new Label("Agendamento de Serviço"),
            cboTipoServico,
            camposServico,
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

        if (data == null) {
            throw new IllegalArgumentException("Data é obrigatória");
        }

        switch (tipo) {
            case "Banho e Tosa":
                return new BanhoETosa("Banho e Tosa", preco, data, cliente, pet, 
                                     tosaProfunda, perfume, lacoGravata);
            case "Consulta Veterinária":
                if (veterinario == null || veterinario.trim().isEmpty()) {
                    throw new IllegalArgumentException("Veterinário é obrigatório para consultas");
                }
                // Calcula o preço da consulta considerando se é urgente
                double precoConsulta = preco;
                if (urgente) {
                    precoConsulta *= 1.5; // Adiciona 50% se for urgente
                }
                return new Consulta("Consulta Veterinária", precoConsulta, data, cliente, pet,
                                  veterinario, urgente);
            case "Hospedagem":
                if (valorDiaria <= 0) {
                    throw new IllegalArgumentException("Valor da diária deve ser maior que zero");
                }
                return new Hospedagem("Hospedagem", preco, data, cliente, pet,
                                    suiteLuxo, servicoSpa, alimentacaoEspecial, valorDiaria);
            case "Adestramento":
                if (numeroSessoes <= 0) {
                    throw new IllegalArgumentException("Número de sessões deve ser maior que zero");
                }
                if (tipoTreinamento == null || tipoTreinamento.trim().isEmpty()) {
                    throw new IllegalArgumentException("Tipo de treinamento é obrigatório");
                }
                return new Adestramento("Adestramento", preco, data, cliente, pet,
                                      numeroSessoes, tipoTreinamento, atendimentoDomiciliar);
            default:
                throw new IllegalArgumentException("Tipo de serviço inválido: " + tipo);
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
                // Validação dos campos obrigatórios
                if (txtNome.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Informe o nome do pacote");
                }

                Cliente cliente = cboCliente.getValue();
                Pet pet = cboPet.getValue();
                
                if (cliente == null) {
                    throw new IllegalArgumentException("Selecione o cliente");
                }
                if (pet == null) {
                    throw new IllegalArgumentException("Selecione o pet");
                }
                if (lstServicos.getItems().isEmpty()) {
                    throw new IllegalArgumentException("Adicione pelo menos um serviço ao pacote");
                }

                // Validação do desconto
                if (txtDesconto.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Informe o percentual de desconto");
                }
                double desconto;
                try {
                    desconto = Double.parseDouble(txtDesconto.getText());
                    if (desconto < 0 || desconto > 100) {
                        throw new IllegalArgumentException("O desconto deve estar entre 0 e 100%");
                    }
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Percentual de desconto inválido");
                }

                // Cria o pacote
                PacoteServicos pacote = new PacoteServicos(txtNome.getText().trim(), desconto, cliente, pet);
                
                // Adiciona os serviços ao pacote
                for (Servico servico : lstServicos.getItems()) {
                    try {
                        pacote.adicionarServico(servico);
                    } catch (IllegalArgumentException ex) {
                        throw new IllegalArgumentException("Erro ao adicionar serviço ao pacote: " + ex.getMessage());
                    }
                }

                // Calcula o preço total
                double precoTotal = pacote.calcularPrecoTotal();
                
                // Adiciona o pacote à lista
                pacotes.add(pacote);

                // Mostra mensagem de sucesso
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setContentText(String.format("Pacote criado com sucesso!\nPreço total com desconto: R$ %.2f", precoTotal));
                alert.showAndWait();
                
                stage.close();
            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
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

        // Campos para seleção de cliente e pet
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

        ListView<Servico> lstServicosDisponiveis = new ListView<>();
        lstServicosDisponiveis.setPrefHeight(200);

        // Label para mostrar o preço do serviço selecionado
        Label lblPrecoServico = new Label("Preço do Serviço: R$ 0,00");
        lblPrecoServico.setStyle("-fx-font-weight: bold");

        // Atualiza a lista de serviços quando cliente e pet são selecionados
        cboPet.setOnAction(e -> {
            Cliente clienteSelecionado = cboCliente.getValue();
            Pet petSelecionado = cboPet.getValue();
            
            if (clienteSelecionado != null && petSelecionado != null) {
                lstServicosDisponiveis.getItems().clear();
                // Filtra os serviços pelo cliente e pet selecionados
                for (Servico servico : servicos) {
                    if (servico.getCliente().equals(clienteSelecionado) && 
                        servico.getPet().equals(petSelecionado)) {
                        lstServicosDisponiveis.getItems().add(servico);
                    }
                }
            }
        });

        // Atualiza o preço quando um serviço é selecionado
        lstServicosDisponiveis.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                double preco = newVal.calcularPreco();
                lblPrecoServico.setText(String.format("Preço do Serviço: R$ %.2f", preco));
            } else {
                lblPrecoServico.setText("Preço do Serviço: R$ 0,00");
            }
        });

        Button btnAdicionar = new Button("Adicionar ao Pacote");
        Button btnCancelar = new Button("Cancelar");

        btnAdicionar.setOnAction(e -> {
            Servico servicoSelecionado = lstServicosDisponiveis.getSelectionModel().getSelectedItem();
            if (servicoSelecionado != null) {
                // Verifica se o serviço já está no pacote
                if (!lstServicos.getItems().contains(servicoSelecionado)) {
                    // Calcula o preço do serviço antes de adicioná-lo ao pacote
                    double precoCalculado = servicoSelecionado.calcularPreco();
                    
                    // Cria uma cópia do serviço com o preço calculado
                    Servico servicoComPreco = criarServico(
                        servicoSelecionado.getDescricao(),
                        servicoSelecionado.getCliente(),
                        servicoSelecionado.getPet(),
                        servicoSelecionado instanceof Consulta ? 
                            getValorConsultaVeterinario(((Consulta) servicoSelecionado).getVeterinario()) : 
                            precoCalculado,
                        servicoSelecionado.getDataAgendamento(),
                        servicoSelecionado instanceof BanhoETosa ? ((BanhoETosa) servicoSelecionado).isTosaProfunda() : false,
                        servicoSelecionado instanceof BanhoETosa ? ((BanhoETosa) servicoSelecionado).isPerfume() : false,
                        servicoSelecionado instanceof BanhoETosa ? ((BanhoETosa) servicoSelecionado).isLacoOuGravata() : false,
                        servicoSelecionado instanceof Consulta ? ((Consulta) servicoSelecionado).getVeterinario() : "",
                        servicoSelecionado instanceof Consulta ? ((Consulta) servicoSelecionado).isConsultaUrgente() : false,
                        servicoSelecionado instanceof Hospedagem ? ((Hospedagem) servicoSelecionado).isSuiteLuxo() : false,
                        servicoSelecionado instanceof Hospedagem ? ((Hospedagem) servicoSelecionado).isServicoSpa() : false,
                        servicoSelecionado instanceof Hospedagem ? ((Hospedagem) servicoSelecionado).isAlimentacaoEspecial() : false,
                        servicoSelecionado instanceof Hospedagem ? ((Hospedagem) servicoSelecionado).getValorDiaria() : 0,
                        servicoSelecionado instanceof Adestramento ? ((Adestramento) servicoSelecionado).getNumeroSessoes() : 0,
                        servicoSelecionado instanceof Adestramento ? ((Adestramento) servicoSelecionado).getTipoTreinamento() : "",
                        servicoSelecionado instanceof Adestramento ? ((Adestramento) servicoSelecionado).isAtendimentoDomiciliar() : false
                    );
                    
                    lstServicos.getItems().add(servicoComPreco);
                    stage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Aviso");
                    alert.setContentText("Este serviço já está no pacote!");
                    alert.showAndWait();
                }
            }
        });

        btnCancelar.setOnAction(e -> stage.close());

        layout.getChildren().addAll(
            new Label("Selecione o Cliente e Pet:"),
            cboCliente,
            cboPet,
            new Label("Serviços Disponíveis:"),
            lstServicosDisponiveis,
            lblPrecoServico,
            btnAdicionar,
            btnCancelar
        );

        Scene scene = new Scene(layout, 400, 500);
        stage.setScene(scene);
        stage.show();
    }

    // Método auxiliar para obter o valor da consulta do veterinário
    private double getValorConsultaVeterinario(String nomeVeterinario) {
        for (Veterinario vet : veterinarios) {
            if (vet.getNome().equals(nomeVeterinario)) {
                return vet.getValorConsulta();
            }
        }
        return 0.0; // Retorna 0 se não encontrar o veterinário (não deveria acontecer)
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
        Button btnRelatorioVeterinarios = new Button("Relatório de Veterinários");
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

        btnRelatorioVeterinarios.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("RELATÓRIO DE VETERINÁRIOS\n");
            sb.append("========================\n\n");
            
            for (Veterinario vet : veterinarios) {
                sb.append("Nome: ").append(vet.getNome()).append("\n");
                sb.append("CRMV: ").append(vet.getCrmv()).append("\n");
                sb.append("Especialidade: ").append(vet.getEspecialidade()).append("\n");
                sb.append("Valor da Consulta: R$ ").append(vet.getValorConsulta()).append("\n");
                
                // Conta quantas consultas cada veterinário realizou
                int consultas = 0;
                for (Servico servico : servicos) {
                    if (servico instanceof Consulta && 
                        ((Consulta) servico).getVeterinario().equals(vet.getNome())) {
                        consultas++;
                    }
                }
                sb.append("Consultas Realizadas: ").append(consultas).append("\n");
                sb.append("-------------------\n\n");
            }
            
            mostrarRelatorio("Relatório de Veterinários", sb.toString());
        });

        btnVoltar.setOnAction(e -> stage.close());

        layout.getChildren().addAll(
            new Label("Relatórios"),
            btnRelatorioClientes,
            btnRelatorioPets,
            btnRelatorioServicos,
            btnRelatorioPacotes,
            btnRelatorioVeterinarios,
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

    private void abrirTelaVeterinarios() {
        Stage stage = new Stage();
        stage.setTitle("Gerenciamento de Veterinários");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome do Veterinário");
        TextField txtCrmv = new TextField();
        txtCrmv.setPromptText("CRMV");
        TextField txtEspecialidade = new TextField();
        txtEspecialidade.setPromptText("Especialidade");
        TextField txtValorConsulta = new TextField();
        txtValorConsulta.setPromptText("Valor da Consulta");

        Button btnSalvar = new Button("Salvar Veterinário");
        Button btnListar = new Button("Listar Veterinários");
        Button btnVoltar = new Button("Voltar");

        btnSalvar.setOnAction(e -> {
            try {
                // Validação dos campos obrigatórios
                if (txtNome.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("O nome do veterinário é obrigatório");
                }
                if (txtCrmv.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("O CRMV é obrigatório");
                }
                if (txtEspecialidade.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("A especialidade é obrigatória");
                }
                if (txtValorConsulta.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("O valor da consulta é obrigatório");
                }

                // Validação do valor da consulta
                double valorConsulta;
                try {
                    valorConsulta = Double.parseDouble(txtValorConsulta.getText());
                    if (valorConsulta <= 0) {
                        throw new IllegalArgumentException("O valor da consulta deve ser maior que zero");
                    }
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("O valor da consulta deve ser um número válido");
                }

                Veterinario novoVet = new Veterinario(
                    txtNome.getText(),
                    txtCrmv.getText(),
                    txtEspecialidade.getText(),
                    valorConsulta
                );
                veterinarios.add(novoVet);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setContentText("Veterinário cadastrado com sucesso!");
                alert.showAndWait();
                
                limparCampos(txtNome, txtCrmv, txtEspecialidade, txtValorConsulta);
            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Erro ao cadastrar veterinário: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        btnListar.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Lista de Veterinários:\n\n");
            for (Veterinario vet : veterinarios) {
                sb.append(vet.toString()).append("\n\n");
            }
            
            TextArea textArea = new TextArea(sb.toString());
            textArea.setEditable(false);
            textArea.setPrefRowCount(10);
            
            Stage listStage = new Stage();
            listStage.setTitle("Lista de Veterinários");
            VBox listLayout = new VBox(10);
            listLayout.setPadding(new Insets(20));
            listLayout.getChildren().add(textArea);
            
            Scene listScene = new Scene(listLayout, 400, 300);
            listStage.setScene(listScene);
            listStage.show();
        });

        btnVoltar.setOnAction(e -> stage.close());

        layout.getChildren().addAll(
            new Label("Cadastro de Veterinário"),
            txtNome,
            txtCrmv,
            txtEspecialidade,
            txtValorConsulta,
            btnSalvar,
            btnListar,
            btnVoltar
        );

        Scene scene = new Scene(layout, 400, 500);
        stage.setScene(scene);
        stage.show();
    }
}

