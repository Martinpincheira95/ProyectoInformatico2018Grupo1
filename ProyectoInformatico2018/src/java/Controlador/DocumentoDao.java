/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;

import Modelo.*;
import java.util.List;

/**
 *
 * @author berko
 */
public class DocumentoDao {
    
    private static Connection conn;
    
    public DocumentoDao(){
        if (conn == null){
            conn = DatabaseConnect.getConn();
        }
        System.out.println("instanced docdao");
    }
    
    public void save(String correo, String tema, String titulo, String sv_path){
        try{
            String query = 
                "INSERT INTO documento(sv_path,correo,titulo) "
              + "VALUES ((?),(?),(?)) "
              + "RETURNING n_doc";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, sv_path);
            ps.setString(2, correo);
            ps.setString(3, titulo);
            ps.execute();            
            ResultSet rs = ps.getResultSet();
            int n_doc = 10000;
            if (rs.next()){
                n_doc = rs.getInt("n_doc");
            }
            query = 
                    "INSERT "
                  + "INTO documento_area(n_doc, tema) "
                  + "VALUES ((?),(?))";
            ps = conn.prepareStatement(query);
            ps.setInt(1, n_doc);
            ps.setString(2, tema);
            ps.execute();
            query = 
                    "INSERT "
                  + "INTO usuario_area(correo, tema) "
                  + "VALUES ((?),(?))";
            ps = conn.prepareStatement(query);
            ps.setString(1, correo);
            ps.setString(2, tema);
            ps.execute();
        } catch (SQLException ex){
            System.out.println(ex);
        }
    }
    
    public List search(String correo){
        String query = "SELECT d.titulo " 
                     + "FROM documento as d " 
                     + "WHERE d.correo = (?)";
        try{    
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, correo);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            List ls = new ArrayList<Documento>();
            while(rs.next()){                
                ls.add(rs.getString("titulo"));
            }
            return ls;
        } catch(SQLException ex){
            System.out.println(ex);
            return null;
        }
    }
    
    public Hashtable<String, ArrayList<String>> search(String keyword, String tema){
        try{
            String query = 
                "SELECT a.tema, d.titulo " +
                "FROM area_de_interes as a, documento_area as d_a, documento as d " +
                "WHERE a.tema = d_a.tema " +
                "AND d.n_doc = d_a.n_doc " +
                "AND d.title LIKE %(?)%" +
                "AND a.tema LIKE %(?)%";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, keyword);
            ps.setString(2, tema);
               
            ResultSet result = ps.executeQuery();
            Hashtable<String, ArrayList<String>> files = new Hashtable<String, ArrayList<String>>();

            while(result.next()){
                ArrayList<String> datos = new ArrayList<String>();
                datos.add(result.getString("titulo"));
                files.put(result.getString("tema"), datos);                    
            }
            return files;
        } catch(SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
    
}
