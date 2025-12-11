package org.example;
import interfaces.IOrderManager;
import repositorio.OrderRepository;
import servicios.*;

public class Main {
    public static void main(String[] args) {
        try{
            IOrderManager orderManager = new OrderManager(new TaxCalculator(),new PDFGenetator(), new EmailNotificator(),new OrderRepository());
            //generar orden express
            System.out.println("--------Orden Express---------");
            orderManager.ejecutarOrden("Express");

            //generar orden standard
            System.out.println("\n--------Orden Standard---------");
            orderManager.ejecutarOrden("Standard");

            orderManager = new ReadonlyOrderManager(new TaxCalculator(),new PDFGenetator());
            //generar orden express readonly
            System.out.println("\n--------Orden Express ReadOnly---------");
            orderManager.ejecutarOrden("Express");

            //generar orden standard readonly
            System.out.println("\n--------Orden Standard ReadOnly---------");
            orderManager.ejecutarOrden("Standard");

        }catch (Exception e) {
            System.out.println("Error crítico: " + e.getMessage());
        }
    }
}
