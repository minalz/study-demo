package cn.minalz.serialization.socket;

import cn.minalz.serialization.pojo.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 先启动serverProvider 再启动clientConsumer User未实现Serialiable接口的时候 会报序列化异常
 *
 */
public class SocketClientConsumer {

    public static void main(String[] args) {
        Socket socket = null;
        ObjectOutputStream out = null;
        try {
            socket = new Socket("localhost", 8080);
            User user = new User();
            user.setName("minalz");
            user.setAge(18);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
