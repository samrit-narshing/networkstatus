/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.List;

public class ApplicationInstanceManager {

    private static ApplicationInstanceListener subListener;
    /**
     * Randomly chosen, but static, high socket number
     */
    // public static final int SINGLE_INSTANCE_NETWORK_SOCKET = 12345;
    public static int SINGLE_INSTANCE_NETWORK_SOCKET = 12345;
    /**
     * Must end with newline
     */
    public static final String SINGLE_INSTANCE_SHARED_KEY = "$$NewInstance$$\n";

    /**
     * Registers this instance of the application.
     *
     * @return true if first instance, false if not.
     */
    public static boolean registerInstance(final int port, List<ServerSocket> serverSockets) {
        SINGLE_INSTANCE_NETWORK_SOCKET = port;
        // returnValueOnError should be true if lenient (allows app to run on network error) or false if strict.
        boolean returnValueOnError = true;
        // try to open network socket
        // if success, listen to socket for new instance message, return true
        // if unable to open, connect to existing and send new instance message, return false
        try {
            final ServerSocket socket = new ServerSocket(SINGLE_INSTANCE_NETWORK_SOCKET, 10, InetAddress.getByAddress(new byte[]{127, 0, 0, 1}));
            serverSockets.add(socket);
            System.out.println("Locking And Listening for application instances on socket " + SINGLE_INSTANCE_NETWORK_SOCKET);
            Thread instanceListenerThread = new Thread(new Runnable() {

                public void run() {
                    boolean socketClosed = false;
                    while (!socketClosed) {
                        if (socket.isClosed()) {
                            socketClosed = true;
                        } else {
                            try {
                                Socket client = socket.accept();
                                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                                String message = in.readLine();
                                if (SINGLE_INSTANCE_SHARED_KEY.trim().equals(message.trim())) {
                                    System.out.println("Shared key matched - new application instance found");
                                    //                                  fireNewInstance();
                                }
                                in.close();
                                client.close();
                            } catch (IOException e) {
                                socketClosed = true;
                            }
                        }
                    }
                }
            });
            instanceListenerThread.start();
            // listen
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
            return returnValueOnError;
        } catch (IOException e) {
            System.out.println("Port is already taken.  Notifying first instance.");
            try {
                Socket clientSocket = new Socket(InetAddress.getByAddress(new byte[]{127, 0, 0, 1}), SINGLE_INSTANCE_NETWORK_SOCKET);
                OutputStream out = clientSocket.getOutputStream();
                out.write(SINGLE_INSTANCE_SHARED_KEY.getBytes());
                out.close();
                clientSocket.close();
                System.out.println("Successfully notified first instance.");
                return false;
            } catch (UnknownHostException e1) {
                System.out.println(e.getMessage());
                return false;
            } catch (IOException e1) {
                System.out.println("Error connecting to local port for single instance notification");
                System.out.println(e1.getMessage());
                return false;
            }
        }
        return true;
    }

    public static void setApplicationInstanceListener(ApplicationInstanceListener listener) {
        subListener = listener;
    }

    static public void fireNewInstance() {
        if (subListener != null) {
            subListener.newInstanceCreated();
        }
    }
}
