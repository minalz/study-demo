package cn.minalz.serialization.other;


public class ProtobufDemo {

    public static void main(String[] args) {
        UserProtos.User user = UserProtos.User.newBuilder().
                setAge(300).setName("Mic").build();

        byte[] bytes = user.toByteArray();
        System.out.println(bytes.length);

        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i]+" ");
        }
        // 10 3 77 105 99 16 -84 2

    }
}
