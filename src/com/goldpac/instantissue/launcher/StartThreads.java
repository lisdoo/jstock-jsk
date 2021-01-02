//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.goldpac.instantissue.launcher;

import com.goldpac.instantissue.perso.InstantIssueTools;
import inputdata.m1_0_1.M1;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

import js.test.Test;
import minicardissue.com.goldpac.smallcardissue.ui.InterfaceData;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class StartThreads implements Job {
    Logger log = LoggerFactory.getLogger(StartThreads.class);

    public StartThreads() {
    }

    public void execute(JobExecutionContext jec) throws JobExecutionException {

        try {
            M1 m1 = new M1();
            Properties properties = m1.getContext();
            File copy1 = new File("./stocksInWindFormat/stocks");
            File copy2 = new File("./stocksInWindFormat/working/stocks");
            InstantIssueTools iit = new InstantIssueTools();
            iit.copy(copy1, copy2, 0);
            properties.setProperty("recordDelimite", "\n");
            properties.setProperty("inputFolder", "./stocksInWindFormat/working");
            m1.runJobInTOS(new String[]{""});
            Collection<InterfaceData> interfaceDatas = (Collection)m1.getContext().getInterfaceData();
            System.out.println(interfaceDatas.size());
            copy1 = new File("./stocksHeXunQuere/quere");
            copy2 = new File("./stocksHeXunQuere/working/quere");
            iit.copy(copy1, copy2, 0);
            properties.setProperty("recordDelimite", "\n");
            properties.setProperty("inputFolder", "./stocksHeXunQuere/working");
            m1.runJobInTOS(new String[]{""});
            Collection<InterfaceData> interfaceData2s = (Collection)m1.getContext().getInterfaceData();
            System.out.println(interfaceData2s.size());
            Iterator var21 = interfaceDatas.iterator();

            while(var21.hasNext()) {
                InterfaceData interfaceData = (InterfaceData)var21.next();
                if (interfaceData2s.isEmpty()) {
                    throw new Exception("interfaceData2s is empty");
                }

                InterfaceData next = (InterfaceData)interfaceData2s.iterator().next();
                String tempLocation = new String(next.getProcessRecordData());
                String columns = tempLocation.substring(tempLocation.indexOf("column=") + 7);
                StringTokenizer tokenizer = new StringTokenizer(columns, ",");

                for(int var12 = 0; tokenizer.hasMoreTokens(); Test.stockColumns[var12++] = tokenizer.nextToken()) {
                }

                String stockList = new String(interfaceData.getProcessRecordData());

                tempLocation = tempLocation.replaceFirst("stocksInWindFormat", stockList);
                String componentName = "BusinessComponent";
                String businessName = "BusinessStocks";
                String indentityName = null;
                String businessType = "Loop";
                String parameters = null;
                indentityName = String.format("%04d", Integer.parseInt(interfaceData.getJobRecordId()));
                this.log.info("play: " + componentName + businessName + indentityName + " [" + businessType + "] : " + tempLocation);
                ThreadFactory.get().playScenario(componentName, businessName, indentityName, businessType, tempLocation);

                for (String stockCode: stockList.split(",")) {
                    if (JdbcFactory.stockList.contains(stockCode)) {
                        RabbitTemplate rt = MqFactory.get(stockCode);
                    }
                }
                System.out.println("Created :" + stockList);
            }

        } catch (Exception var17) {
            var17.printStackTrace();
            throw new JobExecutionException(var17.toString());
        }
    }
}
