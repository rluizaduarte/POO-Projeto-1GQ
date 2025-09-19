// --- Arquivo: main/Main.java ---
package AI_Pede.main;

import AI_Pede.model.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    // A variável 'restaurante' agora é iniciada como 'null'.
    // Ela será criada depois que o usuário fornecer o nome.
    private static Restaurante restaurante = null;
    private static final Scanner scanner = new Scanner(System.in);
    private static Caixa caixaAtual = null;

    public static void main(String[] args) {
        TerminalUI.limparTela();
        System.out.println("\n" + TerminalUI.ANSI_GREEN + "Bem-vindo(a) ao AI Pede!\n" + TerminalUI.ANSI_RESET);

        // Pergunta o nome do restaurante no início.
        System.out.print("Nome do seu restaurante: ");
        String nomeRestaurante = scanner.nextLine();
        // Cria o objeto Restaurante com o nome fornecido pelo usuário.
        restaurante = new Restaurante(nomeRestaurante);

        System.out.println("Restaurante '" + restaurante.getNome() + "' cadastrado.");
        System.out.print("\nEnter para continuar...");
        scanner.nextLine();

        while (true) {
            if (caixaAtual == null) {
                exibirMenuCaixaFechado();
            } else {
                exibirMenuCaixaAberto();
            }
        }
    }

    private static void exibirMenuCaixaFechado() {
        TerminalUI.exibirCabecalho(restaurante.getNome().toUpperCase() + " - CAIXA FECHado");

        System.out.println("1. Abrir Caixa");
        System.out.println("2. Gerenciar Cardápio");
        System.out.println("3. Gerar Relatório Histórico");
        System.out.println("9. Sair do Sistema");
        System.out.print("\nEscolha uma opção: ");

        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: abrirCaixa(); break;
                case 2: gerenciarCardapio(); break;
                case 3: gerarRelatorioHistorico(); break;
                case 9:
                    System.out.println("\nObrigada por usar o AI Pede!");
                    System.exit(0);
            }
        } catch (InputMismatchException e) {
            TerminalUI.limparTela();
            TerminalUI.exibirErro("Entrada inválida. Digite um número.");
            scanner.nextLine();
            System.out.print("\nEnter para continuar...");
            scanner.nextLine();
        }
    }

    private static void exibirMenuCaixaAberto() {
        TerminalUI.exibirCabecalho("CAIXA ABERTO - " + caixaAtual.getData());
        System.out.println("1. Criar Novo Pedido");
        System.out.println("2. Listar Pedidos do Dia");
        System.out.println("3. Fechar Caixa");
        System.out.print("\nEscolha uma opção: ");

        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: criarNovoCarrinho(); break;
                case 2: listarPedidosDoDia(); break;
                case 3: fecharCaixa(); break;
            }
        } catch (InputMismatchException e) {
            TerminalUI.limparTela();
            TerminalUI.exibirErro("Entrada inválida. Digite um número.");
            scanner.nextLine();
            System.out.print("\nEnter para continuar...");
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
                System.out.println("\n" + TerminalUI.ANSI_GREEN + "Caixa aberto com sucesso!" + TerminalUI.ANSI_RESET);
                System.out.print("\nEnter para continuar...");
                scanner.nextLine();
                break;
            } catch (Exception e) {
                TerminalUI.exibirErro("Formato inválido. Use DD/MM/AAAA.\n");
            }
        }
    }

    private static void fecharCaixa() {
        TerminalUI.exibirCabecalho("FECHAMENTO DE CAIXA");
        System.out.printf("Total de Pedidos no Dia: %d\n", caixaAtual.getPedidosDoDia().size());
        System.out.printf("Faturamento Final do Dia: R$ %.2f\n", caixaAtual.calcularFaturamentoTotal());

        restaurante.adicionarCaixaAoHistorico(caixaAtual);
        caixaAtual = null;
        System.out.println("\n" + TerminalUI.ANSI_GREEN + "Caixa fechado e adicionado ao histórico." + TerminalUI.ANSI_RESET);
        System.out.print("\nEnter para continuar...");
        scanner.nextLine();
    }

    private static void gerenciarCardapio() {
        int opcao = 0;
        while (opcao != 9) {
            TerminalUI.exibirCabecalho("GERENCIAR CARDÁPIO");
            System.out.println("1. Adicionar Item");
            System.out.println("2. Remover Item");
            System.out.println("3. Listar Itens");
            System.out.println("9. Voltar");
            System.out.print("\nEscolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.print("Nome do novo item: ");
                        String nome = scanner.nextLine();
                        System.out.print("Descrição do item: ");
                        String desc = scanner.nextLine();
                        System.out.print("Preço de venda: ");
                        double preco = scanner.nextDouble();
                        System.out.print("Custo de produção: ");
                        double custo = scanner.nextDouble();
                        scanner.nextLine();
                        restaurante.adicionarItemCardapio(new ItemCardapio(nome, desc, preco, custo));
                        System.out.println("\n" + TerminalUI.ANSI_GREEN + "Item adicionado com sucesso!" + TerminalUI.ANSI_RESET);
                        System.out.print("\nEnter para continuar...");
                        scanner.nextLine();
                        break;
                    case 2:
                        System.out.print("Digite o ID ou o NOME do item a ser removido: ");
                        String idOuNome = scanner.nextLine();
                        if (restaurante.removerItemCardapio(idOuNome)) {
                            System.out.println("\n" + TerminalUI.ANSI_GREEN + "Item removido com sucesso!" + TerminalUI.ANSI_RESET);
                            System.out.print("\nEnter para continuar...");
                            scanner.nextLine();
                        } else {
                            TerminalUI.limparTela();
                            TerminalUI.exibirErro("Item não encontrado.");
                            System.out.print("\nEnter para continuar...");
                            scanner.nextLine();
                        }
                        break;
                    case 3:
                        TerminalUI.exibirCabecalho("CARDÁPIO ATUAL");
                        if(restaurante.getCardapio().isEmpty()) {
                            TerminalUI.limparTela();
                            TerminalUI.exibirErro("Cardápio vazio.");
                        } else {
                            restaurante.getCardapio().forEach(item -> {
                                System.out.println(item);
                            });
                        }
                        System.out.print("\nEnter para continuar...");
                        scanner.nextLine();
                        break;
                    case 9:
                        TerminalUI.exibirMensagem("Voltando ao menu anterior...");
                        System.out.print("\nEnter para continuar...");
                        scanner.nextLine();
                        break;
                    default:
                        TerminalUI.exibirErro("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                TerminalUI.limparTela();
                TerminalUI.exibirErro("Entrada inválida. Digite um número.");
                scanner.nextLine();
                System.out.print("\nEnter para continuar...");
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
            System.out.print("\nEscolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        if(restaurante.getCardapio().isEmpty()){
                            TerminalUI.limparTela();
                            TerminalUI.exibirErro("O cardápio está vazio. Adicione itens no menu principal.");
                            System.out.print("\nEnter para continuar...");
                            scanner.nextLine();
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
                                System.out.println("\n" + TerminalUI.ANSI_GREEN + "Item adicionado ao carrinho com sucesso!" + TerminalUI.ANSI_RESET);;
                                System.out.print("\nEnter para continuar...");
                                scanner.nextLine();
                            } else {
                                TerminalUI.exibirErro("Quantidade deve ser positiva.");
                                System.out.print("\nEnter para continuar...");
                                scanner.nextLine();
                            }
                        } else {
                            TerminalUI.limparTela();
                            TerminalUI.exibirErro("ID de item inválido!");
                            System.out.print("\nEnter para continuar...");
                            scanner.nextLine();
                        }
                        break;
                    case 2:
                        if (carrinho.getItens().isEmpty()){
                            TerminalUI.limparTela();
                            TerminalUI.exibirErro("O carrinho está vazio.");
                            System.out.print("\nEnter para continuar...");
                            scanner.nextLine();
                            break;
                        }
                        TerminalUI.limparTela();
                        System.out.println("\n" + TerminalUI.ANSI_GREEN + "Itens no carrinho" + TerminalUI.ANSI_RESET);
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
                        TerminalUI.exibirMensagem("\n" + TerminalUI.ANSI_GREEN + "Observação adicionada." + TerminalUI.ANSI_RESET);
                        System.out.print("\nEnter para continuar...");
                        scanner.nextLine();
                        break;
                    case 8:
                        if (carrinho.getItens().isEmpty()) {
                            TerminalUI.limparTela();
                            TerminalUI.exibirErro("Não é possível finalizar o pedido.");
                            System.out.print("\nEnter para continuar...");
                            scanner.nextLine();
                            opcao = 0;
                            break;
                        } else {
                            finalizarPedido(carrinho);
                        }
                        break;
                    case 9:
                        TerminalUI.exibirMensagem("Pedido cancelado.");
                        System.out.print("\nEnter para continuar...");
                        scanner.nextLine();
                        break;
                }
            } catch (InputMismatchException e) {
                TerminalUI.limparTela();
                TerminalUI.exibirErro("Entrada inválida. Digite um número.");
                scanner.nextLine();
                System.out.print("\nEnter para continuar...");
                scanner.nextLine();
            }
        }
    }

    private static void finalizarPedido(Carrinho carrinho) {
        try {
            TerminalUI.exibirCabecalho("FINALIZAR PEDIDO");
            System.out.print("Adicionar desconto\nDigite a porcentagem (0-100): ");
            double desconto = scanner.nextDouble();
            scanner.nextLine();
            carrinho.setPercentualDesconto(desconto);

            System.out.print("\nSelecionar modalidade\n1-Mesa | 2-Retirada: ");
            int tipoModalidade = scanner.nextInt();
            scanner.nextLine();
            if (tipoModalidade == 1) {
                System.out.print("Quantidade de pessoas na mesa: ");
                int pessoas = scanner.nextInt();
                scanner.nextLine();
                carrinho.setModalidade(new ModalidadeMesa(pessoas));
            } else {
                System.out.print("Nome do cliente para retirada: ");
                String nome = scanner.nextLine();
                carrinho.setModalidade(new ModalidadeRetirada(nome));
            }

            System.out.printf("\nValor final do pedido: R$ %.2f\n", carrinho.getValorFinal());
            System.out.print("Selecione a Forma de Pagamento\n1-PIX | 2-Cartão | 3-Dinheiro: ");
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
                        System.out.print("Valor em espécie recebido: ");
                        valorRecebido = scanner.nextDouble();
                        scanner.nextLine();
                        if (valorRecebido >= carrinho.getValorFinal()) {
                            break;
                        } else {
                            TerminalUI.limparTela();
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
            System.out.println("\n" + TerminalUI.ANSI_GREEN + "Pedido finalizado com sucesso!" + TerminalUI.ANSI_RESET);
            System.out.println("\n" + carrinho);

            System.out.print("Enter para continuar...");
            scanner.nextLine();

        } catch (InputMismatchException e) {
            TerminalUI.limparTela();
            TerminalUI.exibirErro("Entrada inválida na finalização. O pedido não foi concluído.");
            scanner.nextLine();
            System.out.print("\nEnter para continuar...");
            scanner.nextLine();
        }
    }

    private static void listarPedidosDoDia() {
        TerminalUI.exibirCabecalho("PEDIDOS DO DIA: " + caixaAtual.getData());
        if (caixaAtual.getPedidosDoDia().isEmpty()) {
            TerminalUI.limparTela();
            TerminalUI.exibirErro("Nenhum pedido foi finalizado hoje.");
            scanner.nextLine();
        } else {
            for (Carrinho pedido : caixaAtual.getPedidosDoDia()) {
                System.out.println(pedido);
                TerminalUI.exibirSeparador();
            }
        }

        System.out.print("\nEnter para continuar...");
        scanner.nextLine();
    }

    private static void gerarRelatorioHistorico() {
        TerminalUI.exibirCabecalho("RELATÓRIO HISTÓRICO");
        IRelatorio relatorio = new RelatorioCompletoDiario();
        String relatorioGerado = relatorio.gerar(restaurante.getHistoricoDeCaixas());
        System.out.println(relatorioGerado);

        System.out.print("\nEnter para continuar...");
        scanner.nextLine();
    }
}