# Sistema para Petshop em Java

Este projeto consiste de um sistema capaz de gerenciar o cadastro de clientes e seus pets em um
pet shop. . Ele permite a gestão de clientes e seus respectivos animais de
estimação, bem como a contratação de serviços avulsos ou pacotes de serviços.
O sistema também possibilita a relação entre clientes, pets e serviços
contratados.

## Funcionalidades Principais

* Cadastro de Clientes

    * Cadastro de clientes com informações como nome, telefone e e-mail.

    * Listagem, busca e exclusão de clientes cadastrados.

* Cadastro de Pets

    * Associação de um ou mais pets a cada cliente.

    * Registro de informações como nome, espécie, raça, idade e peso.

    * Listagem, busca e exclusão de pets cadastrados.

* Contratação de Serviços

    * Contratação de serviços avulsos ou pacotes de serviços.

    * Exemplo de serviços:

        * Banho e tosa

        * Consulta veterinária

        * Hospedagem

        * Adestramento

    * Listagem, busca e cancelamento de serviços contratados.

## Regras de Negócio

* Um cliente só pode contratar serviços para seus próprios pets cadastrados.

* Deve ser possível agendar serviços para datas futuras.

* Pacotes de serviços podem oferecer descontos em relação aos serviços avulsos.

## Estrutura do Sistema

O sistema segue um modelo orientado a objetos e conta com as seguintes classes principais:

* Cliente: Representa um cliente do pet shop.

* Connect: Classe de conexão e consulta ao banco de dados do sistema.

* Pet: Representa um animal de estimação de um cliente.

* Servico: Classe abstrata para serviços oferecidos pelo pet shop.

* BanhoETosa, ConsultaVeterinaria, Hospedagem, Adestramento: Extensões da classe Servico representando serviços específicos.

* PacoteServicos: Representa um conjunto de serviços oferecidos em um pacote promocional.

## Tecnologias Utilizadas

* Linguagem: Java 11+
* Biblioteca Gráfica: JavaFX
* Banco de Dados: MySQL rodando em servidor Oracle Cloud

## Instruções Importantes

* Para executar o programa, é necessário ter um banco de dados MySQL rodando em um servidor.

* Dentro da pasta "src", é necessário criar um arquivo chamado "server.conf" da seguinte forma:

    ```
    host=IP_DO_SERVIDOR
    port=3306 //PORTA DO SERVIDOR (PADRÃO DO MYSQL É 3306)
    user=USUARIO_DO_BANCO
    password=SENHA_DO_USUARIO_DO_BANCO
    database=NOME_DO_BANCO_DE_DADOS
    ```
* Preencha o arquivo com as informaçoes do servidor.

## Tabelas do Banco de Dados

* Tabela Clientes:
```
+----------+--------------+------+-----+---------+----------------+
| Field    | Type         | Null | Key | Default | Extra          |
+----------+--------------+------+-----+---------+----------------+
| id       | int          | NO   | PRI | NULL    | auto_increment |
| nome     | varchar(100) | NO   |     | NULL    |                |
| cpf      | varchar(20)  | NO   | UNI | NULL    |                |
| telefone | varchar(20)  | NO   | UNI | NULL    |                |
| email    | varchar(100) | NO   | UNI | NULL    |                |
| endereco | varchar(255) | NO   |     | NULL    |                |
+----------+--------------+------+-----+---------+----------------+
```
* Tabela Pets:
```
+-----------+---------------------------------+------+-----+---------+----------------+
| Field     | Type                            | Null | Key | Default | Extra          |
+-----------+---------------------------------+------+-----+---------+----------------+
| id        | int                             | NO   | PRI | NULL    | auto_increment |
| nome      | varchar(100)                    | NO   |     | NULL    |                |
| nome_dono | varchar(255)                    | YES  |     | NULL    |                |
| especie   | enum('Cachorro','Gato','Outro') | NO   |     | NULL    |                |
| raca      | varchar(100)                    | YES  |     | NULL    |                |
| idade     | int                             | YES  |     | NULL    |                |
| peso      | decimal(5,2)                    | YES  |     | NULL    |                |
+-----------+---------------------------------+------+-----+---------+----------------+
```
* Tabela Banho_Tosa:
```
+---------------+---------------+------+-----+---------+----------------+
| Field         | Type          | Null | Key | Default | Extra          |
+---------------+---------------+------+-----+---------+----------------+
| id            | int           | NO   | PRI | NULL    | auto_increment |
| nome_cliente  | text          | NO   |     | NULL    |                |
| nome_pet      | text          | NO   |     | NULL    |                |
| data_servico  | date          | NO   |     | NULL    |                |
| tosa_profunda | tinyint(1)    | YES  |     | 0       |                |
| perfume       | tinyint(1)    | YES  |     | 0       |                |
| gravata       | tinyint(1)    | YES  |     | 0       |                |
| valor_servico | decimal(10,2) | YES  |     | NULL    |                |
+---------------+---------------+------+-----+---------+----------------+
```

