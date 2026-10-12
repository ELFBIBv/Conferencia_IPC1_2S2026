// 3 · EL CLIENTE (en la analogía: "el jugador")
// No sabe que existen Sms, Email ni Telegram: solo usa Notificador.
public class Main {
    public static void main(String[] args) {
        // El canal llega como argumento: "EMAIL", "SMS" o "TELEGRAM".
        // En IntelliJ: Run > Edit Configurations... > Program arguments.
        // Si no se manda ninguno, se usa SMS.
        String canal = "EMAIL";

        Notificador notificador = elegir(canal);

        aprobarPago(notificador);
        enviarFactura(notificador);
    }

    // el único lugar que sabe qué canales existen
    static Notificador elegir(String canal) {
        return switch (canal) {
            case "EMAIL" -> new NotificadorEmail();
            case "SMS" -> new NotificadorSms();
            default -> throw new IllegalArgumentException("Canal no válido");
        };
    }

    // Estos métodos ya no tienen if/else: solo llaman a notificar().
    static void aprobarPago(Notificador notificador) {
        System.out.println("-------------Procesando pago...-------------");
        notificador.notificar("Tu pago fue aprobado");
    }

    static void enviarFactura(Notificador notificador) {
        System.out.println("-------------Generando factura...-------------");
        notificador.notificar("Tu factura está lista");
    }
}
