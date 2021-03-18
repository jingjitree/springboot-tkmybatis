package top.inson.springboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.inson.springboot.entity.dto.HelloDto;
import top.inson.springboot.service.IHelloService;

@RestController
@RequestMapping(value = "/hello")
public class HelloController {
    @Autowired
    private IHelloService helloService;


    @GetMapping("/sayHello")
    public HelloDto sayHello(){
        return helloService.sayHello();
    }

}
