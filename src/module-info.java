module gs.accesorios {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires mysql.connector.java;
    requires java.sql;


    opens Menu;
    opens Style;
    opens Producto;
    opens Producto.DAO;
    opens img;
    opens Cliente;
    opens Libreria;
    opens Conexion;
    opens Informe;
    opens Colocacion;
    opens Venta;
    opens Proveedor;

}