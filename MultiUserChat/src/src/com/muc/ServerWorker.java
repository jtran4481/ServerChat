package com.muc;

import java.net.Socket;

public class ServerWorker extends Thread{

    private final Socket clientSocket;
    public ServerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

    }

}