* Tabela Adestramento:
```
+------------------+---------------+------+-----+---------+----------------+
| Field            | Type          | Null | Key | Default | Extra          |
+------------------+---------------+------+-----+---------+----------------+
| id               | int           | NO   | PRI | NULL    | auto_increment |
| nome_cliente     | text          | NO   |     | NULL    |                |
| nome_pet         | text          | NO   |     | NULL    |                |
| data_servico     | date          | NO   |     | NULL    |                |
| numero_sessoes   | int           | NO   |     | NULL    |                |
| tipo_treinamento | text          | NO   |     | NULL    |                |
| domiciliar       | tinyint(1)    | YES  |     | 0       |                |
| valor_servico    | decimal(10,2) | NO   |     | NULL    |                |
+------------------+---------------+------+-----+---------+----------------+
```

* Tabela Consulta:
```
+----------------+---------------+------+-----+---------+----------------+
| Field          | Type          | Null | Key | Default | Extra          |
+----------------+---------------+------+-----+---------+----------------+
| id             | int           | NO   | PRI | NULL    | auto_increment |
| nome_cliente   | text          | NO   |     | NULL    |                |
| nome_pet       | text          | NO   |     | NULL    |                |
| data_servico   | date          | NO   |     | NULL    |                |
| veterinario    | text          | NO   |     | NULL    |                |
| urgente        | tinyint(1)    | YES  |     | 0       |                |
| valor_consulta | decimal(10,2) | NO   |     | NULL    |                |
+----------------+---------------+------+-----+---------+----------------+
```

* Tabela Hospedagem:
```
+----------------------+---------------+------+-----+---------+----------------+
| Field                | Type          | Null | Key | Default | Extra          |
+----------------------+---------------+------+-----+---------+----------------+
| id                   | int           | NO   | PRI | NULL    | auto_increment |
| nome_cliente         | text          | NO   |     | NULL    |                |
| nome_pet             | text          | NO   |     | NULL    |                |
| data_servico         | date          | NO   |     | NULL    |                |
| numero_dias          | int           | NO   |     | NULL    |                |
| suite_luxo           | tinyint(1)    | YES  |     | 0       |                |
| servico_spa          | tinyint(1)    | YES  |     | 0       |                |
| alimentacao_especial | tinyint(1)    | YES  |     | 0       |                |
| valor_servico        | decimal(10,2) | NO   |     | NULL    |                |
+----------------------+---------------+------+-----+---------+----------------+
```

* Tabela Serviços:
```
+---------------+---------------+------+-----+---------+----------------+
| Field         | Type          | Null | Key | Default | Extra          |
+---------------+---------------+------+-----+---------+----------------+
| id            | int           | NO   | PRI | NULL    | auto_increment |
| nome_cliente  | varchar(100)  | YES  |     | NULL    |                |
| nome_pet      | varchar(100)  | NO   |     | NULL    |                |
| data          | date          | YES  |     | NULL    |                |
| tipo_servico  | varchar(100)  | YES  |     | NULL    |                |
| valor_servico | decimal(10,2) | NO   |     | NULL    |                |
+---------------+---------------+------+-----+---------+----------------+
```

* Tabela Veterinários:
```
+----------------+---------------+------+-----+---------+----------------+
| Field          | Type          | Null | Key | Default | Extra          |
+----------------+---------------+------+-----+---------+----------------+
| id             | int           | NO   | PRI | NULL    | auto_increment |
| nome           | varchar(100)  | NO   |     | NULL    |                |
| crmv           | varchar(20)   | NO   | UNI | NULL    |                |
| especialidade  | varchar(100)  | YES  |     | NULL    |                |
| valor_consulta | decimal(10,2) | NO   |     | NULL    |                |
+----------------+---------------+------+-----+---------+----------------+
```

## Autores

Henrique Luza dos Santos e Enzo Gutiérrez Pereira, Trabalho da Matéria de Programação II