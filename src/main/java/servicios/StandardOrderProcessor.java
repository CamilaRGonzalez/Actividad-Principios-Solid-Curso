package servicios;

import interfaces.ProcesadorOrden;

public class StandardOrderProcessor implements ProcesadorOrden {
    @Override
    public void processOrder() {
        System.out.println("Procesando orden estándar...");
    }
}
