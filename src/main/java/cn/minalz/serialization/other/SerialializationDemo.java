package cn.minalz.serialization.other;

import cn.minalz.serialization.pojo.User;


public class SerialializationDemo {

    public static void main(String[] args) {
        ISerializer javaSerializer=new JavaSerializer();
        ISerializer fileSerializer=new JavaSerializerWithFile();
        ISerializer xmlSerializer=new XStreamSerializer();
        ISerializer fastJsonSeriliazer=new FastJsonSeriliazer();
        ISerializer hessianSerializer=new HessianSerializer();

        User user=new User();// JVM内存中.  如何把内存中的对象进行网络传输.(实体)->字节序列
        user.setAge(18);
        user.setName("Mic");

        //原生实现
        byte[] javaBytes = javaSerializer.serialize(user);
        byte[] fileBytes = fileSerializer.serialize(user);
        byte[] xmlBytes = xmlSerializer.serialize(user);
        byte[] fastJsonBytes = fastJsonSeriliazer.serialize(user);
        byte[] hessionBytes = hessianSerializer.serialize(user);

        System.out.println("javaBytes.length: " + javaBytes.length);
        System.out.println("fileBytes.length: " + fileBytes.length);
        System.out.println("xmlBytes.length: " + xmlBytes.length);
        System.out.println("fastJsonBytes.length: " + fastJsonBytes.length);
        System.out.println("hessionBytes.length: " + hessionBytes.length);
        /**
         * javaBytes.length: 104
         * fileBytes.length: 104
         * xmlBytes.length: 269
         * fastJsonBytes.length: 23
         * hessionBytes.length: 62
         */

        System.out.println("-------------------------");

        System.out.println("javaBytes -> " + new String(javaBytes));
        System.out.println("fileBytes -> " + new String(fileBytes));
        System.out.println("xmlBytes -> " + new String(xmlBytes));
        System.out.println("fastJsonBytes -> " + new String(fastJsonBytes));
        System.out.println("hessionBytes -> " + new String(hessionBytes));


        User javaUser = javaSerializer.deserialize(javaBytes,User.class);
        User fileUser = fileSerializer.deserialize(fileBytes,User.class);
        User xmlUser = xmlSerializer.deserialize(xmlBytes,User.class);
        User fastJsonUser = fastJsonSeriliazer.deserialize(fastJsonBytes,User.class);
        User hessionUser = hessianSerializer.deserialize(hessionBytes,User.class);

        System.out.println("javaUser => " + javaUser);
        System.out.println("fileUser => " + fileUser);
        System.out.println("xmlUser => " + xmlUser);
        System.out.println("fastJsonUser => " + fastJsonUser);
        System.out.println("hessionUser => " + hessionUser);



    }
}
