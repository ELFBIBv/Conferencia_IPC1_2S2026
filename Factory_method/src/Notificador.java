// =====================================================================
// 2 · EL CREADOR (en la analogía: "los niveles")
// Notificador tiene las reglas; cada subclase decide qué producto crear.
// =====================================================================

public abstract class Notificador {
    // el factory method: el "?"
    abstract Notificacion crearNotificacion();

    void notificar(String mensaje) {   // las reglas
        crearNotificacion().enviar(mensaje);
    }
}

class NotificadorSms extends Notificador {      // nivel bosque
    Notificacion crearNotificacion() {
        return new Sms();
    }
}

class NotificadorEmail extends Notificador {    // nivel desierto
    Notificacion crearNotificacion() {
        return new Email();
    }
}