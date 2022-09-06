package servidor;

import interfaces.MensageiroInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public final class Gestor extends UnicastRemoteObject implements MensageiroInterface {
    private static MensageiroInterface instance = null;
    private static final long serialVersionUID = 1L;
    private List<Conta> contas;
    public boolean status = false;
    public int qtdContas = 0;

    public Gestor() throws RemoteException {
        this.contas = new ArrayList<Conta>();
    }
    
    @Override
    public boolean criarConta(String nome, int conta) throws RemoteException {
        for (Conta c : contas) {
            if (c.getConta() == conta) {
                return false;
            }
        }
        Conta novaConta = new Conta(nome, conta);
        contas.add(novaConta);
        qtdContas++;
        return true;
    }
    
    @Override
    public boolean removerConta(int conta) throws RemoteException {
        for (Conta c : contas) {
            if (c.getConta() == conta) {
                contas.remove(c);
                qtdContas--;
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean saque(int conta, double valor) throws RemoteException {
        for (Conta c : contas) {
            if (c.getConta() == conta) {
                if (c.getSaldo() >= valor) {
                    c.setSaldo(c.getSaldo() - valor);
                    return true;
                } 
                System.out.println("Saldo em Conta, insuficiente.");
            }
        } 
        return false;
    }
    
    @Override
    public boolean transferir(int conta1, int conta2, double valor) throws RemoteException {
        for (Conta c : contas) {
            if (c.getConta() == conta1) {
                for (Conta c2 : contas) {
                    if (c2.getConta() == conta2) {
                        return c.transferir(valor, c2);
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean depositar(int conta, double valor) throws RemoteException {
        for (Conta c : contas) {
            if (c.getConta() == conta) {
                c.depositar(valor);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String saldo(int conta) throws RemoteException {
        for (Conta c : contas) {
            if (c.getConta() == conta) {
                String dados = "";
                dados += "Titular: " + c.getNome() + "\n" 
                      +  "Conta  : " + c.getConta() + "\n" 
                      +  "Saldo  : " + c.getSaldo() + "\n" ;
                return dados;
            }
        }
        return "Não foi encontrada nenhuma Conta com esse número. ";
    }
    
    @Override
    public String listarContas() throws RemoteException {
        String dados = ""; 
        for (Conta c : contas) {
            dados += "Titular: " + c.getNome() + "\n" 
                  +  "Conta  : " + c.getConta() + "\n" 
                  +  "Saldo  : " + c.getSaldo() + "\n"
                  +  "-------------------------------------------\n";
        }
        return dados;
    }
    
    public static MensageiroInterface getInstance() throws RemoteException {
        if (instance == null) {
            instance = new Gestor();
        }
        return instance;
    }

    
}
