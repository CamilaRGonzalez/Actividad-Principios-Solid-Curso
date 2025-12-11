package repositorio;

import interfaces.Repositorio;

public class OrderRepository implements Repositorio {
    private final MySQLDatabase db;

    public OrderRepository(){
        db = new MySQLDatabase();
    }

    @Override
    public void saveToMySQL() {
        db.insert();
    }
}
