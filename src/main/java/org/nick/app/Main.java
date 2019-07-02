package org.nick.app;

import org.nick.model.Message;
import org.nick.utils.IRC;

import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void kappaSend(Message msg, String channel) throws Exception {

        Object lock = new Object();
        String arr[] = {"(*)>", "(*)> (*)>", "(*)> (*)> (*)>"};
        Thread thread[] = new Thread[5];
        thread[0] = new Thread(() -> {
            synchronized (lock) {
                msg.sendMessage("PRIVMSG #" + channel + " :" + arr[0]);
                try {
                    lock.wait(1250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread[1] = new Thread(() -> {
            synchronized (lock) {
                msg.sendMessage("PRIVMSG #" + channel + " :" + arr[1]);
                try {
                    lock.wait(1250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread[2] = new Thread(() -> {
            synchronized (lock) {
                msg.sendMessage("PRIVMSG #" + channel + " :" + arr[2]);
                try {
                    lock.wait(1250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread[3] = new Thread(() -> {
            synchronized (lock) {
                msg.sendMessage("PRIVMSG #" + channel + " :" + arr[1]);
                try {
                    lock.wait(1250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread[4] = new Thread(() -> {
            synchronized (lock) {
                msg.sendMessage("PRIVMSG #" + channel + " :" + arr[0]);
                try {
                    lock.wait(1250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        for (int i = 0; i < 5; i++) {
            thread[i].start();
            thread[i].join();
        }
    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getClassLoader().getResourceAsStream("secure.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        IRC irc = new IRC();
        Message msg = irc.init();
        String line = null;
        String oauth = properties.getProperty("oauth");
        String enterPASS = "PASS " + oauth;
        String setNickname = "NICK " + irc.getNickname();
        msg.sendMessage(enterPASS);
        msg.sendMessage(setNickname);
        msg.sendMessage("JOIN #" + irc.getChannel());
        try {
            kappaSend(msg, irc.getChannel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        while ((line = msg.receiveMessage()) != null) {
            if (line.toLowerCase().startsWith("PING :tmi.twitch.tv")) {
                msg.sendMessage("PONG :tmi.twitch.tv");
            } else {
                System.out.println(line);
            }
        }
    }
}
