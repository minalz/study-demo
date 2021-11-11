package cn.minalz.serialization.socket;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体类
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1448325811797970490L;

    private String name;
}
