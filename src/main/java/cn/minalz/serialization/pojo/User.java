package cn.minalz.serialization.pojo;

import lombok.Data;

import java.io.IOException;
import java.io.Serializable;

/**
 * 用户实体类
 */
@Data
public class User implements Serializable {


    private static final long serialVersionUID = -434539422310062943L;

    private String name;
    private int age;


    private void writeObject(java.io.ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(name);
    }

    private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        name=(String)s.readObject();
    }

}
