## Código base

import java.util.Objects;

// --- Interfaces ---
interface ISuperOrderProcessor {
void processOrder(String type);
void calculateTax();
void saveToMySQL();
void sendEmailConfirmation();
void generatePDFReport();
}

// --- Clases Concretas (Simulando persistencia) ---
class MySQLDatabase {
public void insert() {
System.out.println("Insertando en base de datos MySQL...");
}
}

// --- Clase Principal con violaciones SRP, OCP, DIP ---
class OrderManager implements ISuperOrderProcessor {

    // Violación DIP: Dependencia concreta fuerte
    private MySQLDatabase database = new MySQLDatabase();

    @Override
    public void processOrder(String type) {
        // Violación OCP: Modificar esto para cada nuevo tipo
        if (type.equals("Standard")) {
            System.out.println("Procesando orden estándar...");
        } else if (type.equals("Express")) {
            System.out.println("Procesando orden express...");
        } else {
            System.out.println("Tipo desconocido");
        }
        
        // Violación SRP: El gestor orquesta demasiadas cosas distintas
        calculateTax();
        saveToMySQL();
        sendEmailConfirmation();
        generatePDFReport();
    }

    @Override
    public void calculateTax() {
        System.out.println("Calculando impuestos complejos...");
    }

    @Override
    public void saveToMySQL() {
        database.insert();
    }

    @Override
    public void sendEmailConfirmation() {
        System.out.println("Enviando correo...");
    }

    @Override
    public void generatePDFReport() {
        System.out.println("Generando PDF...");
    }
}

// --- Subclase que rompe el comportamiento ---
class ReadOnlyOrderManager extends OrderManager {

    // Violación LSP: Cambia el comportamiento base lanzando una excepción
    @Override
    public void saveToMySQL() {
        throw new UnsupportedOperationException("No puedo guardar, soy de solo lectura.");
    }

    // Violación ISP: Obligado a implementar métodos que no usa o deja vacíos
    @Override
    public void sendEmailConfirmation() {
        // No hace nada
    }
}

public class OrderSystem {
public static void main(String[] args) {
System.out.println("--- Orden Normal ---");
OrderManager order = new OrderManager();
order.processOrder("Standard");

        System.out.println("\n--- Intentando usar la subclase (Violación LSP) ---");
        OrderManager badOrder = new ReadOnlyOrderManager();
        try {
            // El polimorfismo debería permitirnos tratar a badOrder igual que a order, pero falla.
            badOrder.saveToMySQL();
        } catch (Exception e) {
            System.out.println("Error crítico: " + e.getMessage());
        }
    }
}

# Violaciones encontradas en código base
1. Single Responsibility Principle:
\nClase principal OrderManager hace todo: procesa la orden, calcula impuestos, genera pdf, envía mails y persiste en sql.
No tiene una sola razón para cambiar, si se cambia el modo de notificación, el cálculo de impuestos o cualquiera de los procesos que realiza, se debe modificar la clase.

2. Open/Closed Principle:
Para procesar un nuevo tipo de orden se debe modificar el código de OrderManager

3. Liskov Substitution Principle:
OrderManager no es intercambiable por ReadOnlyOrderManager, ya que, la subclase debe sobreescribir métodos que no soporta

4. Interface Segregation Principle:
La interfaz ISuperOrderProcessor es demasiado grande y obliga a las clases que la usen a implementar todos sus métodos, sean estos soportados o no por la clase.
Además, los métodos no tienen cohesión, cada uno corresponde a una funcionalidad distinta.

5. Dependency Inversion Principle:
La clase Order manager depende de implementaciones concretas (MySQLConnection, EmailService, etc.). 
No se puede sustituir por otras implementaciones y el sistema queda acoplado a esas clases para realizar sus funciones.
Si en un futuro cambia la base de datos o la forma de notificación se debe reescribir todo el código


# Principios aplicados y mejoras en el código
1. Single Responsibility Principle:
Se separan todas las funcionalidades en distintas clases que hagan 1 sola cosa:
TaxCalculator → calcula impuestos
PDFGenerator → genera PDFs
EmailNotificator → envía mails
OrderRepository → guarda en base de datos
ExpressOrderProcessor y StandardOrderProcessor → procesan la orden
OrderProcessorFactory → decide que tipo de procesador instanciar
OrderManager → coordina la operación
ReadonlyOrderManager → coordina sin persistir datos o enviar notificaciones

2. Open/Closed Principle:
Se puede agregar nuevo tipo de orden agregando una nueva clase que implemente la interfaz ProcesadorOrden 
y modificando solo OrderProcessorFactory, que va a ser la única clase encargada de decidir que tipo de procesador instanciar.

3. Liskov Substitution Principle:
ReadonlyOrderManager ahora sustituye correctamente a OrderManager ya que ambas implementan la misma interfaz. Las dos implementaciones pueden reemplazarse sin romper nada.

4. Interface Segregation Principle:
Se separaron los métodos de la interfaz original en varias interfaces específicas de cada funcionalidad. Ahora los clientes implementan solo lo que necesitan.

5. Dependency Inversion Principle:
OrderManager y ReadOnlyOrderManager ahora inyectan sus dependencias por constructor a partir de abstracciones (interfaces). Esto permite que no estén acoplados
a implementaciones concretas y que se puedan intercambiar si algo cambia en el futuro (ej: cambia MySQL por Dynamo, PostgreSQL, etc.)
