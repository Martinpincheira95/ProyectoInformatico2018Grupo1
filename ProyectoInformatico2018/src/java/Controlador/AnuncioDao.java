/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Anuncio;
import Modelo.Usuario;
import Modelo.AreadeInteres;
import static java.lang.System.out;

import java.util.List;
import java.util.Optional;
import java.util.*;
import java.sql.*;

/**
 *
 * @author berko
 */
public class AnuncioDao {
    
    private static Connection conn = null;
    
    public AnuncioDao(){
         if (conn == null){
            conn = DatabaseConnect.getConn();
        }
        System.out.println("instanced anunciodao");
    }
    
    public void addTema(int n_anun, AreadeInteres area){
        try{
            String query = "INSERT INTO anuncio_area(n_anun, tema)"
                      + "VALUES ((?),(?))";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, n_anun);
            ps.setString(2, area.getTema());
            ps.execute();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }
    
    public void save(Anuncio a, Usuario u, AreadeInteres area){
        try{
            // crear anuncio en bd
            String query = "INSERT INTO anuncio "
                         + "(contenido, correo, fecha_anuncio)"
                         + "VALUES ((?),(?),(?))"
                         + "RETURNING n_anun";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, a.getNumero());
            ps.setString(2, a.getContent());
            ps.setString(3, u.getCorreo());
            ps.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            ps.execute();
            ResultSet rs = ps.getResultSet();
            rs.next();
            int n_anun = rs.getInt("n_anun");
            query = "INSERT INTO anuncio_area(n_anun, tema)"
                  + "VALUES ((?),(?))";
            ps = conn.prepareStatement(query);
            ps.setInt(1, n_anun);
            ps.setString(2, area.getTema());
            ps.execute();
            //

        } catch (SQLException ex){   
        }   
    }
    public TreeSet<Anuncio> getAnuncios(TreeSet<AreadeInteres> x){
        TreeSet<Anuncio> res = new TreeSet<Anuncio>();
        /*ArrayList<AreadeInteres> y = new ArrayList<AreadeInteres>();
        y.add(new AreadeInteres("Estructuras"));*/

        
        try{
            out.println("AAAAA");
            for(AreadeInteres i : /*y*/x){
                String query = "SELECT a.titulo, a.n_anun, a.contenido, us.nombre_usuario, a.fecha_anuncio FROM plataforma_colaborativa.anuncio as a, plataforma_colaborativa.anuncio_area as x, plataforma_colaborativa.usuario as us WHERE a.n_anun = x.n_anun AND x.tema = '"+i.getTema()+"' AND a.correo = us.correo";
                out.println(query);
                PreparedStatement ps = conn.prepareStatement(query);
                ps.execute();
                ResultSet rs = ps.getResultSet();
                while (rs.next()){
                    out.println(rs.getInt("n_anun"));
                    res.add(new Anuncio(rs.getInt("n_anun"),rs.getString("titulo"),rs.getString("nombre_usuario"),rs.getString("contenido"),rs.getString("fecha_anuncio"),i.getTema()));
                }
            }
            return res;
        } catch (SQLException ex){   
        }
        return null;
    }
}
