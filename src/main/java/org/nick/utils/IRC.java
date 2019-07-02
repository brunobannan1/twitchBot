package org.nick.utils;

import org.nick.app.Main;
import org.nick.model.Message;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class IRC {
    Properties properties = new Properties();
    private int port;
    private Socket socket;
    private String server;
    private String nickname;
    private String login;
    private String channel;

    public int getPort() {
        return port;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Socket connect() {
        try {
            this.port = Integer.valueOf(properties.getProperty("port"));
            this.server = properties.getProperty("server");
            this.socket = new Socket(server, port);
            this.nickname = properties.getProperty("nickname");
            this.channel = properties.getProperty("channel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socket;
    }

    public BufferedWriter getOutput() {
        try {
            return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedReader getInput() {
        try {
            return new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Message init() {
        try {
            properties.load(Main.class.getClassLoader().getResourceAsStream("secure.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connect();
        return new Message(socket, getInput(), getOutput());
    }
}
