package com.petshop;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class Main extends Application {
    private List<Cliente> clientes = new ArrayList<>();
    private List<Pet> pets = new ArrayList<>();
    private List<Servico> servicos = new ArrayList<>();
    private List<PacoteServicos> pacotes = new ArrayList<>();
    private List<Veterinario> veterinarios = new ArrayList<>();
    private static Connect connection;
    double precoTotal = 0;

    public Main() {
        // Inicializa os veterinários padrão
        /*
         * SOMENTE PARA TESTE
         * veterinarios.add(new Veterinario("Luiza", "CRMV-1234", "Clínica Geral",
         * 150.0));
         * veterinarios.add(new Veterinario("Pedro", "CRMV-5678", "Ortopedia", 150.0));
         * veterinarios.add(new Veterinario("João", "CRMV-9012", "Dermatologia",
         * 150.0));
         */
    }

    // Método original main
    public static void main(String[] args) {
        // Inicia a aplicação JavaFX
        //Versão sem gerenciamento de pacotes.
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
                System.out.println("Banco de dados conectado com sucesso!");
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
        // abrir janela em 800x600\
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);

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

        mainLayout.getChildren().addAll(titulo, btnCliente, btnPet, btnServico, btnVeterinarios, btnRelatorios,
                btnSair);

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

        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        Label titulo = new Label("Gerenciamento de Clientes");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button btnCadastrar = new Button("Cadastrar Cliente");
        Button btnDeletar = new Button("Deletar Cliente");
        Button btnListar = new Button("Listar Clientes"); // Nova opção
        Button btnVoltar = new Button("Voltar");

        // Estilização dos botões
        String buttonStyle = "-fx-font-size: 14px; -fx-min-width: 200px; -fx-min-height: 40px;";
        btnCadastrar.setStyle(buttonStyle);
        btnDeletar.setStyle(buttonStyle);
        btnListar.setStyle(buttonStyle); // Estilização do novo botão
        btnVoltar.setStyle(buttonStyle);

        btnCadastrar.setOnAction(e -> abrirTelaCadastroCliente());
        btnDeletar.setOnAction(e -> abrirTelaDeleteCliente());
        btnListar.setOnAction(e -> abrirTelaListarClientes()); // Ação do novo botão
        btnVoltar.setOnAction(e -> stage.close());

        mainLayout.getChildren().addAll(titulo, btnCadastrar, btnDeletar, btnListar, btnVoltar);

        Scene scene = new Scene(mainLayout, 400, 350); // Aumentei a altura para acomodar o novo botão
        stage.setScene(scene);
        stage.show();
    }

    // Novo método para listar clientes
    private void abrirTelaListarClientes() {
        Stage stage = new Stage();
        stage.setTitle("Lista de Clientes");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Obter a lista de clientes do banco de dados
        List<Cliente> listaClientes = connection.getTodosClientes();

        // Criar uma tabela para exibir os clientes
        TableView<Cliente> tabelaClientes = new TableView<>();
        tabelaClientes.setPrefHeight(400);
        tabelaClientes.setPrefWidth(600);

        // Definir as colunas da tabela
        TableColumn<Cliente, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(150);

        TableColumn<Cliente, String> colCPF = new TableColumn<>("CPF");
        colCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colCPF.setPrefWidth(100);

        TableColumn<Cliente, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colTelefone.setPrefWidth(100);

        TableColumn<Cliente, String> colEndereco = new TableColumn<>("Endereço");
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colEndereco.setPrefWidth(150);

        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(150);

        // Adicionar as colunas à tabela
        tabelaClientes.getColumns().addAll(colNome, colCPF, colTelefone, colEndereco, colEmail);

        // Adicionar os clientes à tabela
        tabelaClientes.setItems(FXCollections.observableArrayList(listaClientes));

        Button btnAtualizar = new Button("Atualizar Lista");
        Button btnVoltar = new Button("Voltar");

        btnAtualizar.setOnAction(e -> {
            List<Cliente> clientesAtualizados = connection.getTodosClientes();
            tabelaClientes.setItems(FXCollections.observableArrayList(clientesAtualizados));
        });

        btnVoltar.setOnAction(e -> stage.close());

        layout.getChildren().addAll(
                new Label("Lista de Clientes"),
                tabelaClientes,
                btnAtualizar,
                btnVoltar);

        Scene scene = new Scene(layout, 650, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void abrirTelaCadastroCliente() {
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Cliente");

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
                        txtEndereco.getText());
                novoCliente.setEmail(txtEmail.getText());
                clientes.add(novoCliente);

                connection.inserirCliente(novoCliente); // adiciona no banco de dados.

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

        btnVoltar.setOnAction(e -> stage.close());

        layout.getChildren().addAll(
                new Label("Cadastro de Cliente"),
                txtNome,
                txtCpf,
                txtTelefone,
                txtEndereco,
                txtEmail,
                btnSalvar,
                btnVoltar);

        Scene scene = new Scene(layout, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void abrirTelaDeleteCliente() {
        Stage stage = new Stage();
        stage.setTitle("Deletar Cliente");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Obter a lista de clientes do banco de dados
        List<Cliente> listaClientes = connection.getTodosClientes();

        // ComboBox para selecionar o cliente a ser deletado
        ComboBox<Cliente> cboClientes = new ComboBox<>();
        cboClientes.setPromptText("Selecione o cliente para deletar");
        cboClientes.setItems(FXCollections.observableArrayList(listaClientes));
        cboClientes.setPrefWidth(300);

        Button btnDeletar = new Button("Deletar Cliente");
        Button btnVoltar = new Button("Voltar");

        btnDeletar.setOnAction(e -> {
            Cliente clienteSelecionado = cboClientes.getValue();
            if (clienteSelecionado != null) {
                Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacao.setTitle("Confirmar Exclusão");
                confirmacao.setHeaderText("Excluir Cliente");
                confirmacao.setContentText("Tem certeza que deseja excluir o cliente " +
                        clienteSelecionado.getNome() + "?");

                Optional<ButtonType> resultado = confirmacao.showAndWait();
                if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                    try {
                        // Remover o cliente do banco de dados
                        System.out.println("ID do cliente a ser deletado: " + connection.getId(clienteSelecionado)); // debug
                        int id = connection.getId(clienteSelecionado);
                        try {
                            connection.deleteClientePorId(id);
                        } catch (Exception ex) {
                            System.out.println("Erro ao deletar cliente: " + ex.getMessage());
                        }

                        // Remover o cliente da lista local
                        clientes.remove(clienteSelecionado);

                        // Atualizar a ComboBox
                        cboClientes.getItems().remove(clienteSelecionado);

                        Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
                        sucesso.setTitle("Sucesso");
                        sucesso.setContentText("Cliente deletado com sucesso!");
                        sucesso.showAndWait();
                    } catch (Exception ex) {
                        Alert erro = new Alert(Alert.AlertType.ERROR);
                        erro.setTitle("Erro");
                        erro.setContentText("Erro ao deletar cliente: " + ex.getMessage());
                        erro.showAndWait();
                    }
                }
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Aviso");
                alerta.setContentText("Por favor, selecione um cliente para deletar.");
                alerta.showAndWait();
            }
        });

        btnVoltar.setOnAction(e -> stage.close());

        layout.getChildren().addAll(
                new Label("Selecione o cliente que deseja deletar:"),
                cboClientes,
                btnDeletar,
                btnVoltar);

        Scene scene = new Scene(layout, 400, 300);
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
        // cboDono.getItems().addAll(clientes);
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
                        Integer.parseInt(txtIdade.getText()));
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

        btnListar.setOnAction(e -> {
            Stage listStage = new Stage();
            listStage.setTitle("Lista de Pets");

            VBox listLayout = new VBox(10);
            listLayout.setPadding(new Insets(20));
            listLayout.setAlignment(Pos.CENTER);

            // Obter a lista de pets do banco de dados
            List<Pet> listaPets = connection.getTodosPets();

            // Criar uma tabela para exibir os pets
            TableView<Pet> tabelaPets = new TableView<>();
            tabelaPets.setPrefHeight(400);
            tabelaPets.setPrefWidth(600);

            // Definir as colunas da tabela
            TableColumn<Pet, String> colNome = new TableColumn<>("Nome");
            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colNome.setPrefWidth(100);

            TableColumn<Pet, String> colEspecie = new TableColumn<>("Espécie");
            colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
            colEspecie.setPrefWidth(100);

            TableColumn<Pet, String> colRaca = new TableColumn<>("Raça");
            colRaca.setCellValueFactory(new PropertyValueFactory<>("raca"));
            colRaca.setPrefWidth(100);

            TableColumn<Pet, Number> colPeso = new TableColumn<>("Peso");
            colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
            colPeso.setPrefWidth(80);

            TableColumn<Pet, Number> colIdade = new TableColumn<>("Idade");
            colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
            colIdade.setPrefWidth(80);

            TableColumn<Pet, String> colDono = new TableColumn<>("Dono");
            colDono.setCellValueFactory(cellData -> {
                Cliente dono = cellData.getValue().getDono();
                return dono != null ? new javafx.beans.property.SimpleStringProperty(dono.getNome())
                        : new javafx.beans.property.SimpleStringProperty("");
            });
            colDono.setPrefWidth(140);

            // Adicionar as colunas à tabela
            tabelaPets.getColumns().addAll(colNome, colEspecie, colRaca, colPeso, colIdade, colDono);

            // Adicionar os pets à tabela
            tabelaPets.setItems(FXCollections.observableArrayList(listaPets));

            Button btnAtualizar = new Button("Atualizar Lista");
            Button btnVoltar2 = new Button("Voltar");

            btnAtualizar.setOnAction(event -> {
                List<Pet> petsAtualizados = connection.getTodosPets();
                tabelaPets.setItems(FXCollections.observableArrayList(petsAtualizados));
            });

            btnVoltar2.setOnAction(event -> listStage.close());

            listLayout.getChildren().addAll(
                    new Label("Lista de Pets"),
                    tabelaPets,
                    btnAtualizar,
                    btnVoltar2);

            Scene listScene = new Scene(listLayout, 650, 500);
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
                btnVoltar);

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
        // puxar os clientes do banco
        List<Cliente> listaClientes = connection.getTodosClientes();
        cboCliente.setItems(FXCollections.observableArrayList(listaClientes));
        // cboCliente.getItems().addAll(clientes);

        ComboBox<Pet> cboPet = new ComboBox<>();
        cboPet.setPromptText("Selecione o Pet");

        List<Pet> listaPets = connection.getTodosPets();
        // cboPet.setItems(FXCollections.observableArrayList(listaPets));

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
                System.out.println(clienteSelecionado); // debug
                System.out.println(connection.getPetsByClientName(clienteSelecionado.getNome())); // debug
                cboPet.getItems().clear();
                cboPet.getItems().addAll(connection.getPetsByClientName(clienteSelecionado.getNome()));
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

                switch (tipoServico) {
                    case "Banho e Tosa":
                        precoTotal = 100; // Preço base
                        if (chkTosaProfunda.isSelected())
                            precoTotal += 50;
                        if (chkPerfume.isSelected())
                            precoTotal += 30;
                        if (chkLacoGravata.isSelected())
                            precoTotal += 20;
                        break;

                    case "Consulta Veterinária":
                        Veterinario vet = cboVeterinario.getValue();
                        if (vet != null) {
                            precoTotal = vet.getValorConsulta();
                            if (chkUrgente.isSelected())
                                precoTotal *= 1.5;
                        }
                        break;

                    case "Hospedagem":
                        int dias = Integer.parseInt(txtDiasHospedagem.getText());
                        precoTotal = 100 * dias; // Preço base por dia
                        if (chkSuiteLuxo.isSelected())
                            precoTotal += 100 * dias;
                        if (chkServicoSpa.isSelected())
                            precoTotal += 80 * dias;
                        if (chkAlimentacaoEspecial.isSelected())
                            precoTotal += 50 * dias;
                        break;

                    case "Adestramento":
                        int sessoes = Integer.parseInt(txtNumeroSessoes.getText());
                        precoTotal = 150 * sessoes; // Preço base por sessão
                        if (chkAtendimentoDomiciliar.isSelected())
                            precoTotal += 100 * sessoes;
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

        // fazer a partir do banho e tosa
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
                        chkAtendimentoDomiciliar.isSelected());

                // parte de salvar o agendamento
                if (servico != null) {
                    servicos.add(servico);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    // fazer a checagem do tipo de servico para salvar nas tabelas certas!!
                    if (cboTipoServico.getValue().equals("Banho e Tosa")) {
                        connection.inserirBanhoTosa(cboCliente.getValue(), cboPet.getValue(), dtpData,
                                chkTosaProfunda.isSelected(), chkPerfume.isSelected(), chkLacoGravata.isSelected(),
                                precoTotal);
                    }
                    if (cboTipoServico.getValue().equals("Consulta Veterinária")) {
                        connection.inserirConsulta(cboCliente.getValue(), cboPet.getValue(), dtpData,
                                cboVeterinario.getValue(), chkUrgente.isSelected(), precoTotal);
                    }
                    if (cboTipoServico.getValue().equals("Hospedagem")) {
                        int dias = Integer.parseInt(txtDiasHospedagem.getText());
                        connection.inserirHospedagem(cboCliente.getValue(), cboPet.getValue(), dtpData, dias,
                                chkSuiteLuxo.isSelected(), chkServicoSpa.isSelected(),
                                chkAlimentacaoEspecial.isSelected(), precoTotal);
                    }
                    if (cboTipoServico.getValue().equals("Adestramento")) {
                        int numSessoes = Integer.parseInt(txtNumeroSessoes.getText());
                        connection.inserirAdestramento(cboCliente.getValue(), cboPet.getValue(), dtpData, numSessoes,
                                txtTipoTreinamento.getText(), chkAtendimentoDomiciliar.isSelected(), precoTotal);
                    }
                    alert.setTitle("Sucesso");
                    alert.setContentText("Serviço agendado com sucesso!");
                    connection.inserirServico(cboCliente.getValue(), cboPet.getValue(), dtpData, tipoServico,
                            precoTotal);
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
                lblPrecoTotal);

        layout.getChildren().addAll(
                new Label("Agendamento de Serviço"),
                cboTipoServico,
                camposServico,
                btnCalcular,
                btnAgendar,
                btnVoltar);

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
                alert.setContentText(
                        String.format("Pacote criado com sucesso!\nPreço total com desconto: R$ %.2f", precoTotal));
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
                btnVoltar);

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
                            servicoSelecionado instanceof Consulta
                                    ? getValorConsultaVeterinario(((Consulta) servicoSelecionado).getVeterinario())
                                    : precoCalculado,
                            servicoSelecionado.getDataAgendamento(),
                            servicoSelecionado instanceof BanhoETosa
                                    ? ((BanhoETosa) servicoSelecionado).isTosaProfunda()
                                    : false,
                            servicoSelecionado instanceof BanhoETosa ? ((BanhoETosa) servicoSelecionado).isPerfume()
                                    : false,
                            servicoSelecionado instanceof BanhoETosa
                                    ? ((BanhoETosa) servicoSelecionado).isLacoOuGravata()
                                    : false,
                            servicoSelecionado instanceof Consulta ? ((Consulta) servicoSelecionado).getVeterinario()
                                    : "",
                            servicoSelecionado instanceof Consulta ? ((Consulta) servicoSelecionado).isConsultaUrgente()
                                    : false,
                            servicoSelecionado instanceof Hospedagem ? ((Hospedagem) servicoSelecionado).isSuiteLuxo()
                                    : false,
                            servicoSelecionado instanceof Hospedagem ? ((Hospedagem) servicoSelecionado).isServicoSpa()
                                    : false,
                            servicoSelecionado instanceof Hospedagem
                                    ? ((Hospedagem) servicoSelecionado).isAlimentacaoEspecial()
                                    : false,
                            servicoSelecionado instanceof Hospedagem
                                    ? ((Hospedagem) servicoSelecionado).getValorDiaria()
                                    : 0,
                            servicoSelecionado instanceof Adestramento
                                    ? ((Adestramento) servicoSelecionado).getNumeroSessoes()
                                    : 0,
                            servicoSelecionado instanceof Adestramento
                                    ? ((Adestramento) servicoSelecionado).getTipoTreinamento()
                                    : "",
                            servicoSelecionado instanceof Adestramento
                                    ? ((Adestramento) servicoSelecionado).isAtendimentoDomiciliar()
                                    : false);

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
                btnCancelar);

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
            Stage listStage = new Stage();
            listStage.setTitle("Lista de Clientes");

            VBox listLayout = new VBox(10);
            listLayout.setPadding(new Insets(20));
            listLayout.setAlignment(Pos.CENTER);

            // Obter a lista de clientes do banco de dados
            List<Cliente> listaClientes = connection.getTodosClientes();

            // Criar uma tabela para exibir os clientes
            TableView<Cliente> tabelaClientes = new TableView<>();
            tabelaClientes.setPrefHeight(400);
            tabelaClientes.setPrefWidth(600);

            // Definir as colunas da tabela
            TableColumn<Cliente, String> colNome = new TableColumn<>("Nome");
            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colNome.setPrefWidth(150);

            TableColumn<Cliente, String> colCPF = new TableColumn<>("CPF");
            colCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
            colCPF.setPrefWidth(100);

            TableColumn<Cliente, String> colTelefone = new TableColumn<>("Telefone");
            colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
            colTelefone.setPrefWidth(100);

            TableColumn<Cliente, String> colEndereco = new TableColumn<>("Endereço");
            colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
            colEndereco.setPrefWidth(150);

            TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colEmail.setPrefWidth(150);

            // Adicionar as colunas à tabela
            tabelaClientes.getColumns().addAll(colNome, colCPF, colTelefone, colEndereco, colEmail);

            // Adicionar os clientes à tabela
            tabelaClientes.setItems(FXCollections.observableArrayList(listaClientes));

            Button btnAtualizar = new Button("Atualizar Lista");
            Button btnVoltar2 = new Button("Voltar");

            btnAtualizar.setOnAction(event -> {
                List<Cliente> clientesAtualizados = connection.getTodosClientes();
                tabelaClientes.setItems(FXCollections.observableArrayList(clientesAtualizados));
            });

            btnVoltar2.setOnAction(event -> listStage.close());

            // Adicionar os componentes ao layout
            listLayout.getChildren().addAll(
                    new Label("Relatório de Clientes"),
                    tabelaClientes,
                    new HBox(10, btnAtualizar, btnVoltar2));

            // Configurar e mostrar a cena
            Scene listScene = new Scene(listLayout, 650, 500);
            listStage.setScene(listScene);
            listStage.show();
        });

        btnRelatorioPets.setOnAction(e -> {
            Stage listStage = new Stage();
            listStage.setTitle("Lista de Pets");

            VBox listLayout = new VBox(10);
            listLayout.setPadding(new Insets(20));
            listLayout.setAlignment(Pos.CENTER);

            // Obter a lista de pets do banco de dados
            List<Pet> listaPets = connection.getTodosPets();

            // Criar uma tabela para exibir os pets
            TableView<Pet> tabelaPets = new TableView<>();
            tabelaPets.setPrefHeight(400);
            tabelaPets.setPrefWidth(600);

            // Definir as colunas da tabela
            TableColumn<Pet, String> colNome = new TableColumn<>("Nome");
            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colNome.setPrefWidth(100);

            TableColumn<Pet, String> colEspecie = new TableColumn<>("Espécie");
            colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
            colEspecie.setPrefWidth(100);

            TableColumn<Pet, String> colRaca = new TableColumn<>("Raça");
            colRaca.setCellValueFactory(new PropertyValueFactory<>("raca"));
            colRaca.setPrefWidth(100);

            TableColumn<Pet, Number> colPeso = new TableColumn<>("Peso");
            colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
            colPeso.setPrefWidth(80);

            TableColumn<Pet, Number> colIdade = new TableColumn<>("Idade");
            colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
            colIdade.setPrefWidth(80);

            TableColumn<Pet, String> colDono = new TableColumn<>("Dono");
            colDono.setCellValueFactory(cellData -> {
                Cliente dono = cellData.getValue().getDono();
                return dono != null ? new javafx.beans.property.SimpleStringProperty(dono.getNome())
                        : new javafx.beans.property.SimpleStringProperty("");
            });
            colDono.setPrefWidth(140);

            // Adicionar as colunas à tabela
            tabelaPets.getColumns().addAll(colNome, colEspecie, colRaca, colPeso, colIdade, colDono);

            // Adicionar os pets à tabela
            tabelaPets.setItems(FXCollections.observableArrayList(listaPets));

            Button btnAtualizar = new Button("Atualizar Lista");
            Button btnVoltar2 = new Button("Voltar");

            btnAtualizar.setOnAction(event -> {
                List<Pet> petsAtualizados = connection.getTodosPets();
                tabelaPets.setItems(FXCollections.observableArrayList(petsAtualizados));
            });

            btnVoltar2.setOnAction(event -> listStage.close());

            // Adicionar os componentes ao layout
            listLayout.getChildren().addAll(
                    new Label("Relatório de Pets"),
                    tabelaPets,
                    new HBox(10, btnAtualizar, btnVoltar2));

            // Configurar e mostrar a cena
            Scene listScene = new Scene(listLayout, 650, 500);
            listStage.setScene(listScene);
            listStage.show();
        });

        btnRelatorioServicos.setOnAction(e -> {
            Stage listStage = new Stage();
            listStage.setTitle("Lista de Serviços");

            VBox listLayout = new VBox(10);
            listLayout.setPadding(new Insets(20));
            listLayout.setAlignment(Pos.CENTER);

            // Obter a lista de serviços do banco de dados
            List<ServicoGenerico> listaServicos = connection.getTodosServicos();

            // Criar uma tabela para exibir os serviços
            TableView<ServicoGenerico> tabelaServicos = new TableView<>();
            tabelaServicos.setPrefHeight(400);
            tabelaServicos.setPrefWidth(600);

            // Definir as colunas da tabela

            TableColumn<ServicoGenerico, String> colCliente = new TableColumn<>("Cliente");
            colCliente.setCellValueFactory(new PropertyValueFactory<>("nome_dono"));
            colCliente.setPrefWidth(150);

            TableColumn<ServicoGenerico, String> colPet = new TableColumn<>("Pet");
            colPet.setCellValueFactory(new PropertyValueFactory<>("nome_pet"));
            colPet.setPrefWidth(100);

            TableColumn<ServicoGenerico, String> colTipo = new TableColumn<>("Tipo");
            colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo_servico"));
            colTipo.setPrefWidth(150);

            TableColumn<ServicoGenerico, Number> colPreco = new TableColumn<>("Preço");
            colPreco.setCellValueFactory(new PropertyValueFactory<>("valor_servico"));
            colPreco.setPrefWidth(100);

            TableColumn<ServicoGenerico, LocalDateTime> colData = new TableColumn<>("Data");
            colData.setCellValueFactory(new PropertyValueFactory<>("dataAgendamento"));
            colData.setPrefWidth(150);

            // Adicionar as colunas à tabela
            tabelaServicos.getColumns().addAll(colCliente, colPet, colTipo, colPreco, colData);

            // Adicionar os serviços à tabela
            tabelaServicos.setItems(FXCollections.observableArrayList(listaServicos));

            Button btnAtualizar = new Button("Atualizar Lista");
            Button btnVoltar2 = new Button("Voltar");

            btnAtualizar.setOnAction(event -> {
                List<ServicoGenerico> servicosAtualizados = connection.getTodosServicos();
                tabelaServicos.setItems(FXCollections.observableArrayList(servicosAtualizados));
            });

            btnVoltar2.setOnAction(event -> listStage.close());

            // Adicionar os componentes ao layout
            listLayout.getChildren().addAll(
                    new Label("Relatório de Serviços"),
                    tabelaServicos,
                    new HBox(10, btnAtualizar, btnVoltar2));

            // Configurar e mostrar a cena
            Scene listScene = new Scene(listLayout, 650, 500);
            listStage.setScene(listScene);
            listStage.show();
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
            Stage listStage = new Stage();
            listStage.setTitle("Lista de Veterinários");

            VBox listLayout = new VBox(10);
            listLayout.setPadding(new Insets(20));
            listLayout.setAlignment(Pos.CENTER);

            // Obter a lista de veterinários do banco de dados
            List<Veterinario> listaVeterinarios = connection.getTodosVeterinarios();

            // Criar uma tabela para exibir os veterinários
            TableView<Veterinario> tabelaVeterinarios = new TableView<>();
            tabelaVeterinarios.setPrefHeight(400);
            tabelaVeterinarios.setPrefWidth(600);

            // Definir as colunas da tabela
            TableColumn<Veterinario, String> colNome = new TableColumn<>("Nome");
            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colNome.setPrefWidth(150);

            TableColumn<Veterinario, String> colCRMV = new TableColumn<>("CRMV");
            colCRMV.setCellValueFactory(new PropertyValueFactory<>("crmv"));
            colCRMV.setPrefWidth(100);

            TableColumn<Veterinario, String> colEspecialidade = new TableColumn<>("Especialidade");
            colEspecialidade.setCellValueFactory(new PropertyValueFactory<>("especialidade"));
            colEspecialidade.setPrefWidth(150);

            TableColumn<Veterinario, Number> colValorConsulta = new TableColumn<>("Valor Consulta");
            colValorConsulta.setCellValueFactory(new PropertyValueFactory<>("valorConsulta"));
            colValorConsulta.setPrefWidth(120);

            // Adicionar as colunas à tabela
            tabelaVeterinarios.getColumns().addAll(colNome, colCRMV, colEspecialidade, colValorConsulta);

            // Adicionar os veterinários à tabela (usando a lista do banco de dados)
            tabelaVeterinarios.setItems(FXCollections.observableArrayList(listaVeterinarios));

            Button btnAtualizar = new Button("Atualizar Lista");
            Button btnVoltar2 = new Button("Voltar");

            btnAtualizar.setOnAction(event -> {
                // Atualizar a lista de veterinários do banco de dados
                List<Veterinario> veterinariosAtualizados = connection.getTodosVeterinarios();
                tabelaVeterinarios.setItems(FXCollections.observableArrayList(veterinariosAtualizados));
            });

            btnVoltar2.setOnAction(event -> listStage.close());

            // Adicionar os componentes ao layout
            listLayout.getChildren().addAll(
                    new Label("Relatório de Veterinários"),
                    tabelaVeterinarios,
                    new HBox(10, btnAtualizar, btnVoltar2));

            // Configurar e mostrar a cena
            Scene listScene = new Scene(listLayout, 670, 500);
            listStage.setScene(listScene);
            listStage.show();
        });

        btnVoltar.setOnAction(e -> stage.close());

        layout.getChildren().addAll(
                new Label("Relatórios"),
                btnRelatorioClientes,
                btnRelatorioPets,
                btnRelatorioServicos,
                btnRelatorioVeterinarios,
                btnVoltar);

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
                        valorConsulta);
                veterinarios.add(novoVet);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setContentText("Veterinário cadastrado com sucesso!");
                connection.inserirVeterinario(novoVet);
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
            Stage listStage = new Stage();
            listStage.setTitle("Lista de Veterinários");

            VBox listLayout = new VBox(10);
            listLayout.setPadding(new Insets(20));
            listLayout.setAlignment(Pos.CENTER);

            // Obter a lista de veterinários do banco de dados
            List<Veterinario> listaVeterinarios = connection.getTodosVeterinarios();

            // Criar uma tabela para exibir os veterinários
            TableView<Veterinario> tabelaVeterinarios = new TableView<>();
            tabelaVeterinarios.setPrefHeight(400);
            tabelaVeterinarios.setPrefWidth(600);

            // Definir as colunas da tabela
            TableColumn<Veterinario, String> colNome = new TableColumn<>("Nome");
            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colNome.setPrefWidth(150);

            TableColumn<Veterinario, String> colCRMV = new TableColumn<>("CRMV");
            colCRMV.setCellValueFactory(new PropertyValueFactory<>("crmv"));
            colCRMV.setPrefWidth(100);

            TableColumn<Veterinario, String> colEspecialidade = new TableColumn<>("Especialidade");
            colEspecialidade.setCellValueFactory(new PropertyValueFactory<>("especialidade"));
            colEspecialidade.setPrefWidth(150);

            TableColumn<Veterinario, Number> colValorConsulta = new TableColumn<>("Valor Consulta");
            colValorConsulta.setCellValueFactory(new PropertyValueFactory<>("valorConsulta"));
            colValorConsulta.setPrefWidth(120);

            // Adicionar as colunas à tabela
            tabelaVeterinarios.getColumns().addAll(colNome, colCRMV, colEspecialidade, colValorConsulta);

            // Adicionar os veterinários à tabela (usando a lista do banco de dados)
            tabelaVeterinarios.setItems(FXCollections.observableArrayList(listaVeterinarios));

            Button btnAtualizar = new Button("Atualizar Lista");
            Button btnVoltar2 = new Button("Voltar");

            btnAtualizar.setOnAction(event -> {
                // Atualizar a lista de veterinários do banco de dados
                List<Veterinario> veterinariosAtualizados = connection.getTodosVeterinarios();
                tabelaVeterinarios.setItems(FXCollections.observableArrayList(veterinariosAtualizados));
            });

            btnVoltar2.setOnAction(event -> listStage.close());

            // Adicionar os componentes ao layout
            listLayout.getChildren().addAll(
                    new Label("Relatório de Veterinários"),
                    tabelaVeterinarios,
                    new HBox(10, btnAtualizar, btnVoltar2));

            // Configurar e mostrar a cena
            Scene listScene = new Scene(listLayout, 670, 500);
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
                btnVoltar);

        Scene scene = new Scene(layout, 400, 500);
        stage.setScene(scene);
        stage.show();
    }
}