package ua.spring;

import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan("ua.spring")
@PropertySource("classpath:auditorium.properties")
@ImportResource("classpath:context.xml")
public class Config {

}
