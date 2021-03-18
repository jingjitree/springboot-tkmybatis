package top.inson.springboot.entity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.inson.springboot.cluster.entity.Animal;
import top.inson.springboot.entity.Users;

@Getter
@Setter
@Accessors(chain = true)
public class HelloDto implements java.io.Serializable{
    private Animal animal;

    private Users users;

}
