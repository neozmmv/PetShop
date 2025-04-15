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

## Autores

Henrique Luza dos Santos e Enzo Gutiérrez Pereira, Trabalho da Matéria de Programação II