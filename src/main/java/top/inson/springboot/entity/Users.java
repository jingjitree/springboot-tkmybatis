package top.inson.springboot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;


@Getter
@Setter
@Table(name = "users")
public class Users extends BaseEntity<Users> {

    private String username;
    private String password;
    private String account;
    private Integer sex;
    private String phone;
    private String address;
    private Integer userType;

}
