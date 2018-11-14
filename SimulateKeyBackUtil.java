package com.szy.erpcashier.Util;

import android.app.Instrumentation;
import android.text.InputFilter;
import android.view.KeyEvent;

/**
 * 模拟物理返回按键
 */
public class SimulateKeyBackUtil {

    public static void simulateKeyBack() {
        new Thread() {
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                } catch (Exception e) {
                }
            }
        }.start();
    }
}
