package top.inson.springboot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.inson.springboot.cluster.dao.IAnimalMapper;
import top.inson.springboot.cluster.entity.Animal;
import top.inson.springboot.dao.IUsersMapper;
import top.inson.springboot.entity.Users;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSpringbootApplication {
    @Autowired
    private DataSource dataSource;


    @Autowired
    private IUsersMapper usersMapper;
    @Autowired
    private IAnimalMapper animalMapper;

    private final Gson gson = new GsonBuilder().create();
    @Test
    public void testDataSource(){
        log.info("data:" + dataSource);
        Users users = usersMapper.selectByPrimaryKey(1);
        String json = gson.toJson(users);
        log.info("json:{}", json);

        Animal animal = animalMapper.selectByPrimaryKey(1);
        log.info("animal:{}", gson.toJson(animal));

    }


}
