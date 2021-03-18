package top.inson.springboot.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.inson.springboot.cluster.dao.IAnimalMapper;
import top.inson.springboot.cluster.entity.Animal;
import top.inson.springboot.dao.IUsersMapper;
import top.inson.springboot.entity.Users;
import top.inson.springboot.entity.dto.HelloDto;
import top.inson.springboot.service.IHelloService;

@Slf4j
@Service
public class HelloServiceImpl implements IHelloService {
    @Autowired
    private IUsersMapper usersMapper;
    @Autowired
    private IAnimalMapper animalMapper;


    private final Gson gson = new GsonBuilder().create();

    @Override
    public HelloDto sayHello() {
        Users users = usersMapper.selectByPrimaryKey(1);
        Animal animal = animalMapper.selectByPrimaryKey(1);
        JsonObject resObj = new JsonObject();
        resObj.add("user", gson.toJsonTree(users));
        resObj.add("animal", gson.toJsonTree(animal));
        log.info("resObj:{}", resObj);

        HelloDto dto = new HelloDto()
                .setAnimal(animal)
                .setUsers(users);

        return dto;
    }

}
