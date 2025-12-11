package servicios;

import interfaces.ProcesadorOrden;

public class OrderProcessorFactory {
    public ProcesadorOrden getProcesador(String type) throws Exception {
        if (type.equals("Standard")) {
            return new StandardOrderProcessor();
        } else if (type.equals("Express")) {
            return new ExpressOrderProcessor();
        } else {
            System.out.println("Tipo desconocido");
            throw new Exception("Tipo procesador desconocido");
        }
    }
}
