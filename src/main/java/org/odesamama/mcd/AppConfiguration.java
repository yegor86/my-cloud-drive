package org.odesamama.mcd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by starnakin on 08.10.2015.
 */

@Configuration
public class AppConfiguration {

    @Value("${hdfs.hadoopuser}") String hadoopUserName;

    @PostConstruct
    public void init(){
        System.setProperty("HADOOP_USER_NAME", hadoopUserName);
    }
}
