package com.muc;

import java.io.*;

import java.net.Socket;
import java.util.Date;
import java.util.List;

public class ServerWorker extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private String login = null;
    private OutputStream outputStream;
    //Server server = new Server(8818);

    public ServerWorker(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void handleClientSocket() throws IOException, InterruptedException {
        this.outputStream = clientSocket.getOutputStream();
        InputStream inputStream = clientSocket.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(" ");
            if (tokens != null && tokens.length > 0) {


                String cmd = tokens[0];
                if ("logoff".equalsIgnoreCase(cmd) || "quit".equalsIgnoreCase(line)) {
                    handleLogoff();
                    break;
                } else if ("login".equalsIgnoreCase(cmd)) {
                    handleLogin(outputStream, tokens);
                } else {
                    String msg = "unknown" + cmd + "\n";
                    outputStream.write(msg.getBytes());
                }


            }

        }
        clientSocket.close();
    }

    private void handleLogoff() throws IOException {
        List<ServerWorker> workerList = server.getWorkerList();

        String onlineMsg = "offline " + login + "\n";
        for (ServerWorker worker : workerList) {
            if (!login.equals(worker.getLogin())) {
                worker.send(onlineMsg);
            }
        }
        clientSocket.close();
    }

    public String getLogin() {
        return login;
    }

    private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException {
        if (tokens.length == 3) {
            String login = tokens[1];
            String password = tokens[2];

            if ((login.equals("guest") && password.equals("guest")) || (login.equals("jim") && password.equals("jim"))) {
                String msg = "ok login\n";
                outputStream.write(msg.getBytes());
                this.login = login;
                System.out.println("User logged in successfully:" + login);



                List<ServerWorker> workerList = server.getWorkerList();

                for (ServerWorker worker : workerList) {
                    if (!login.equals(worker.getLogin())) {
                        if (!login.equals(null)) {
                            String msg2 = "online" + worker.getLogin() + "\n";
                            send(msg2);
                        }
                    }
                }

                String onlineMsg = "online " + login + "\n";
                for (ServerWorker worker : workerList) {
                    if (!login.equals(worker.getLogin())) {
                        worker.send(onlineMsg);
                    }
                }

            } else {
                String msg = "error login\n";
                outputStream.write(msg.getBytes());
            }

        }
    }


    private void send(String onlineMsg) throws IOException {
        if (!login.equals(null)) {
            outputStream.write(onlineMsg.getBytes());
        }


    }
}
