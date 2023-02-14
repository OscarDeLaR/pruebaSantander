package prueba.santander;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.io.*;

public class PruebaSantander {

	    public static void main(String[] args) {
	    	
	    	String url = "jdbc:mysql://localhost:3306/prueba_santander";
	        String user = "root";
	        String password = "";
	        
	        int contadorClientes = 0;
	        
	        int codigoCliente = Integer.parseInt(args[0]);
	        
	        String rutaFichero = "C:\\Oscar\\";
	        
	        String nombreFichero = "proveedores.txt";
	        
	        String rutaFicheroSalida = rutaFichero + nombreFichero;

	        if (args.length < 1) {
	            System.out.println("Error: debes proporcionar el código de cliente como parámetro.");
	            return;
	        }

	        try (Connection con = DriverManager.getConnection(url, user, password)) {
	        	
	        	BufferedWriter writer = new BufferedWriter(new FileWriter(rutaFicheroSalida));
	        	
	            Statement stmt = con.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT id_proveedor, nombre, fecha_alta, id_cliente FROM proveedores");
	            
	            List<Proveedor> listProveedores = new ArrayList<>();

	            while (rs.next()) {
	            	
	            	Proveedor proveedor = new Proveedor();
	            	
	                proveedor.setId_proveedor(rs.getInt("id_proveedor"));
	                proveedor.setNombre(rs.getString("nombre"));
	                proveedor.setFecha_alta(rs.getDate("fecha_alta"));
	                proveedor.setId_cliente(rs.getInt("id_cliente"));
	                
	                listProveedores.add(proveedor);
	            }

	            // Escribimos las cabeceras del archivo
	            writer.write("ID_PROVEEDOR,NOMBRE,FECHA_ALTA,ID_CLIENTE");
	            writer.newLine();

	            // Escribimos los datos de cada proveedor en una nueva línea
	            for(Proveedor buscarProveedor : listProveedores) {
	            	if(buscarProveedor.getId_cliente()==Integer.parseInt(args[0])) {
	            		writer.write(buscarProveedor.getId_proveedor() + "," + buscarProveedor.getNombre() + "," + buscarProveedor.getFecha_alta() + "," + buscarProveedor.getId_cliente());
	            		writer.newLine();
	            		contadorClientes++;
	            	}
	            }
	            
	            if(contadorClientes==0) {
	            	writer.write("No se han encontrado proveedores para el id_cliente especificado");
	            	System.out.println("No se han encontrado proveedores para el id_cliente especificado");
	            };
	            
	            writer.close();
	            System.out.println("El archivo " + rutaFicheroSalida + " ha sido generado con éxito.");

	        } catch (SQLException | IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	}

