package servicios;

import interfaces.NotificadorEmail;

public class EmailNotificator implements NotificadorEmail {
    @Override
    public void sendEmailConfirmation() {
        System.out.println("Enviando correo...");
    }
}
