package servicios;

import interfaces.CalculadorImpuestos;
import interfaces.GeneradorReporte;
import interfaces.IOrderManager;
import interfaces.ProcesadorOrden;

public class ReadonlyOrderManager implements IOrderManager {
    protected CalculadorImpuestos calculadorImpuestos;
    protected GeneradorReporte generadorReporte;

    public ReadonlyOrderManager(CalculadorImpuestos calculadorImpuestos, GeneradorReporte generadorReporte) throws Exception {
        this.calculadorImpuestos = calculadorImpuestos;
        this.generadorReporte = generadorReporte;
    }

    public void ejecutarOrden(String tipo) throws Exception {
        new OrderProcessorFactory().getProcesador(tipo).processOrder();
        calculadorImpuestos.calculateTax();
        generadorReporte.generatePDFReport();
    }

}
