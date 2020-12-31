//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package js.test;

import _.test.Output;
import com.goldpac.instantissue.launcher.JdbcFactory;
import com.goldpac.instantissue.perso.InstantIssueTools;
import inputdata.m1_0_1.M1;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import minicardissue.com.goldpac.smallcardissue.ui.InterfaceData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.yccheok.jstock.engine.Code;
import org.yccheok.jstock.engine.HeXunStockHistoryServer;
import org.yccheok.jstock.engine.HeXunStockServer;
import org.yccheok.jstock.engine.Stock;
import org.yccheok.jstock.engine.YahooStockHistoryServer;
import org.yccheok.jstock.gui.Utils;

public class Test implements Job {
    private static final Log log = LogFactory.getLog(Test.class);
    public static String[] stockColumns = new String[34];

    public Test() {
    }

    public static void main(String[] args) throws Exception {
//        getHadoopOutput();

        JdbcFactory.get();
        JdbcFactory.fillList();
        JdbcFactory.release();

        get();
    }

    /*
     * 解析代码s生成请求体
     */
    public static void getHadoopOutput() throws Exception {
        M1 m1 = new M1();
        Properties properties = m1.getContext();
        File copy1 = new File("./stocksHadoopOutput/outputFiles");
        File copy2 = new File("./stocksHadoopOutput/working/outputFiles");
        InstantIssueTools iit = new InstantIssueTools();
        iit.copy(copy1, copy2, 0);
        properties.setProperty("recordDelimite", "\n");
        properties.setProperty("inputFolder", "./stocksHadoopOutput/working");
        m1.runJobInTOS(new String[]{""});
        Collection<InterfaceData> interfaceDatas = (Collection)m1.getContext().getInterfaceData();
        System.out.println(interfaceDatas.size());
        Iterator var12 = interfaceDatas.iterator();

        while(var12.hasNext()) {
            InterfaceData interfaceData = (InterfaceData)var12.next();
            String tempDate = new String(interfaceData.getProcessRecordData());
            System.out.println(tempDate);
//            String location = "http://180.76.144.164:50075/webhdfs/v1/stocksinfo.####-##-##/part-00000?op=OPEN&namenoderpcaddress=localhost:9000&offset=0";
            String location = "http://webstock.quote.hermes.hexun.com/a/quotelist?code=####-##-##&callback=callback&column=DateTime,LastClose,Open,High,Low,Price,Volume,Amount,LastSettle,SettlePrice,OpenPosition,ClosePosition,BuyPrice,BuyVolume,SellPrice,SellVolume,PriceWeight,EntrustRatio,UpDown,EntrustDiff,UpDownRate,OutVolume,InVolume,AvePrice,VolumeRatio,PE,ExchangeRatio,LastVolume,VibrationRatio,DateTime,OpenTime,CloseTime,Name,Code";
            location = location.replace("####-##-##", tempDate);
            String respond = Utils.getResponseBodyAsStringBasedOnProxyAuthOption(location);
            File folder = new File(".\\stocks\\000629");
            File file = new File(folder, "log");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(respond.getBytes());
        }

    }

    public static void getK() throws Exception {
        String location = "http://webstock.quote.hermes.hexun.com/a/multihistoryminute?code=sse600068&start=20130115&number=-2&callback=callback";
        String respond = Utils.getResponseBodyAsStringBasedOnProxyAuthOption(location);
        System.out.println(respond);
        Output.out("getK", respond.getBytes());
    }

    public static void get() throws Exception {
        String location = "http://webstock.quote.hermes.hexun.com/a/quotelist?code=szse000001,szse000002,szse000004,szse000005,szse000006,szse000007,szse000008,szse000009,szse000010,szse000011,szse000012,szse000014,szse000016,szse000017,szse000018,szse000019,szse000020,szse000021,szse000022,szse000023,szse000024,szse000025,szse000026,szse000027,szse000028,szse000029,szse000030,szse000031,szse000032,szse000033,szse000034,szse000035,szse000036,szse000037,szse000038,szse000039,szse000040,szse000042,szse000043,szse000045,szse000046,szse000048,szse000049,szse000050,szse000055,szse000056,szse000058,szse000059,szse000060,szse000061,szse000062,szse000063,szse000065,szse000066,szse000068,szse000069,szse000070,szse000078,szse000088&callback=callback&column=DateTime,LastClose,Open,High,Low,Price,Volume,Amount,LastSettle,SettlePrice,OpenPosition,ClosePosition,BuyPrice,BuyVolume,SellPrice,SellVolume,PriceWeight,EntrustRatio,UpDown,EntrustDiff,UpDownRate,OutVolume,InVolume,AvePrice,VolumeRatio,PE,ExchangeRatio,LastVolume,VibrationRatio,DateTime,OpenTime,CloseTime,Name,Code";
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
        int threadWaitTime = 5000;
        ExecutorService es = newFixedThreadPool(100);
        Iterator var7 = interfaceDatas.iterator();
        if (var7.hasNext()) {
            InterfaceData interfaceData = (InterfaceData)var7.next();
            if (interfaceData2s.isEmpty()) {
                throw new Exception("interfaceData2s is empty");
            }

            InterfaceData next = (InterfaceData)interfaceData2s.iterator().next();
            String tempLocation = new String(next.getProcessRecordData());
            String columns = tempLocation.substring(tempLocation.indexOf("column=") + 7);
            StringTokenizer tokenizer = new StringTokenizer(columns, ",");

            for(int var13 = 0; tokenizer.hasMoreTokens(); stockColumns[var13++] = tokenizer.nextToken()) {
            }

            String tempDate = new String(interfaceData.getProcessRecordData());
            tempLocation = tempLocation.replaceFirst("stocksInWindFormat", tempDate);
            log.info("list: " + tempLocation);
            es.execute(new LoadStocksRunner(tempLocation, threadWaitTime));
        }

    }

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
    }

    public static void parseStockHistory() throws Exception {
        Stock stock = null;
        FileInputStream respond = new FileInputStream(new File(".//ii"));
        ObjectInputStream ois = new ObjectInputStream(respond);
        stock = (Stock)ois.readObject();
        respond = null;
        FileInputStream fis = new FileInputStream(new File(".//oo.xml"));
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        fis.close();
        String str = new String(bytes);
        HeXunStockHistoryServer yshs = new HeXunStockHistoryServer(stock.code);
        yshs.parse(str);
    }

    public static void parseStock() throws Exception {
        HeXunStockServer yshs = new HeXunStockServer();
        Stock stock = yshs.getStock(Code.newInstance("600068.SS"));
        stock.show();
    }

    public static void parse2() throws Exception {
        FileInputStream fis = new FileInputStream(new File(".//ii"));
        ObjectInputStream ois = new ObjectInputStream(fis);
        Stock stock = (Stock)ois.readObject();
        YahooStockHistoryServer yshs = new YahooStockHistoryServer(stock.code);
        boolean returnValue = yshs.parse("Date,Open,High,Low,Close,Volume,Adj Close\n2015-07-02,10.10,10.24,8.91,8.91,22955400,8.91\n2015-07-01,10.50,10.90,9.90,9.90,25538200,9.90\n2015-06-30,9.99,10.87,9.12,10.81,33007500,10.81\n2015-06-29,11.40,11.55,10.13,10.13,24574600,10.13\n");
    }

    public void execute(JobExecutionContext jec) throws JobExecutionException {
        try {
            main((String[])null);
        } catch (Exception var3) {
            throw new JobExecutionException(var3.toString());
        }
    }
}
