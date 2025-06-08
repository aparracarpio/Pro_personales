package gym.entrenos.account;

import gym.entrenos.usuarios.Autenticable;

public class ManageAccount {  // Nota: Nombre en PascalCase por convención
    public boolean login(Autenticable usuario, String pass) {
        if (usuario.checkPassword(pass)) {
            System.out.println("Contraseña correcta");
            return true;
        } else {
            System.out.println("Contraseña incorrecta");
            return false;
        }
    }
}