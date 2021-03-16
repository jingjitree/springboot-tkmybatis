package top.inson.springboot.cluster.entity;

import lombok.Getter;
import lombok.Setter;
import top.inson.springboot.entity.BaseEntity;

import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Table(name = "animal")
public class Animal extends BaseEntity<Animal> {

    private String animalName;
    private Integer sex;
    private Integer age;
    private Date birthday;
    private String address;

}
