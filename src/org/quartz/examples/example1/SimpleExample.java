//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.quartz.examples.example1;

import com.goldpac.instantissue.launcher.JdbcFactory;
import com.goldpac.instantissue.launcher.ShutdownThreads;
import com.goldpac.instantissue.launcher.StartThreads;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleExample {
    Logger log = LoggerFactory.getLogger(SimpleExample.class);

    public SimpleExample() {
    }

    public void run() throws Exception {

        this.log.info("------- Initializing -------------------");
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();
        this.log.info("------- Initialization Complete --------");

        this.schedulingJobs(sched, StartThreads.class, "0 20 9 ? * MON-FRI", ShutdownThreads.class, "0 31 11 ? * MON-FRI");
        this.schedulingJobs(sched, StartThreads.class, "0 59 12 ? * MON-FRI", ShutdownThreads.class, "0 1 15 ? * MON-FRI");
        this.log.info("------- Starting Scheduler ----------------");
        sched.start();
        this.log.info("------- Started Scheduler -----------------");

        this.log.info("------- get stock list ----------------");
        JdbcFactory.get();
        JdbcFactory.fillList();
        JdbcFactory.release();
        this.log.info("------- got stock list -----------------");

        this.log.info("------- Waiting input ------------");
        String input = null;
        Scanner sc = new Scanner(System.in);

        while(true) {
            while(true) {
                input = sc.next();
                SimpleDateFormat formatterStart;
                SimpleDateFormat formatterEnd;
                if (input.equals("runtoday")) {
                    (new StartThreads()).execute((JobExecutionContext)null);
                    formatterStart = new SimpleDateFormat("0 20 9 dd MM ?");
                    formatterEnd = new SimpleDateFormat("0 31 11 dd MM ?");
                    this.schedulingJobs(sched, StartThreads.class, formatterStart.format(new Date()), ShutdownThreads.class, formatterEnd.format(new Date()));
                    formatterStart = new SimpleDateFormat("0 59 12 dd MM ?");
                    formatterEnd = new SimpleDateFormat("0 1 15 dd MM ?");
                    this.schedulingJobs(sched, StartThreads.class, formatterStart.format(new Date()), ShutdownThreads.class, formatterEnd.format(new Date()));
                } else if (input.equals("notruntoday")) {
                    formatterStart = new SimpleDateFormat("0 21 9 dd MM ?");
                    formatterEnd = new SimpleDateFormat("0 00 13 dd MM ?");
                    this.schedulingJobs(sched, ShutdownThreads.class, formatterStart.format(new Date()), ShutdownThreads.class, formatterEnd.format(new Date()));
                } else if (input.equals("liststocks")) {
                    JdbcFactory.get();
                    JdbcFactory.fillList();
                    JdbcFactory.release();
                    System.out.println("\t" + JdbcFactory.stockList);
                } else {
                    if (input.equals("exit")) {
                        this.log.info("------- Shutting Down ---------------------");
                        sched.shutdown(true);
                        this.log.info("------- Shutdown Complete -----------------");
                        SchedulerMetaData metaData = sched.getMetaData();
                        this.log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
                        System.exit(0);
                        return;
                    }

                    if (input.equals("help")) {
                        System.out.println("\truntoday: fire a job immediately and just run one day.");
                        System.out.println("\tnotruntoday: not run today.");
                        System.out.println("\tliststocks: list stocks.");
                        System.out.println("\texit: exit.");
                        System.out.println("\thelp: help.");
                    } else {
                        System.out.println("can not recognize input: " + input);
                        System.out.println("you can type help for command that can recognization.");
                    }
                }
            }
        }
    }

    void schedulingJobs(Scheduler sched, Class startClass, String startCommand, Class endClass, String endCommand) throws SchedulerException {
        this.log.info("------- Scheduling Jobs ----------------");
        JobDetail job = null;
        CronTrigger trigger = null;
        job = JobBuilder.newJob(startClass).build();
        trigger = (CronTrigger)TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(startCommand)).build();
        Date ft = sched.scheduleJob(job, trigger);
        this.log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger.getCronExpression());
        job = JobBuilder.newJob(endClass).build();
        trigger = (CronTrigger)TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(endCommand)).build();
        ft = sched.scheduleJob(job, trigger);
        this.log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger.getCronExpression());
    }

    public static void main(String[] args) throws Exception {
        SimpleExample example = new SimpleExample();
        example.run();
    }
}
