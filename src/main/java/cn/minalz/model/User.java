package cn.minalz.model;

import lombok.Data;

/**
 * 用户实体类
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
