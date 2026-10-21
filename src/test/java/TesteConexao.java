
import java.sql.Connection;
import java.sql.SQLException;
import util.ConexaoDB;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mmiguel
 */
public class TesteConexao {
    public static void main(String[] args) {
        try (Connection conn = ConexaoDB.obter()) {
            System.out.println("LIGADO TROPA!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ROCHASTE MYBOY");
        }
    }
}
/* TIPOS DE ERROS
*“Connection refused” = o servidor ou a porta; 
*“Access denied” = a conta ou a senha; 
*“No suitable driver” = o driver no pom.xml; 
*“Unknown database” = o nome da base.
**/
