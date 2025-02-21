/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDS;

import Vistas.Vista;
import Vistas.Vista2;
import java.awt.List;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author kevmt
 */
public class CRM {
    Conexion conexion = new Conexion();
    
    public void Eliminar(JTable tabla) throws SQLException{
        Connection conn = (Connection) conexion.obtenerConexion();
        PreparedStatement pstmt = null;
        
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        
        Vista v = new Vista();
        int reng = v.getNumRenglon();
        
        int id = (int) modelo.getValueAt(reng, 0);
        
        String sql = "Delete from Inventario where id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        
        pstmt.executeUpdate();
        
        
        pstmt.close();
        conn.close();
        
       
    }
    
    
    public void Modificar(JTable tabla) throws ClassNotFoundException, SQLException, ParseException{
        
        Connection conn = (Connection) conexion.obtenerConexion();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        Vista2 v2 = new Vista2();
        Vista v = new Vista();
        int reng = v.getNumRenglon();
        
        int id = (int) modelo.getValueAt(reng, 0);
        
        String sql = "select id, nombrecorto,descripcion,serie,color,FechaAdquision,TipoAdquision,observaciones,areas_id from Inventario where id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        
        rs = pstmt.executeQuery();
        
        if (rs.next()) {
            v2.llenar(id, rs.getString("nombrecorto"), rs.getString("descripcion"),rs.getString("serie"), rs.getString("color"), rs.getString("FechaAdquision"), rs.getString("TipoAdquision"), rs.getInt("areas_id"), rs.getString("observaciones"));
            v2.setVisible(true);
            
         }
        
        rs.close();
        pstmt.close();
        conn.close();
        
    }
    
    public boolean Insertar(int mod,int id,String nombre,String descripcion,String serie,String color,String fecha_ad,String tipo_ad,int area,String observaciones) throws SQLException, ClassNotFoundException{
        Connection conn = (Connection) conexion.obtenerConexion();
        PreparedStatement pstmt = null;
        
        if (mod == 0){
        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "insert into Inventario values(?,?,?,?,?,?,?,?,?)";
            //id,nombre,descripcion,serie,color,fecha_ad,tipo_ad,area,observaciones
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, nombre);
            pstmt.setString(3, descripcion);
            pstmt.setString(4, serie);
            pstmt.setString(5, color);
            pstmt.setString(6, fecha_ad);
            pstmt.setString(7, tipo_ad);
            pstmt.setString(8, observaciones);
            pstmt.setInt(9, area);
            

            int afectado = pstmt.executeUpdate();
            if (afectado>0) {
                System.out.println("simon");
                return true;
            }else{
                System.out.println("no mi pa");
                return false;
            }
        
        }
        catch(SQLException e){
            System.out.println(e);
            System.out.println("no pa");
            return false;
        }
        
        }else{//--------------------------------------------------------------------------------------
            
            if (mod ==1) {
            try{
            
            
            String sql = "UPDATE Inventario SET nombrecorto = ?, descripcion = ?, serie = ?, color = ?, FechaAdquision = ?, TipoAdquision = ?, observaciones = ?, AREAS_id = ?  WHERE id = ?";
            //id,nombre,descripcion,serie,color,fecha_ad,tipo_ad,area,observaciones
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setString(2, descripcion);
            pstmt.setString(3, serie);
            pstmt.setString(4, color);
            pstmt.setString(5, fecha_ad);
            pstmt.setString(6, tipo_ad);
            pstmt.setString(7, observaciones);
            pstmt.setInt(8, area);
            pstmt.setInt(9, id);
           // pstmt.setInt(9, reng);
            
            //System.out.println(reng);
            int afectado = pstmt.executeUpdate();
            if (afectado>0) {
                System.out.println("simon (update)");
                return true;
            }else{
                System.out.println("no mi pa (update)");
                return false;
            }

            }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("no pa (update)");
            return false;
        }
        }else{return false;}
         
        
    }
}
    
    
    
    
    
    public void CargarTabla(JTable tabla) throws SQLException{
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        Connection conn = (Connection) conexion.obtenerConexion();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        modelo.setRowCount(0);
        String[] columnas = {"ID", "NOMBRECORTO", "DESCRIPCION","SERIE","COLOR"};
        try{
            String sql = "select id,NombreCorto,Descripcion,Serie,Color from Inventario";
            pstmt = conn.prepareStatement(sql);
            
        
            // Ejecutar la consulta
            rs = pstmt.executeQuery();
        
            while (rs.next()) {
                Object[] fila = new Object[columnas.length];  

                for (int j = 0; j < columnas.length; j++) {
                    fila[j] = rs.getObject(columnas[j]); 
            }
            
            modelo.addRow(fila);  
        }
            System.out.println("si cargue");
        }catch(SQLException e){
            System.out.println(e);
            System.out.println("no cargue");
        }
        rs.close();
        pstmt.close();
        conn.close();
        
    }
    
    public void cargarCB(JComboBox<String> cb) throws SQLException {
    ArrayList<String> datos = new ArrayList<String>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = conexion.obtenerConexion();
        String sql = "SELECT Nombre, ubicacion FROM Areas";
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            String nombre = rs.getString("Nombre") + " , " + rs.getString("Ubicacion"); 
            datos.add(nombre);
        }

        // Limpiar el JComboBox antes de agregar los nuevos datos
        cb.removeAllItems();
        
        // Agregar los datos al JComboBox
        for (String item : datos) {
            cb.addItem(item);
        }

    } catch (SQLException e) {
        e.printStackTrace(); // Muestra el error en consola
    } finally {
        // Cerrar recursos para evitar fugas de memoria
        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
}
    
    
    
}
