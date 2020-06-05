package com.example.demo_activity.test1.config;

import com.example.demo_activity.test1.model.CityName;
import com.example.demo_activity.test1.service.ICityNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTask {
    @Autowired
    private ICityNameService cityNameService;

        private int count=0;

        @Scheduled(cron="*/20 * * * * ?")
        private void process(){
            //System.out.println("this is scheduler task runing  "+(count++));
            CityName cityNameById = cityNameService.getCityNameById("2");
            System.out.println(cityNameById+"----------------"+(count++));
        }

}
