/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchatv2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jes
 */
public class Server implements Runnable {

    ArrayList<Cliente> usuarios = new ArrayList<>();
    ArrayList<User> userNames = new ArrayList<>();
    ObjectOutputStream flujoSalida;
    ObjectInputStream flujoEntrada;
    ServerSocket serverSocket;
    Socket socketCliente;
    int userId = 0;

    Server(int puerto) {
        try {
            serverSocket = new ServerSocket(puerto);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Thread server = new Thread(this);
        server.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                socketCliente = serverSocket.accept();
                System.out.println("Usuario" + userId + " conectado...");

                flujoSalida = new ObjectOutputStream(socketCliente.getOutputStream());
                flujoEntrada = new ObjectInputStream(socketCliente.getInputStream());

                Cliente clienteAux = new Cliente(flujoSalida, flujoEntrada, socketCliente);
                userNames.add(new User(userId));
                usuarios.add(clienteAux);
                
//                updateUsers();

                HiloServidor hiloServer = new HiloServidor(usuarios, userNames, userId);
                hiloServer.start();

                userId++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Server(5000);
    }

    public class HiloServidor extends Thread {

        ObjectOutputStream flujoSalida;
        ObjectInputStream flujoEntrada;
        Mensaje mensajeRecibido;
        String textoUsuarios;
        String userName;
        int userId;
        ArrayList<User> userNames;
        ArrayList<Cliente> users;

        HiloServidor(ArrayList<Cliente> users, ArrayList<User> userNames, int userId) {
            this.users = users;
            this.userId = userId;
            this.userNames = userNames;
        }

        public void run() {
            flujoEntrada = users.get(userId).getFlujoEntrada();

            while (true) {
                try {

                    mensajeRecibido = (Mensaje) flujoEntrada.readObject();
                    for (int i = 0; i < users.size(); i++) {
                        if (i != userId) {
                            flujoSalida = users.get(i).getFlujoSalida();
                            Mensaje aux = new Mensaje(mensajeRecibido.getContenido(), userNames, userId);
                            flujoSalida.writeObject(aux);
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException c) {
                    c.printStackTrace();
                }
            }
        }
    }
    
    public void updateUsers(){
        Mensaje aux;
        ObjectOutputStream out;
        for (int i = 0; i < usuarios.size(); i++) {
            try {
                out = new ObjectOutputStream(usuarios.get(i).getFlujoSalida());
                aux = new Mensaje("", userNames, 0);
                out.writeObject(aux);
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
