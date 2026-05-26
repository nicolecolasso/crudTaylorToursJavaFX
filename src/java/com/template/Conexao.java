package com.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
    Gerencia a conexão com o banco de dados PostgreSQL.
 */

public class Conexao {
    // Configurações de acesso: protocolo JDBC + host + porta + nome do banco
    static String conexao = "jdbc:postgresql://localhost:5432/TaylorTours";
    static String usuario = "postgres";
    static String senha = "postgres";

    public Connection conectaBD(){
        try{
            // Estabelece conexão com o banco
            return DriverManager.getConnection(conexao, usuario, senha);

        }catch (SQLException e){
            // Lança exceção em caso de erro na conexão
            throw new RuntimeException(e.getMessage());
        }
    }
}