import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServeurMatriceInterface extends Remote {
    String identifier(String nom, String motDePasse) throws RemoteException;
    boolean ajouterUtilisateur(String nom, String motDePasse) throws RemoteException;
    List<String> listerOperations() throws RemoteException;
    double[][] addition(double[][] a, double[][] b) throws RemoteException;
    double[][] soustraction(double[][] a, double[][] b) throws RemoteException;
    double[][] multiplication(double[][] a, double[][] b) throws RemoteException;
    boolean authenticate(String username, String password);
}
