/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchatv2;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Jes
 */
public class Mensaje implements Serializable{
    String contenido;
    ArrayList<User> users;
    int userId;
    
    Mensaje(String contenido, ArrayList<User> users, int userId){
        this.contenido = contenido;
        this.users = users;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    Mensaje(String contenido){
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
    
    
}
