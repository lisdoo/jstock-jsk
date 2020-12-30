//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.goldpac.instantissue.launcher.businesscomponent.businessstocks;

import com.goldpac.instantissue.perso.InstantIssueTools;
import com.goldpac.instantissue.perso.LoopGenericTransformer;
import java.util.Map;
import js.test.LoadStocksRunner;

public class BusinessStocksLoopMode extends LoopGenericTransformer {
    int i = 0;
    InstantIssueTools iit = null;

    public BusinessStocksLoopMode(Map parameters, Object callingObject) {
        super(parameters, callingObject);
        this.iit = new InstantIssueTools(callingObject);
    }

    public void transform() throws Exception {
        try {
            LoadStocksRunner loadStocksRunner = new LoadStocksRunner(this.getStringParameter("ConfigFile"), 5000);

            do {
                loadStocksRunner.run();
            } while((Boolean)this.iit.executeDynamicMethod(super.callingObject, "isStillAlive", (Class[])null, (Object[])null));
        } catch (Throwable var2) {
            var2.printStackTrace();
        }

    }
}
