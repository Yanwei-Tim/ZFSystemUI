package com.android.systemui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class SystemUIService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        ((SystemUIApplication) getApplication()).startServicesIfNeeded();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        SystemUI[] services = ((SystemUIApplication) getApplication()).getServices();
        if (args == null || args.length == 0) {
            for (SystemUI ui: services) {
                pw.println("dumping service: " + ui.getClass().getName());
                ui.dump(fd, pw, args);
            }
        } else {
            String svc = args[0];
            for (SystemUI ui: services) {
                String name = ui.getClass().getName();
                if (name.endsWith(svc)) {
                    ui.dump(fd, pw, args);
                }
            }
        }
    }
}