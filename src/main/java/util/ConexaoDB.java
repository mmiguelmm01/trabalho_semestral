/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mmiguel
 */
public class ConexaoDB {
    private static final String URL = "jdbc:mariadb://127.0.0.1:3307/SGAHCP";
    private static final String USER = "clinica_user";
    private static final String PASS = "33xTMMpoo22"; //password just for this project, this is a school project. Dont waste ur time mate. ;)
    
    public static Connection obter() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
/* TIPOS DE ERROS
*“Connection refused” = o servidor ou a porta; 
*“Access denied” = a conta ou a senha; 
*“No suitable driver” = o driver no pom.xml; 
*“Unknown database” = o nome da base.
**/

