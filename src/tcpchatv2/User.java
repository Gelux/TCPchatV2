/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchatv2;

import java.io.Serializable;

/**
 *
 * @author Jes
 */
class User implements Serializable{
    String userName;
    int userId;

    public User(int userId) {
        this.userId = userId;
        userName = "User" + userId;
    }    

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    
}
