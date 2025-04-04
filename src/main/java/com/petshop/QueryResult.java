package com.petshop;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryResult {
    private ResultSet resultSet;

    public QueryResult(ResultSet rs)
    {
        this.resultSet = rs;
    }

    //Função para printar o resultado da query de busca
    public void printQuery(String label) throws SQLException
    {
        while(resultSet.next())
        {
            System.out.println(resultSet.getString(label));
        }
    }
}
