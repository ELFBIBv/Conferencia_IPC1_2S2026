// =====================================================================
// 1 · EL PRODUCTO (en la analogía: "el enemigo")
// Lo que todos los canales saben hacer: enviar().
// =====================================================================

public interface Notificacion {
    void enviar(String mensaje);
}

class Sms implements Notificacion {             // el lobo
    public void enviar(String mensaje) {
        System.out.println("[SMS] se obtiene su número de teléfono");
        System.out.println("[SMS] se envía el mensaje");
        System.out.println("[SMS] " + mensaje);
    }
}

class Email implements Notificacion {           // el escorpión
    public void enviar(String mensaje) {
        System.out.println("[EMAIL] se obtiene su correo electronico");
        System.out.println("[EMAIL] se levanta el servidor SMTP");
        System.out.println("[EMAIL] se envía el correo");
        System.out.println("[EMAIL] " + mensaje);
    }
}