package org;

import org.pojo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threads.CustomCallable;
import org.threads.CustomRunnable;

import java.io.*;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String ...args) throws ExecutionException, InterruptedException {



    }

    private static void runnableExample() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new CustomRunnable());

        System.out.println("after exec");
    }

    private static void futureExample() {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new CustomCallable());

        System.out.println(new Date());

        while(!future.isDone()) {}

        System.out.println(new Date());

        try{
            System.out.println(future.get());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        executor.shutdown();

    }

    private static void readWriteObjectExample() {
        Person p1 = new Person("Nick1", "Levin1", 25);
        Person p2 = new Person("Nick2", "Levin2", 25);
        Person p3 = new Person("Nick3", "Levin3", 25);

        try(FileOutputStream fileOutputStream = new FileOutputStream(new File("myObjects.shatz"));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(p1);
            objectOutputStream.writeObject(p2);
            objectOutputStream.writeObject(p3);


        } catch (IOException e) {
            log.error(e.getMessage());
        }

        try(FileInputStream fileInputStream = new FileInputStream(new File("myObjects.shatz"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            Person inPer1 = (Person) objectInputStream.readObject();
            Person inPer2 = (Person) objectInputStream.readObject();
            Person inPer3 = (Person) objectInputStream.readObject();

            log.debug(inPer1.toString());
            log.debug(inPer2.toString());
            log.debug(inPer3.toString());

        } catch(IOException | ClassNotFoundException e) {
            log.error(e.getMessage());
        }
    }

}
