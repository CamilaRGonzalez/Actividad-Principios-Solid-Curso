package servicios;

import interfaces.*;

public class OrderManager implements IOrderManager {
    private NotificadorEmail notificadorEmail;
    private Repositorio repositorio;
    protected CalculadorImpuestos calculadorImpuestos;
    protected GeneradorReporte generadorReporte;

    public OrderManager(CalculadorImpuestos calculadorImpuestos,
                        GeneradorReporte generadorReporte,
                        NotificadorEmail notificadorEmail,
                        Repositorio repositorio) throws Exception {
        this.notificadorEmail = notificadorEmail;
        this.repositorio = repositorio;
        this.calculadorImpuestos = calculadorImpuestos;
        this.generadorReporte = generadorReporte;
    }


    @Override
    public void ejecutarOrden(String tipo) throws Exception {
        new OrderProcessorFactory().getProcesador(tipo).processOrder();
        calculadorImpuestos.calculateTax();
        repositorio.saveToMySQL();
        notificadorEmail.sendEmailConfirmation();
        generadorReporte.generatePDFReport();
    }
}
