//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.goldpac.instantissue.launcher;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownThreads implements Job {
    Logger log = LoggerFactory.getLogger(ShutdownThreads.class);

    public ShutdownThreads() {
    }

    public void execute(JobExecutionContext jec) throws JobExecutionException {
        try {
            String[] scenraiosName = ThreadFactory.get().getScenarioNameList();
            this.log.info("shutdown: " + scenraiosName.length);
            String[] var3 = scenraiosName;
            int var4 = scenraiosName.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String sn = var3[var5];
                this.log.info("        : " + sn);
                ThreadFactory.get().killSpecificScenario(sn);
            }

        } catch (Exception var7) {
            var7.printStackTrace();
            throw new JobExecutionException(var7.toString());
        }
    }
}
