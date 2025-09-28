/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esofii.alunoscurso.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author JoaoDias
 */
public class Conexao {
    private final String driver = "org.postgresql.Driver";
    private final String url = "jdbc:postgresql://localhost:5432/bd_escola";
    private final String usuario = "postgres";
    private final String senha = "admin123";
    
    public Connection conectar(){
        Connection conn = null;
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, usuario, senha);
        }catch(SQLException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return conn;
    }
}
