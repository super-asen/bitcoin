package com.example.demo.scheduler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class BitcoinScheduler {


   private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Scheduled(fixedRate =3000*1000*60)
    public void syndata(){
        logger.info("begin to sync bitcoin data");
    }




}
