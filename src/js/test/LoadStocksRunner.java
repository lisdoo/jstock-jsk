//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package js.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.Iterator;

import com.goldpac.instantissue.launcher.JdbcFactory;
import com.goldpac.instantissue.launcher.MqFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.yccheok.jstock.gui.Utils;

public class LoadStocksRunner implements Runnable {
    private static final Log log = LogFactory.getLog(LoadStocksRunner.class);
    String location;
    int threadWaitTime;
    int hashCode = 0;

    public LoadStocksRunner(String location, int threadWaitTime) {
        this.location = location;
        this.threadWaitTime = threadWaitTime;
    }

    public void run() {
        String respond = Utils.getResponseBodyAsStringBasedOnProxyAuthOption(this.location);
        if (respond.hashCode() == this.hashCode) {
            log.info("Hash code is equ, no need log");
        } else {
            log.info(respond);
            this.hashCode = respond.hashCode();
            try {
//                createJSONObject(respond);
                createJstockMqMsg(respond);
            } catch (Throwable t) {
                // TODO 短信通知
                t.printStackTrace();
            }
        }

        try {
            Thread.sleep((long)this.threadWaitTime);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    void createJstockMqMsg(String str) throws Exception {
        JSONObject jo = JSON.parseObject(str.substring(9, str.length() - 2));
        JSONArray ja = jo.getJSONArray("Data");
        ja = ja.getJSONArray(0);
        Iterator var4 = ja.iterator();

        while(var4.hasNext()) {
            Object innerJoTemp = var4.next();
            Object innerJo = (JSONArray)innerJoTemp;
            int i = 0;
            Iterator var7 = ((JSONArray)innerJo).iterator();

            while(var7.hasNext()) {
                Object s = var7.next();
                if (i == 33) {
                    if (JdbcFactory.stockList.contains(s.toString())) {
                        RabbitTemplate rt = MqFactory.get(s.toString());
                        rt.convertAndSend(innerJo);
                    }
                }
                i++;
            }
        }

    }

    void createJSONObject(String str) throws Exception {
        JSONObject jo = JSON.parseObject(str.substring(9, str.length() - 2));
        JSONArray ja = jo.getJSONArray("Data");
        ja = ja.getJSONArray(0);
        Iterator var4 = ja.iterator();

        while(var4.hasNext()) {
            Object innerJoTemp = var4.next();
            Object innerJo = (JSONArray)innerJoTemp;
            int i = 0;
            Iterator var7 = ((JSONArray)innerJo).iterator();

            while(var7.hasNext()) {
                Object s = var7.next();
                System.out.println(String.format("%02d", i) + "\t" + String.format("%20s", Test.stockColumns[i++]) + "\t" + s);
            }

            System.out.println("------------------------------");
        }
    }
}
