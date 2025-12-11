package servicios;

import interfaces.ProcesadorOrden;

public class ExpressOrderProcessor implements ProcesadorOrden {
    @Override
    public void processOrder() {
        System.out.println("Procesando orden express...");
    }
}
