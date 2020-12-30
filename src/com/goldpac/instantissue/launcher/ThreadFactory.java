//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.goldpac.instantissue.launcher;

import com.goldpac.instantissue.execution.ScenarioManager;

public class ThreadFactory {
    static ScenarioManager scenarioManager;

    public ThreadFactory() {
    }

    public static ScenarioManager get() {
        if (scenarioManager == null) {
            scenarioManager = new ScenarioManager();
        }

        return scenarioManager;
    }
}
