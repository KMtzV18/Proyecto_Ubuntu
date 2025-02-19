/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kevmt
 */
public class Conexion {
    // Método para obtener la conexión a la base de datos
    public static Connection obtenerConexion() throws SQLException {
        String url = "jdbc:mysql://192.168.20.139:3306/proyecto?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String usuario = "kevin"; // Usuario de la base de datos
        String contraseña = "kevin123"; // Contraseña de la base de datos

        // Establece y devuelve la conexión
        return DriverManager.getConnection(url, usuario, contraseña);
    }
}
