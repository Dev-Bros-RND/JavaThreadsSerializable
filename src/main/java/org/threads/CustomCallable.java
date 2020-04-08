package org.threads;

import java.util.concurrent.Callable;

public class CustomCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        return "Callable Works";
    }

}
