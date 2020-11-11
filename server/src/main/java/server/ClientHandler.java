package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.*;

public class ClientHandler {
    DataInputStream in;
    DataOutputStream out;
    Server server;
    Socket socket;

    private String nickname;
    private String login;
    private static final Logger logger = Logger.getLogger (ClientHandler.class.getName());

    public ClientHandler(Server server, Socket socket) {
        try {
//            Handler fileHandler = new FileHandler ("log.txt", true);
//            fileHandler.setFormatter (new SimpleFormatter ());
//            fileHandler.setLevel (Level.INFO);
//            logger.addHandler (fileHandler);
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
//            System.out.println("Client connected " + socket.getRemoteSocketAddress());
            logger.log (Level.INFO, "Client connected " + socket.getRemoteSocketAddress());

            server.getExecutorService ().execute (() -> {
                try {
                    socket.setSoTimeout(120000);

                    //цикл аутентификации
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/reg ")) {
                            String[] token = str.split("\\s");
                            if (token.length < 4) {
                                continue;
                            }
                            boolean b = server.getAuthService()
                                    .registration(token[1], token[2], token[3]);
                            if (b) {
                                sendMsg("/regok");
                                logger.log (Level.INFO, "Регистрация нового пользователя прошла успешно");
                            } else {
                                sendMsg("/regno");
                                logger.log (Level.INFO, "Ошибка регистрации");
                            }
                        }

                        if (str.startsWith("/auth ")) {
                            String[] token = str.split("\\s");
                            if (token.length < 3) {
                                continue;
                            }
                            String newNick = server.getAuthService()
                                    .getNicknameByLoginAndPassword(token[1], token[2]);
                            if (newNick != null) {
                                login = token[1];
                                if (!server.isLoginAuthenticated(login)) {
                                    nickname = newNick;
                                    sendMsg("/authok " + newNick);
                                    logger.log (Level.INFO, "Вход " + login + " (" + newNick + ") выполнен успешно");
                                    server.subscribe(this);
                                    socket.setSoTimeout(0);
                                    sendMsg(SQLHandler.getMessageForNick(nickname));
                                    break;
                                } else {
                                    sendMsg("С этим логином уже вошли в чат");
                                    logger.log (Level.INFO, "Ошибка входа в чат. Логин " + login + " уже используется");
                                }
                            } else {
                                sendMsg("Неверный логин / пароль");
                                logger.log (Level.INFO, "Введен неверный пароль/логин");
                            }
                        }
                    }

                    //цикл работы
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/")) {
                            if (str.equals("/end")) {
                                sendMsg("/end");
                                logger.log (Level.INFO, "Выход пользователя " + login);
                                break;
                            }
                            if (str.startsWith("/w ")) {
                                String[] token = str.split("\\s", 3);
                                if (token.length < 3) {
                                    continue;
                                }
                                server.privateMsg(this, token[1], token[2]);
                                logger.log (Level.INFO, "Пользователь " + login + " отправил приватное сообщение " + token[1]);
                            }
                            if (str.startsWith("/chnick ")) {
                                String[] token = str.split(" ", 2);
                                if (token.length < 2) {
                                    continue;
                                }
                                if (token[1].contains(" ")) {
                                    sendMsg("Ник не может содержать пробелов");
                                    continue;
                                }
                                if (server.getAuthService().changeNick(this.nickname, token[1])) {
                                    sendMsg("/yournickis " + token[1]);
                                    sendMsg("Ваш ник изменен на " + token[1]);
                                    logger.log (Level.INFO, "Смена ника " + nickname + " на " + token[1]);
                                    this.nickname = token[1];
                                    server.broadcastClientList();
                                } else {
                                    sendMsg("Не удалось изменить ник. Ник " + token[1] + " уже существует");
                                    logger.log (Level.INFO, "Ошибка изменения ника. Ник " + token[1] + " уже существует");
                                }
                            }
                        } else {
                            server.broadcastMsg(this, str);
                            logger.log (Level.INFO, "Пользователь " + login + " отправил сообщение в общий чат");
                        }
                    }

                } catch (SocketTimeoutException e) {
                    sendMsg("/end");
//                    System.out.println("Client disconnected by timeout");
                    logger.log(Level.INFO, "Client disconnected by timeout");
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.log (Level.INFO, "IOException");
                } finally {
                    server.unsubscribe(this);
//                    System.out.println("Client disconnected " + socket.getRemoteSocketAddress());
                    logger.log(Level.INFO, "Client disconnected " + socket.getRemoteSocketAddress());
                    try {
                        socket.close();
                        in.close();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNickname() {
        return nickname;
    }

    public String getLogin() {
        return login;
    }
}
