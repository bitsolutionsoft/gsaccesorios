package Producto;

import ClassAux.Util;
import Colocacion.DAO.Colocacion;
import Colocacion.DAO.DataColocacion;
import Producto.DAO.DataProducto;
import Producto.DAO.Producto;
import Proveedor.DAO.DataProveedor;
import Proveedor.DAO.Proveedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class FormProducto implements Initializable {
    public Button btnIngresarProducto;
    public Button btnNuevaColocacion;
    public Button btnNuevoProveedor;
    public CheckBox chbActivo;
    public CheckBox chbNoActivo;
    public ComboBox <Colocacion>cbColocacion;
    public ComboBox <Proveedor>cbProveedor;
    public TextField txtPrecioUnidad;
    public TextField txtPrecioMayor;
    public TextField txtPrecioMayorista;
    public TextField txtPrecioCompra;
    public TextField txtMinima;
    public TextField txtMaxima;
    public TextField txtCantidad;
    public TextField txtEspecificacion;
    public TextField txtModelo;
    public TextField txtNombre;
    public TextField txtCodigo;
    private   String accion="new";
    private String estado="Activo";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
estado("Activo");
        iniciar_combo_pro_col(0,0);
    }

    public void pasarRegistro(Producto producto){
        if (producto !=null){
            accion="update";
            txtCodigo.setText(String.valueOf(producto.getCodigo()));
            txtCodigo.setEditable(false);
            txtNombre.setText(producto.getNombre());
            txtModelo.setText(producto.getModelo());
            txtEspecificacion.setText(producto.getEspecificacion());
            txtCantidad.setText(String.valueOf(producto.getStock()));
            txtMaxima.setText(String.valueOf(producto.getMaximo()));
            txtMinima.setText(String.valueOf(producto.getMinimo()));
            txtPrecioCompra.setText(String.valueOf(producto.getPrecio_compra()));
            txtPrecioMayorista.setText(String.valueOf(producto.getPrecio_mayorista()));
            txtPrecioMayor.setText(String.valueOf(producto.getPrecio_mayor()));
            txtPrecioUnidad.setText(String.valueOf(producto.getPrecio_unidad()));
            iniciar_combo_pro_col(producto.getProveedor(),producto.getColocacion());

            estado(producto.getEstado());
            btnIngresarProducto.setText("Actualizar Producto");

        }
    }
    public  void  estado(String estado){
        if (estado.equals("Activo")){
          chbActivo.setSelected(true);
          chbNoActivo.setSelected(false);
        }else {
            chbNoActivo.setSelected(true);
            chbActivo.setSelected(false);
        }

    }
    public void RegistrarProducto(ActionEvent actionEvent) {
        switch (accion){
            case "new":
               guardarProducto("new");
                break;
            case "update":
                guardarProducto("update");
                break;

        }
    }
    public  void guardarProducto(String accion){
        if (returnProducto()!=null){
            DataProducto dataProducto=new DataProducto();
            dataProducto.crudProducto(returnProducto(),accion);
            limpiar();
        }



    }

    public void IngresarNuevaColocacion(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("Colocacion/FormColocacion.fxml"));
            Stage stage=new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnHiding((event ->{
                iniciar_combo_pro_col(0,0);
                cbColocacion.getSelectionModel().selectFirst();
            }));

        }catch (IOException e){
            e.printStackTrace();

        }
    }

    public void ingresarNuevoProveedor(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("Proveedor/FormProveedor.fxml"));
            Stage stage=new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnHiding((event ->{
                iniciar_combo_pro_col(0,0);
                cbProveedor.getSelectionModel().selectFirst();

            }));

        }catch (IOException e){
            e.printStackTrace();

        }
    }

    public void estadoActivo(ActionEvent actionEvent) {
        if (chbActivo.isSelected()){
           estado="Activo";
           chbNoActivo.setSelected(false);
        }
    }

    public void estadoNoActivo(ActionEvent actionEvent) {
        if (chbNoActivo.isSelected()){
            estado="No Activo";
            chbActivo.setSelected(false);
        }
    }

    public  void iniciar_combo_pro_col(int proveedor, int colocacion){
        DataProveedor dataProveedor=new DataProveedor();
        ArrayList<Proveedor> listProveedor= dataProveedor.viewProveedor("viewlast");
        ObservableList<Proveedor> obListProveedor= FXCollections.observableArrayList();

        obListProveedor.addAll(listProveedor);
        cbProveedor.setItems(obListProveedor);



        DataColocacion dataColocacion=new DataColocacion();
        ArrayList<Colocacion> listColocacion=dataColocacion.viewColocacion("viewlast");
        ObservableList<Colocacion> obListColocacion=FXCollections.observableArrayList();

        obListColocacion.addAll(listColocacion);
        cbColocacion.setItems(obListColocacion);

        if (proveedor>0){
            for (int i=0; i<obListProveedor.size();i++){
                if (proveedor==obListProveedor.get(i).getIdProveedor()){
                    Proveedor proveedor1=new Proveedor(obListProveedor.get(i).getIdProveedor(),obListProveedor.get(i).getNombre(),obListProveedor.get(i).getApellido(),obListProveedor.get(i).getTelefonoUno(),obListProveedor.get(i).getTelefonoDos(),obListProveedor.get(i).getCompania(),obListProveedor.get(i).getDireccion(),obListProveedor.get(i).getSexo(),obListProveedor.get(i).getEstado());
                    cbProveedor.getSelectionModel().select(proveedor1);

                }
            }

        }
        if (colocacion >0){
            for (int i=0; i<obListColocacion.size();i++){
                if (colocacion==obListColocacion.get(i).getIdColocacion()){
                    Colocacion colocacion1=new Colocacion(obListColocacion.get(i).getIdColocacion(), obListColocacion.get(i).getNombre(),obListColocacion.get(i).getEstado());
                    cbColocacion.getSelectionModel().select(colocacion1);
                }
            }

        }

    }

