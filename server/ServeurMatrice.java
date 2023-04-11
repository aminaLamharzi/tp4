import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ServeurMatriceImpl extends UnicastRemoteObject implements ServeurMatriceInterface {
    private Map<String, String> clients;

    public ServeurMatriceImpl() throws RemoteException {
        super();
        clients = new HashMap<>();
    }

    public static void main(String[] args) {
        try {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            ServeurMatriceInterface serveurMatrice = new ServeurMatriceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ServeurMatrice", serveurMatrice);
            System.out.println("ServeurMatrice prêt.");
        } catch (Exception e) {
            System.err.println("Erreur serveur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean authentifier(String nom, String motDePasse) throws RemoteException {
        if (!clients.containsKey(nom)) {
            clients.put(nom, motDePasse);
            return true;
        } else {
            String mdp = clients.get(nom);
            if (mdp.equals(motDePasse)) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public Matrice addition(Matrice m1, Matrice m2) throws RemoteException {
        if (m1.getNbLignes() != m2.getNbLignes() || m1.getNbColonnes() != m2.getNbColonnes()) {
            throw new RemoteException("Les matrices doivent avoir la même taille");
        }
        Matrice result = new Matrice(m1.getNbLignes(), m1.getNbColonnes());
        for (int i = 0; i < m1.getNbLignes(); i++) {
            for (int j = 0; j < m1.getNbColonnes(); j++) {
                result.setElement(i, j, m1.getElement(i, j) + m2.getElement(i, j));
            }
        }
        return result;
    }

    @Override
    public Matrice multiplication(Matrice m1, Matrice m2) throws RemoteException {
        if (m1.getNbColonnes() != m2.getNbLignes()) {
            throw new RemoteException("Le nombre de colonnes de la première matrice doit être égal au nombre de lignes de la deuxième matrice");
        }
        Matrice result = new Matrice(m1.getNbLignes(), m2.getNbColonnes());
        for (int i = 0; i < m1.getNbLignes(); i++) {
            for (int j = 0; j < m2.getNbColonnes(); j++) {
                double sum = 0;
                for (int k = 0; k < m1.getNbColonnes(); k++) {
                    sum += m1.getElement(i, k) * m2.getElement(k, j);
                }
                result.setElement(i, j, sum);
            }
        }
        return result;
    }
}
