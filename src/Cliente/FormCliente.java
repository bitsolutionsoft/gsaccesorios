package Cliente;

import ClassAux.Formato;
import ClassAux.Util;
import Cliente.DAO.Cliente;
import Cliente.DAO.DataCliente;
import com.mysql.cj.xdevapi.Client;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FormCliente implements Initializable {


    public TextField txtCodigo;
    public TextField txtNombre;
    public TextField txtApellido;
    public TextField txtTelefonouno;
    public TextField txtTelefonodos;
    public TextField txtNit;


    public Label labelTitulo;
    public CheckBox chboxHombre;
    public CheckBox chboxMujer;
    public Button btnIngresarCliente;
    private String accion = "new"; // por default es new
    private String estado = "Activo";
    private  String sexo="Hombre";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        labelTitulo.setText("Ingresar Cliente");
        definirSexo();
        validarFormato();
    }
    public  void validarFormato(){
        Formato formato=new Formato();
        txtCodigo.setEditable(false);

        formato.entero(txtTelefonodos,8);
        formato.entero(txtTelefonouno,8);

    }

    public void pasarRegistro(Cliente cliente) {
        if (cliente != null) {
            accion = "update";
            labelTitulo.setText("Modificar datos del cliente");
            txtCodigo.setText(String.valueOf(cliente.getCodigo()));
            txtCodigo.setEditable(false); ///
            txtNombre.setText(cliente.getNombre());
            txtApellido.setText(cliente.getApellido());
            txtTelefonouno.setText(String.valueOf(cliente.getTelefonoUno()));
            txtTelefonodos.setText(String.valueOf(cliente.getTelefonoDos()));
            txtNit.setText(cliente.getNit());

            sexo=cliente.getSexo();
            definirSexo();


            btnIngresarCliente.setText("Actualizar Cliente"); //no has cambiado el id del boton

        }
    }


    public void RegistrarCliente(ActionEvent actionEvent) {
        switch (accion) {
            case "new":
                guardarCliente("new");
                break;
            case "update":
                guardarCliente("update");
                break;

        }
    }

    public void guardarCliente(String accion) {
        if (returnCliente() != null) {
            DataCliente dataCliente = new DataCliente();
            dataCliente.crudCliente(returnCliente(), accion);
            limpiar();
        }


    }




    public Cliente returnCliente() {
        Cliente cliente = new Cliente();
        if (txtCodigo.getText().isEmpty()) {
            cliente.setCodigo(0);
            cliente.setSexo(sexo);
        } else {
            cliente.setCodigo(Integer.parseInt(txtCodigo.getText()));
            cliente.setSexo(sexo);
        }

        if (!txtNit.getText().isEmpty()){ cliente.setNit(txtNit.getText()); }else{cliente.setNit("");}
        if (!txtTelefonodos.getText().isEmpty()){ cliente.setTelefonoDos(Integer.parseInt(txtTelefonodos.getText())); }else{cliente.setTelefonoDos(0);}
        ////campos obligatorios
        if (txtNombre.getText().isEmpty()) {
            Util.Error("Error", "El campo  nombre esta vacío");
            return null;
        } else {
            cliente.setNombre(txtNombre.getText());

            if (txtApellido.getText().isEmpty()) {
                Util.Error("Error", "El campo  apellido esta vacío");
                return null;
            } else {
                cliente.setApellido(txtApellido.getText());

                if(txtTelefonouno.getText().isEmpty()){
                    Util.Error("Error","El cambo telefono uno esta vacio");
                    return null;
                } else {
                    cliente.setTelefonoUno(Integer.parseInt(txtTelefonouno.getText()));
                    return  cliente;

                }

            }

        }


    }
    public  void limpiar() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefonouno.setText("");
        txtTelefonodos.setText("");
        txtNit.setText("");
        sexo="Hombre";
        definirSexo();
// VOS Y ESTADO COMO LE HAGO
    }

    public void SeleccionarHombre(ActionEvent actionEvent) {
        if (chboxHombre.isSelected()){
            chboxMujer.setSelected(false);
            sexo="Hombre";
        }
    }

    public void SeleccionarMujer(ActionEvent actionEvent) {
        if (chboxMujer.isSelected()){
            chboxHombre.setSelected(false);
            sexo="Mujer";
        }
    }
    public void definirSexo(){
        if (sexo.equals("Hombre")){
            chboxHombre.setSelected(true);
            chboxMujer.setSelected(false);
        }else {
            chboxHombre.setSelected(false);
            chboxMujer.setSelected(true);
        }

    }



}