public  Producto returnProducto(){
        Producto producto=new Producto();
        if (txtCodigo.getText().isEmpty()){
            Util.Error("Error","El campo  codigo esta vacío");
            return null;
        }else{
            producto.setCodigo(Integer.parseInt(txtCodigo.getText()));
            if (txtNombre.getText().isEmpty()){
                Util.Error("Error","El campo  nombre esta vacío");
                return null;
            }else{
                producto.setNombre(txtNombre.getText());
                if (txtModelo.getText().isEmpty()){
                    Util.Error("Error","El campo  modelo esta vacío");
                    return null;
                }else{
                    producto.setModelo(txtModelo.getText());
                    if (txtEspecificacion.getText().isEmpty()){
                        Util.Error("Error","El campo  especificacion esta vacío");
                        return null;
                    }else{
                        producto.setEspecificacion(txtEspecificacion.getText());
                        if (txtCantidad.getText().isEmpty()){
                            Util.Error("Error","El campo  cantidad esta vacío");
                            return  null;
                        }else{
                            producto.setStock(Integer.parseInt( txtCantidad.getText()));
                            if (txtMaxima.getText().isEmpty()){
                                Util.Error("Error","El campo cantidad maxima esta vacío");
                                return null;
                            }else{
                                producto.setMaximo(Integer.parseInt(txtMaxima.getText()));
                                if (txtMinima.getText().isEmpty()){
                                    Util.Error("Error","El campo  cantidad minima  esta vacío");
                                    return null;
                                }else{
                                    producto.setMinimo(Integer.parseInt(txtMaxima.getText()));
                                    if (txtPrecioCompra.getText().isEmpty()){
                                        Util.Error("Error","El campo  precio compra esta vacío");
                                        return null;
                                    }else{
                                        producto.setPrecio_compra(Float.parseFloat(txtPrecioCompra.getText()));
                                        if (txtPrecioMayorista.getText().isEmpty()){
                                            Util.Error("Error","El campo  precio de venta mayorista esta vacío");
                                            return null;
                                        }else{
                                            producto.setPrecio_mayorista(Float.parseFloat(txtPrecioMayorista.getText()));
                                            if (txtPrecioMayor.getText().isEmpty()){
                                                Util.Error("Error","El campo  precio de venta por  mayor esta vacío");
                                                return null;
                                            }else{
                                                producto.setPrecio_mayor(Float.parseFloat(txtPrecioMayor.getText()));
                                                if (txtPrecioUnidad.getText().isEmpty()){
                                                    Util.Error("Error","El campo  precio de venta por unidad esta vacío");
                                                    return null;
                                                }else{
                                                    producto.setPrecio_unidad(Float.parseFloat(txtPrecioUnidad.getText()));
                                                    if (cbColocacion.getSelectionModel().isEmpty()){
                                                        Util.Error("Error","Seleccione la colocacion del producto");
                                                        return null;
                                                    }else{
                                                        producto.setColocacion(cbColocacion.getSelectionModel().getSelectedItem().getIdColocacion());
                                                        if (cbProveedor.getSelectionModel().isEmpty()){
                                                            Util.Error("Error","Seleccione el proveedor del producto");
                                                            return null;
                                                        }else {
                                                            producto.setProveedor(cbProveedor.getSelectionModel().getSelectedItem().getIdProveedor());
                                                            if (estado.isEmpty()){
                                                                Util.Error("Error","Seleccione el estado del producto");
                                                                return null;
                                                            }else {
                                                                producto.setEstado(estado);
                                                                return producto;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }

}

public  void limpiar(){
        txtCodigo.setText("");
        txtNombre.setText("");
        txtModelo.setText("");
        txtEspecificacion.setText("");
        txtCantidad.setText("");
        txtMaxima.setText("");
        txtMinima.setText("");
        txtPrecioCompra.setText("");
        txtPrecioMayorista.setText("");
        txtPrecioMayor.setText("");
        txtPrecioUnidad.setText("");
        cbProveedor.getSelectionModel().clearSelection();
        cbColocacion.getSelectionModel().clearSelection();
}

}
