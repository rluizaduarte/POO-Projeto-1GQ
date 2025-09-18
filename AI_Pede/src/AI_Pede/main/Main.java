// --- Arquivo: main/Main.java ---
package AI_Pede.main;

import AI_Pede.model.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static Restaurante restaurante = null;
    private static final Scanner scanner = new Scanner(System.in);
    private static Caixa caixaAtual = null;

    public static void main(String[] args) {
        // ALTERAÇÃO: A limpeza de tela inicial foi reintroduzida.
        TerminalUI.limparTela();
        System.out.println("\n" + TerminalUI.ANSI_GREEN + "BEM-VINDO AO SISTEMA AI PEDE!" + TerminalUI.ANSI_RESET);

        System.out.print("Por favor, digite o nome do seu restaurante: ");
        String nomeRestaurante = scanner.nextLine();
        restaurante = new Restaurante(nomeRestaurante);

        TerminalUI.exibirMensagem("Restaurante '" + restaurante.getNome() + "' carregado com sucesso!");
        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();

        while (true) {
            if (caixaAtual == null) {
                exibirMenuCaixaFechado();
            } else {
                exibirMenuCaixaAberto();
            }
        }
    }

    // O restante da classe Main não precisa de alterações.
    // Cole o restante dos métodos (exibirMenuCaixaFechado, abrirCaixa, etc.) da versão anterior aqui.
    private static void exibirMenuCaixaFechado() {
        TerminalUI.exibirCabecalho(restaurante.getNome().toUpperCase() + " - CAIXA FECHADO");

        System.out.println("1. Abrir Caixa");
        System.out.println("2. Gerenciar Cardápio");
        System.out.println("3. Gerar Relatório Histórico");
        System.out.println("9. Sair do Sistema");
        System.out.print("Escolha uma opção: ");

        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: abrirCaixa(); break;
                case 2: gerenciarCardapio(); break;
                case 3: gerarRelatorioHistorico(); break;
                case 9:
                    System.out.println("\nObrigado por usar o AI Pede!");
                    System.exit(0);
                default: TerminalUI.exibirErro("Opção inválida.");
            }
        } catch (InputMismatchException e) {
            TerminalUI.exibirErro("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine();
        }
    }

    private static void exibirMenuCaixaAberto() {
        TerminalUI.exibirCabecalho("CAIXA ABERTO - " + caixaAtual.getData());
        System.out.println("1. Criar Novo Pedido (Carrinho)");
        System.out.println("2. Listar Pedidos do Dia");
        System.out.println("3. Fechar Caixa");
        System.out.print("Escolha uma opção: ");

        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: criarNovoCarrinho(); break;
                case 2: listarPedidosDoDia(); break;
                case 3: fecharCaixa(); break;
                default: TerminalUI.exibirErro("Opção inválida.");
            }
        } catch (InputMismatchException e) {
            TerminalUI.exibirErro("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine();
        }
    }

    private static void abrirCaixa() {
        TerminalUI.exibirCabecalho("ABERTURA DE CAIXA");
        while (true) {
            System.out.print("Digite a data de hoje (DD/MM/AAAA): ");
            String dataInput = scanner.nextLine();
            try {
                String[] partes = dataInput.split("/");
                LocalDate data = LocalDate.of(Integer.parseInt(partes[2]), Integer.parseInt(partes[1]), Integer.parseInt(partes[0]));
                caixaAtual = new Caixa(data);
                TerminalUI.exibirMensagem("Caixa aberto com sucesso para o dia " + data + "!");
                break;
            } catch (Exception e) {
                TerminalUI.exibirErro("Formato de data inválido. Tente novamente usando DD/MM/AAAA.");
            }
        }
    }

    private static void fecharCaixa() {
        TerminalUI.exibirCabecalho("FECHAMENTO DE CAIXA");
        System.out.printf("Total de Pedidos no Dia: %d\n", caixaAtual.getPedidosDoDia().size());
        System.out.printf("Faturamento Final do Dia: R$ %.2f\n", caixaAtual.calcularFaturamentoTotal());

        restaurante.adicionarCaixaAoHistorico(caixaAtual);
        caixaAtual = null;
        TerminalUI.exibirMensagem("\nCaixa fechado e adicionado ao histórico.");
        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();
    }

    private static void gerenciarCardapio() {
        int opcao = 0;
        while (opcao != 9) {
            TerminalUI.exibirCabecalho("GERENCIAR CARDÁPIO");
            System.out.println("1. Adicionar Item");
            System.out.println("2. Remover Item");
            System.out.println("3. Listar Itens");
            System.out.println("9. Voltar ao Menu Anterior");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.print("Nome do novo item: ");
                        String nome = scanner.nextLine();
                        System.out.print("Descrição do item: ");
                        String desc = scanner.nextLine();
                        System.out.print("Preço de venda (ex: 25.50): ");
                        double preco = scanner.nextDouble();
                        System.out.print("Custo de produção (ex: 10.25): ");
                        double custo = scanner.nextDouble();
                        scanner.nextLine();
                        restaurante.adicionarItemCardapio(new ItemCardapio(nome, desc, preco, custo));
                        TerminalUI.exibirMensagem("Item adicionado com sucesso!");
                        break;
                    case 2:
                        System.out.print("Digite o ID ou o NOME do item a ser removido: ");
                        String idOuNome = scanner.nextLine();
                        if (restaurante.removerItemCardapio(idOuNome)) {
                            TerminalUI.exibirMensagem("Item removido com sucesso.");
                        } else {
                            TerminalUI.exibirErro("Item não encontrado.");
                        }
                        break;
                    case 3:
                        TerminalUI.exibirCabecalho("CARDÁPIO ATUAL");
                        if(restaurante.getCardapio().isEmpty()) {
                            System.out.println("Cardápio vazio.");
                        } else {
                            restaurante.getCardapio().forEach(item -> {
                                System.out.println(item);
                                TerminalUI.exibirSeparador();
                            });
                        }
                        System.out.print("\nPressione Enter para continuar...");
                        scanner.nextLine();
                        break;
                    case 9:
                        TerminalUI.exibirMensagem("Voltando ao menu anterior...");
                        break;
                    default:
                        TerminalUI.exibirErro("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                TerminalUI.exibirErro("Entrada inválida. Por favor, use apenas números.");
                scanner.nextLine();
            }
        }
    }

    private static void criarNovoCarrinho() {
        Carrinho carrinho = new Carrinho();
        int opcao = 0;

        while (opcao != 8 && opcao != 9) {
            TerminalUI.exibirCabecalho("MONTAGEM DO PEDIDO");
            System.out.println(TerminalUI.ANSI_GREEN + "Itens atuais no carrinho:" + TerminalUI.ANSI_RESET);
            if(carrinho.getItens().isEmpty()) {
                System.out.println("Carrinho vazio.");
            } else {
                carrinho.getItens().forEach(System.out::println);
            }
            TerminalUI.exibirSeparador();

            System.out.println("1. Adicionar Item");
            System.out.println("2. Remover Item");
            System.out.println("3. Adicionar Observação");
            System.out.println("8. Finalizar Pedido");
            System.out.println("9. Cancelar Pedido");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        if(restaurante.getCardapio().isEmpty()){
                            System.out.println("O cardápio está vazio. Adicione itens no menu principal.");
                            break;
                        }
                        TerminalUI.exibirCabecalho("CARDÁPIO");
                        restaurante.getCardapio().forEach(System.out::println);
                        System.out.print("\nDigite o ID do item para adicionar: ");
                        int idItem = scanner.nextInt();
                        scanner.nextLine();
                        ItemCardapio itemSelecionado = restaurante.buscarItemPorId(idItem);
                        if (itemSelecionado != null) {
                            System.out.print("Digite a quantidade: ");
                            int qtd = scanner.nextInt();
                            scanner.nextLine();
                            if(qtd > 0) {
                                carrinho.adicionarItem(itemSelecionado, qtd);
                                TerminalUI.exibirMensagem("Item adicionado!");
                            } else {
                                TerminalUI.exibirErro("Quantidade deve ser positiva.");
                            }
                        } else {
                            TerminalUI.exibirErro("ID de item inválido!");
                        }
                        break;
                    case 2:
                        if (carrinho.getItens().isEmpty()){
                            System.out.println("O carrinho está vazio.");
                            break;
                        }
                        System.out.println("\n--- Itens no Carrinho ---");
                        carrinho.getItens().forEach(item -> System.out.println(item.getItem()));
                        System.out.print("Digite o ID do item do cardápio que deseja remover: ");
                        int idParaRemover = scanner.nextInt();
                        scanner.nextLine();
                        carrinho.removerItem(idParaRemover);
                        break;
                    case 3:
                        System.out.print("Digite a observação: ");
                        String obs = scanner.nextLine();
                        carrinho.setObservacao(obs);
                        TerminalUI.exibirMensagem("Observação adicionada.");
                        break;
                    case 8:
                        if (carrinho.getItens().isEmpty()) {
                            TerminalUI.exibirErro("Não é possível finalizar um pedido vazio.");
                            opcao = 0;
                        } else {
                            finalizarPedido(carrinho);
                        }
                        break;
                    case 9:
                        TerminalUI.exibirMensagem("Pedido cancelado.");
                        break;
                    default:
                        TerminalUI.exibirErro("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                TerminalUI.exibirErro("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }

    private static void finalizarPedido(Carrinho carrinho) {
        try {
            TerminalUI.exibirCabecalho("FINALIZAR PEDIDO");
            System.out.print("Haverá desconto? Digite a porcentagem (ou 0 para nenhum): ");
            double desconto = scanner.nextDouble();
            scanner.nextLine();
            carrinho.setPercentualDesconto(desconto);

            System.out.print("\nQual a modalidade? (1-Mesa, 2-Retirada): ");
            int tipoModalidade = scanner.nextInt();
            scanner.nextLine();
            if (tipoModalidade == 1) {
                System.out.print("Quantas pessoas na mesa?: ");
                int pessoas = scanner.nextInt();
                scanner.nextLine();
                carrinho.setModalidade(new ModalidadeMesa(pessoas));
            } else {
                System.out.print("Nome do cliente para retirada: ");
                String nome = scanner.nextLine();
                carrinho.setModalidade(new ModalidadeRetirada(nome));
            }

            System.out.printf("\nValor final do pedido: R$ %.2f\n", carrinho.getValorFinal());
            System.out.print("Forma de Pagamento (1-PIX, 2-Cartão, 3-Dinheiro): ");
            int tipoPagamento = scanner.nextInt();
            scanner.nextLine();

            Pagamento pagamento;
            switch (tipoPagamento) {
                case 2:
                    pagamento = new PagamentoCartao(carrinho.getValorFinal());
                    break;
                case 3:
                    double valorRecebido;
                    while (true) {
                        System.out.print("Valor recebido em dinheiro: ");
                        valorRecebido = scanner.nextDouble();
                        scanner.nextLine();
                        if (valorRecebido >= carrinho.getValorFinal()) {
                            break;
                        } else {
                            TerminalUI.exibirErro(String.format("Valor insuficiente. Faltam R$ %.2f", carrinho.getValorFinal() - valorRecebido));
                        }
                    }
                    pagamento = new PagamentoDinheiro(carrinho.getValorFinal(), valorRecebido);
                    break;
                default:
                    pagamento = new PagamentoPix(carrinho.getValorFinal());
                    break;
            }
            carrinho.setPagamento(pagamento);

            caixaAtual.adicionarPedido(carrinho);
            TerminalUI.exibirMensagem("Pedido Finalizado com Sucesso!");
            System.out.println(carrinho);

            System.out.print("\nPressione Enter para continuar...");
            scanner.nextLine();

        } catch (InputMismatchException e) {
            TerminalUI.exibirErro("Entrada inválida na finalização. O pedido não foi concluído.");
            scanner.nextLine();
        }
    }

    private static void listarPedidosDoDia() {
        TerminalUI.exibirCabecalho("PEDIDOS DO DIA: " + caixaAtual.getData());
        if (caixaAtual.getPedidosDoDia().isEmpty()) {
            System.out.println("Nenhum pedido foi finalizado hoje.");
        } else {
            for (Carrinho pedido : caixaAtual.getPedidosDoDia()) {
                System.out.println(pedido);
                TerminalUI.exibirSeparador();
            }
        }

        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();
    }

    private static void gerarRelatorioHistorico() {
        TerminalUI.exibirCabecalho("RELATÓRIO HISTÓRICO");
        IRelatorio relatorio = new RelatorioCompletoDiario();
        String relatorioGerado = relatorio.gerar(restaurante.getHistoricoDeCaixas());
        System.out.println(relatorioGerado);

        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();
    }
}