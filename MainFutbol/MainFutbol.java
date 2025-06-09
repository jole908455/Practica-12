import java.util.ArrayList;
import java.util.List;

public class MainFutbol {
    public static void main(String[] args) {
        System.out.println("=== DEMOSTRACIÓN DE PATRONES DE DISEÑO EN FÚTBOL ===");
        
        // 1. Singleton (Árbitro único)
        System.out.println("\n--- PATRÓN SINGLETON ---");
        Arbitro arbitro = Arbitro.getInstancia();
        arbitro.pitarFalta();
        
        Arbitro mismoArbitro = Arbitro.getInstancia();
        System.out.println("¿Es el mismo árbitro? " + (arbitro == mismoArbitro));
        
        // 2. Adapter (Delantero adaptado a defensa)
        System.out.println("\n--- PATRÓN ADAPTER ---");
        Delantero delantero = new JugadorDelantero();
        Defensa defensaAdaptado = new AdaptadorDelanteroADefensa(delantero);
        
        System.out.println("Acciones como delantero:");
        delantero.atacar();
        delantero.hacerGol();
        
        System.out.println("\nAcciones adaptadas como defensa:");
        defensaAdaptado.defender();
        defensaAdaptado.cortarAtaque();
        
        // 3. Observer (Notificación de gol)
        System.out.println("\n--- PATRÓN OBSERVER ---");
        Porteria porteria = new Porteria();
        porteria.agregarObservador(new HinchaLocal());
        porteria.agregarObservador(new HinchaVisitante());
        
        System.out.println("¡Sucede un gol!");
        porteria.marcarGol();
    }
}

// ========== SINGLETON ==========
class Arbitro {
    private static Arbitro instancia;
    private String nombre = "Árbitro Principal";
    
    private Arbitro() {}
    
    public static Arbitro getInstancia() {
        if (instancia == null) {
            instancia = new Arbitro();
        }
        return instancia;
    }
    
    public void pitarFalta() {
        System.out.println(nombre + ": ¡Pitido! Falta cometida");
    }
}

// ========== ADAPTER ==========
interface Delantero {
    void atacar();
    void hacerGol();
}

interface Defensa {
    void defender();
    void cortarAtaque();
}

class JugadorDelantero implements Delantero {
    @Override
    public void atacar() {
        System.out.println("Delantero: Corriendo hacia el área rival");
    }
    
    @Override
    public void hacerGol() {
        System.out.println("Delantero: ¡Gol! Celebra con emoción");
    }
}

class AdaptadorDelanteroADefensa implements Defensa {
    private Delantero delantero;
    
    public AdaptadorDelanteroADefensa(Delantero delantero) {
        this.delantero = delantero;
    }
    
    @Override
    public void defender() {
        System.out.print("Defensa adaptado: ");
        delantero.atacar(); // Usa su velocidad para recuperar
    }
    
    @Override
    public void cortarAtaque() {
        System.out.print("Defensa adaptado: ");
        delantero.hacerGol(); // Anticipación como si fuera a hacer gol
    }
}

// ========== OBSERVER ==========
class Porteria {
    private List<Observador> observadores = new ArrayList<>();
    
    public void agregarObservador(Observador obs) {
        observadores.add(obs);
    }
    
    public void marcarGol() {
        System.out.println("⚽ La pelota entra en la portería!");
        notificarObservadores();
    }
    
    private void notificarObservadores() {
        for (Observador obs : observadores) {
            obs.actualizar();
        }
    }
}

interface Observador {
    void actualizar();
}

class HinchaLocal implements Observador {
    @Override
    public void actualizar() {
        System.out.println("Hincha LOCAL: ¡Gooool! Celebra saltando");
    }
}

class HinchaVisitante implements Observador {
    @Override
    public void actualizar() {
        System.out.println("Hincha VISITANTE: Silbidos y protestas");
    }
}