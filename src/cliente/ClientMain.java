package cliente;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import interfaces.MensageiroInterface;

public class ClientMain {

private static void exibirMenu() {
    System.out.println("================ M E N U ==================");
    System.out.println("    1 - Criar Conta");
    System.out.println("    2 - Remover Conta");
    System.out.println("    3 - Depositar dinheiro na Conta");
    System.out.println("    4 - Sacar dinheiro da Conta");
    System.out.println("    5 - Transferir dinheiro entre Contas");
    System.out.println("    6 - Saldo da Conta");
    System.out.println("    7 - Listar Contas Abertas");
    System.out.println("    0 - Sair");
    System.out.println("===========================================");
    System.out.print("Opção: ");
}    

    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(123);
//        MensageiroInterface gerente = (MensageiroInterface) registry.lookup("gestor");
        MensageiroInterface gerente = (MensageiroInterface) Naming.lookup("rmi://localhost:123/gestor");
        Scanner sc = new Scanner(System.in);
        String nome;
        int conta, conta1, conta2;
        double valor;
        String op = "1";
        while (!op.equals("0")) {            
            exibirMenu();
            op = sc.next();
            switch (op) {
                case "1":
                    System.out.println("Criar Conta");
                    System.out.print("Informe o nome do cliente:   ");
                    nome = sc.next();
                    System.out.print("Informe o n�mero da conta:  ");
                    conta1 = sc.nextInt();
                    if (gerente.criarConta(nome, conta1)) {
                        System.out.println("Conta criada com sucesso!");
                    } else {
                        System.out.println("J� existe uma Conta com esse n�mero.");
                    }
                    break;
                case "2":
                    System.out.println("Remover Conta");
                    System.out.print("Informe o n�mero da conta:    ");
                    conta = sc.nextInt();
                    if (gerente.removerConta(conta)) {
                        System.out.println("Conta removida com sucesso!");
                    } else {
                        System.out.println("N�o existe uma Conta com esse n�mero.");
                    }
                    break;
               case "3":
                    System.out.println("Depositar dinheiro na Conta");
                    System.out.print("Informe o n�mero da conta:    ");
                    conta = sc.nextInt();
                    System.out.print("Qual o valor a ser depositado?    ");
                    valor = sc.nextInt();
                    if (gerente.depositar(conta, valor)) {
                        System.out.println("Deposito efetuado com sucesso!");
                    } else {
                        System.out.println("N�o existe uma Conta com esse n�mero.");
                    }
                    break;
                case "4":
                    System.out.println("Sacar dinheiro da Conta");
                    System.out.print("Informe o n�mero da conta:    ");
                    conta = sc.nextInt();
                    System.out.print("Qual o valor a ser sacado?    ");
                    valor = sc.nextInt();
                    if (gerente.saque(conta, valor)) {
                        System.out.println("Saque efetuado com sucesso!");
                    } else {
                        System.out.println("N�o existe uma Conta com esse n�mero.");
                    }
                    break;
               case "5":
                    System.out.println("Tansferir dinheiro de uma Conta para outra");
                    System.out.print("Informe o n�mero da Conta que ser� debitada:  ");
                    conta1 = sc.nextInt();
                    System.out.print("Informe o n�mero da Conta que ser� creditada: ");
                    conta2 = sc.nextInt();
                    System.out.print("Qual o valor a ser transferido?   ");
                    valor = sc.nextInt();
                    if (gerente.transferir(conta1, conta2, valor)) {
                        System.out.println("transfer�ncia efetuada com sucesso!");
                    } else {
                        System.out.println("Não existe saldo suficiente em Conta para efeturar a transfer�ncia.");
                    }
                    break;  
                case "6":
                    System.out.println("Saldo da Conta");
                    System.out.print("Informe o n�mero da conta:    ");
                    conta = sc.nextInt();
                    String dados = gerente.saldo(conta);
                    System.out.println("");
                    System.out.println("============ Dados da Contas ==============");
                    System.out.print(dados);
                    System.out.print("===========================================\n");
                    break; 
                case "7":
                    System.out.println("Listar Contas Abertas");
                    String dados2 = gerente.listarContas();
                    System.out.println("========= Listando Contas Abertas =========");
                    System.out.print(dados2);
                    System.out.print("===========================================\n");
                    break; 
            }
        }
        sc.close();
        System.out.println("Sistema encerrado.");
    }
}
