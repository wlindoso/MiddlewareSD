package servidor;

import java.rmi.registry.LocateRegistry;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class ServerMain {
    
    
    public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException{
        //Cria o cara que disponibiliza objetos informando a porta
        Registry registry = LocateRegistry.createRegistry(123);
        //Disponibiliza o objeto remoto
        registry.bind("gestor", Gestor.getInstance());
        Naming.rebind("rmi://localhost:123/gestor", Gestor.getInstance());
        System.out.println("Servidor no ar!");
        System.out.println(registry);
        System.out.println(registry.getClass());
        
    }
    
}
