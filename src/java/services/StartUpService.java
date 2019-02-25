package services;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.Connection;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 *  -   Create connection met de database as soon as the app starts
 */

@Startup
@Singleton
public class StartUpService {

    @Resource
    private DataSource dataSource;

    @PostConstruct
    public void init(){
        System.out.println("services.StartUpService");
        System.out.println("Make connection with database.");
    }
}
