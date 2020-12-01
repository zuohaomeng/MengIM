package com.meng.mengim.client.console;

import com.meng.mengim.client.service.IMClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Scanner;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @Author ZuoHao
 * @Date 2020/11/30
 * @Description
 */
@Service
public class MessageConsole implements ApplicationRunner {

    @Resource
    private IMClientService imClientService;
    @Value("${server.port}")
    private long memberId;
    ScheduledExecutorService threadPool = new ScheduledThreadPoolExecutor(1);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(memberId);
        imClientService.sendLoginMessage(memberId);
        Scanner sc = new Scanner(System.in);
        threadPool.execute(()->{
            while(true){
                while (sc.hasNext()){
                    long receivedId = sc.nextLong();
                    System.out.println("receivedId:"+receivedId);
                    String content = sc.next();
                    System.out.println("content:"+content);
                    imClientService.sendChatMessage(memberId,receivedId,content);
                }
            }
        });
    }
}
