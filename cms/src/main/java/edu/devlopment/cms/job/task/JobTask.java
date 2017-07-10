package edu.devlopment.cms.job.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import cn.edu.hfut.dmic.webcollector.crawler.lagou.JobCrawler;

/**
 * 职位定时器
 * 
 * @author dujianqiao
 * @version 1.0
 */
@Component
public class JobTask {

    /**
     * 定时任务
     */
    @Scheduled(cron = "0 0/2 * * * ? ")
    public void taskCycle() {
	System.out.println("定时任务================================");
	JobCrawler job = new JobCrawler();
	job.crawler();
    }

}
