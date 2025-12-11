package servicios;

import interfaces.CalculadorImpuestos;

public class TaxCalculator implements CalculadorImpuestos {
    @Override
    public void calculateTax() {
        System.out.println("Calculando impuestos complejos...");
    }
}
