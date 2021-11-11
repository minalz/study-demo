package cn.minalz.serialization.other;

import java.io.*;
import java.nio.file.Files;


public class JavaSerializerWithFile implements ISerializer{


    @Override
    public <T> byte[] serialize(T obj) {
        File userFile = new File("user");
        try {
            ObjectOutputStream outputStream=
                    new ObjectOutputStream(new FileOutputStream(userFile));

            outputStream.writeObject(obj);
            return Files.readAllBytes(userFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            ObjectInputStream objectInputStream=
                    new ObjectInputStream(new FileInputStream(new File("user")));
            return (T) objectInputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
