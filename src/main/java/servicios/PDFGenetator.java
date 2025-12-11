package servicios;

import interfaces.GeneradorReporte;

public class PDFGenetator implements GeneradorReporte {
    @Override
    public void generatePDFReport() {
        System.out.println("Generando PDF...");
    }
}
