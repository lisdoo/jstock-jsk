//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.goldpac.instantissue.launcher;

import com.goldpac.instantissue.perso.InstantIssueTools;
import inputdata.m1_0_1.M1;
import js.test.Test;
import minicardissue.com.goldpac.smallcardissue.ui.InterfaceData;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

public class LogThreads implements Job {
    Logger log = LoggerFactory.getLogger(LogThreads.class);

    public LogThreads() {
    }

    public void execute(JobExecutionContext jec) throws JobExecutionException {

        this.log.info("写一段日志而已。");
    }
}
