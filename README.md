# 🚀 AI Pede - Sistema de Gestão para Restaurantes

Bem-vindo ao AI Pede, um sistema de ponto de venda (PDV) e gestão desenvolvido em Java, focado em fornecer insights estratégicos para restaurantes. Este projeto foi construído como parte da disciplina de Programação Orientada a Objetos, aplicando conceitos fundamentais de POO para criar uma solução robusta, flexível e extensível.

O sistema opera via terminal e simula as operações diárias de um restaurante, desde a gestão do cardápio até o fecho do caixa, culminando na geração de relatórios analíticos.

## ✨ Features Principais

* **Gestão de Estado Dinâmica:** O sistema opera em dois estados distintos (Caixa Aberto / Caixa Fechado), oferecendo menus e funcionalidades contextuais para cada momento da operação.
* **Cardápio Dinâmico:** Permite que o operador adicione, remova e liste os itens do cardápio em tempo de execução, sem necessidade de reiniciar o programa.
* **Criação de Pedidos Interativa:** Utiliza um conceito de "Carrinho" que permite adicionar/remover itens, incluir observações e aplicar descontos antes de finalizar o pedido.
* **Análise de Dados Históricos:** Armazena o registo de múltiplos dias de operação (caixas fechados), permitindo a geração de relatórios que analisam tendências ao longo do tempo.
* **Interface de Terminal Rica:** Oferece uma experiência de utilizador limpa e organizada, com cabeçalhos padronizados, cores para ênfase e pausas estratégicas para melhor legibilidade.

## 🎯 Perguntas Estratégicas Respondidas

O núcleo do sistema foi projetado para responder a perguntas de negócio cruciais, transformando dados operacionais em inteligência para o gestor do restaurante:

a. Qual o dia da semana com o maior fluxo de pessoas?

b. Qual a forma de pagamento mais utilizada diariamente?

c. Qual o lucro médio semanal?

d. Quais itens do cardápio são mais pedidos?

e. Qual modalidade vende mais (retirada ou mesa)?

f. Qual o ticket médio geral?

## 🏗️ Design e Arquitetura

O projeto foi modelado seguindo os pilares da Programação Orientada a Objetos. A arquitetura é baseada em uma clara separação de responsabilidades, com um pacote `model` para as entidades de negócio e um pacote `main` para o controlo da execução e interface com o utilizador.

### Principais Conceitos de POO Aplicados

> A aplicação prática dos conceitos teóricos foi um pilar deste desenvolvimento, resultando num código de alta coesão e baixo acoplamento.

* **Herança e Classes Abstratas:** As hierarquias de `Pagamento` e `Modalidade` são exemplos centrais. Criamos classes abstratas base (`Pagamento.java`, `Modalidade.java`) que definem um contrato comum, enquanto as classes filhas (`PagamentoPix.java`, `ModalidadeMesa.java`, etc.) fornecem as implementações específicas.

* **Interfaces e Polimorfismo:** A interface `IRelatorio.java` define um contrato `gerar()`, que desacopla a lógica de geração de relatórios do resto do sistema. A classe `Main` pode invocar o relatório através da interface, sem precisar de conhecer os detalhes da sua implementação (`RelatorioCompletoDiario.java`).

* **Encapsulamento:** Todas as classes do pacote `model` (como `Carrinho.java` e `ItemCardapio.java`) utilizam atributos privados, expondo o seu estado de forma controlada através de métodos públicos (`getters` e `setters`), protegendo a integridade dos dados.

* **Separação de Responsabilidades (SoC):** A responsabilidade de formatar e exibir a interface do terminal foi extraída da classe `Main` e centralizada na classe `TerminalUI.java`. Graças a isso, alterações estéticas (como mudar a cor do sistema) puderam ser feitas modificando apenas um ficheiro, sem impactar a lógica de negócio.

## ⚙️ Como Executar o Projeto

### Pré-requisitos
* Java Development Kit (JDK) versão 17 ou superior instalado e configurado no PATH do sistema.

### Execução via Terminal
1.  Navegue até a pasta raiz do projeto (`/POO-Projeto-1GQ-6dee3a9f800a3e290f48c632aeeeacfffaac976a`).
2.  Compile todos os ficheiros `.java` a partir da pasta `src`, colocando os ficheiros `.class` na pasta `bin`:
    ```bash
    javac -d AI_Pede/bin AI_Pede/src/AI_Pede/model/*.java AI_Pede/src/AI_Pede/main/*.java
    ```
3.  Execute a classe `Main`, especificando o `classpath` para a pasta `bin`:
    ```bash
    java -cp AI_Pede/bin AI_Pede.main.Main
    ```

### Execução via IDE (VS Code / IntelliJ)
1.  Abra a pasta do projeto (`AI_Pede`) na sua IDE.
2.  Certifique-se de que a IDE reconheceu o JDK corretamente.
3.  Localize o ficheiro `src/AI_Pede/main/Main.java`.
4.  Clique com o botão direito sobre o ficheiro e selecione a opção "Run" (Executar).

## 🗂️ Estrutura de Ficheiros

A estrutura do código-fonte está organizada da seguinte forma:

     └── src
        └── AI_Pede
        ├── main
        │   ├── Main.java                 (Controlador principal e loop da aplicação)
        │   └── TerminalUI.java           (Classe utilitária para a interface do terminal)
        └── model
            ├── Caixa.java                (Gere as operações de um dia)
            ├── Carrinho.java             (Modela um pedido/carrinho de compras)
            ├── IRelatorio.java           (Interface para geradores de relatório)
            ├── ItemCardapio.java         (Modela um item do menu)
            ├── ItemPedido.java           (Modela um item dentro de um carrinho)
            ├── Modalidade.java           (Classe abstrata para modalidades)
            ├── ModalidadeMesa.java       (Implementação para pedidos em mesa)
            ├── ModalidadeRetirada.java   (Implementação para pedidos de retirada)
            ├── Pagamento.java            (Classe abstrata para pagamentos)
            ├── PagamentoCartao.java
            ├── PagamentoDinheiro.java
            ├── PagamentoPix.java
            ├── RelatorioCompletoDiario.java (Implementação do relatório estratégico)
            └── Restaurante.java          (Classe principal que agrega os dados)
        
## 👥 Autores
* Rielly Luiza
* Camila Torquato
