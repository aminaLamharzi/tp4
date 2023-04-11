import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientMatrice {

    public static void main(String[] args) {

        try {
            // Obtention du registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Récupération de l'objet distant
            ServeurMatriceInterface serveurMatrice = (ServeurMatriceInterface) registry.lookup("ServeurMatrice");

            // Authentification
            Scanner sc = new Scanner(System.in);
            System.out.println("Entrez votre nom d'utilisateur :");
            String username = sc.nextLine();
            System.out.println("Entrez votre mot de passe :");
            String password = sc.nextLine();

            if (serveurMatrice.authenticate(username, password)) {
                System.out.println("Authentification réussie !");
                // Utilisation des opérations matricielles
                System.out.println("Entrez la taille de la matrice :");
                int taille = sc.nextInt();
                double[][] matrice1 = new double[taille][taille];
                double[][] matrice2 = new double[taille][taille];

                System.out.println("Entrez les éléments de la première matrice :");
                for (int i = 0; i < taille; i++) {
                    for (int j = 0; j < taille; j++) {
                        matrice1[i][j] = sc.nextDouble();
                    }
                }

                System.out.println("Entrez les éléments de la deuxième matrice :");
                for (int i = 0; i < taille; i++) {
                    for (int j = 0; j < taille; j++) {
                        matrice2[i][j] = sc.nextDouble();
                    }
                }

                System.out.println("Addition des deux matrices :");
                double[][] resultatAddition = serveurMatrice.addition(matrice1, matrice2);
                for (int i = 0; i < taille; i++) {
                    for (int j = 0; j < taille; j++) {
                        System.out.print(resultatAddition[i][j] + " ");
                    }
                    System.out.println();
                }

                System.out.println("Multiplication des deux matrices :");
                double[][] resultatMultiplication = serveurMatrice.multiplication(matrice1, matrice2);
                for (int i = 0; i < taille; i++) {
                    for (int j = 0; j < taille; j++) {
                        System.out.print(resultatMultiplication[i][j] + " ");
                    }
                    System.out.println();
                }
            } else {
                System.out.println("Nom d'utilisateur ou mot de passe incorrect !");
            }

        } catch (Exception e) {
            System.err.println("Exception côté client : " + e.toString());
            e.printStackTrace();
        }
    }
}
