/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchatv2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author Jes
 */
public class Cliente implements Serializable{

    ObjectOutputStream flujoSalida;
    ObjectInputStream flujoEntrada;
    Socket socketCliente;

    Cliente(String host, int puerto) {
        try {
            socketCliente = new Socket(host, puerto);

            flujoSalida = new ObjectOutputStream(socketCliente.getOutputStream());
            flujoEntrada = new ObjectInputStream(socketCliente.getInputStream());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    Cliente (ObjectOutputStream flujoSalida, ObjectInputStream flujoEntrada, Socket socketCliente){
        this.flujoSalida = flujoSalida;
        this.flujoEntrada = flujoEntrada;
        this.socketCliente = socketCliente;
    }

    public ObjectOutputStream getFlujoSalida() {
        return flujoSalida;
    }

    public void setFlujoSalida(ObjectOutputStream flujoSalida) {
        this.flujoSalida = flujoSalida;
    }

    public ObjectInputStream getFlujoEntrada() {
        return flujoEntrada;
    }

    public void setFlujoEntrada(ObjectInputStream flujoEntrada) {
        this.flujoEntrada = flujoEntrada;
    }

    public Socket getSocketCliente() {
        return socketCliente;
    }

    public void setSocketCliente(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    public void enviar(String msg) {
        Mensaje aux = new Mensaje(msg);
        try {
            flujoSalida.writeObject(aux);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Mensaje recibir() throws ClassNotFoundException {
        Mensaje aux;
        try {
            aux = (Mensaje)flujoEntrada.readObject();
            return aux;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
