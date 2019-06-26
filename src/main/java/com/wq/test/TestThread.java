package com.wq.test;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThread {

    //K-文件id，V-文件中的数字
    private Map<Integer, List<Integer>> map = new HashMap<>();

    private ExecutorService service = Executors.newCachedThreadPool();

    @Test
    public void test1() {
        long time = System.currentTimeMillis();
        ExecutorService service = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; i++) {
            int j = i;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(j);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        service.shutdown();
        System.out.println("======================执行完毕,耗时" + (System.currentTimeMillis() - time) + "========================");


    }

    @Test
    public void test2() throws Exception {

        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            integerList.add(-1);
        }

        //map初始化
        for (int i = 0; i < 10; i++) {
            map.put(i, integerList);
        }

        //启动10个任务将数据写入文件
        for (int i = 0; i < 10; i++) {
            int m = i;
            service.execute(() -> {


                //1、创建文件
                File file = new File("g:" + m + ".txt");
                if (file.exists()) {
                    if (file.delete()) {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                System.out.println("=========" + m + "=========");
                try {

                    PrintStream ps = new PrintStream(new FileOutputStream(file));
                    //记录每一个文件的数字集合
                    List<Integer> list = new ArrayList<>();

                    //2、写入10个数字
                    for (int j = 0; j < 10; j++) {
                        while (true) {
                            int num = new Random().nextInt(10);

                            //3、检查当前文件有没有重复数字
                            if (!list.contains(num)) {

                                //4、检查相同索引处数字是否重复

                                if (m == 0) {//第1个文件检查自身有无重复即可
                                    ps.print(num);

                                    list.add(num);

                                    map.get(m).add(j, num);
                                    break;

                                } else {//第2个及后面的文件除了检查自身有无重复外，还需要检查相同索引处数字是否重复

                                    boolean flag = true;
                                    bb:
                                    for (List<Integer> integers : map.values()) {
                                        //判断每个文件的相同索引位置是否重复
                                        if (integers.size() > 0) {

                                            for (int k = 0; k < integers.size(); k++) {
                                                if (k == j && integers.get(k) == num) {

                                                    flag = false;
                                                    //跳出Map循环
                                                    break bb;
                                                }

                                            }
                                        }
                                    }

                                    if (flag) {

                                        list.add(num);
                                        ps.print(num);

                                        map.get(m).add(j, num);

                                        break;
                                    }

                                }

                            }

                        }
                    }
                    //记录每个文件的数字集合
                    System.out.println("第【" + m + "】个集合" + list);
                } catch (Exception e) {
                    System.out.println("========e=======");
                    e.printStackTrace();
                }

            });
        }

    }

    @Test
    public void test3() throws IOException {

        Map<Integer, List<Integer>> map = new HashMap<>();

        long time = System.currentTimeMillis();

        //10个文件
        for (int i = 0; i < 10; i++) {

            //1、创建文件
            File file = new File("g:" + i + ".txt");
            if (file.exists()) {
                if (file.delete()) {
                    file.createNewFile();
                }
            } else {
                file.createNewFile();
            }

            try {

                PrintStream ps = new PrintStream(new FileOutputStream(file));
                //记录每一个文件的数字集合
                List<Integer> list = new ArrayList<>();

                //2、写入10个数字
                for (int j = 0; j < 10; j++) {
                    while (true) {
                        int num = new Random().nextInt(20);

                        //3、检查当前文件有没有重复数字
                        if (!list.contains(num)) {

                            //4、检查相同索引处数字是否重复

                            if (i == 0) {//第1个文件检查自身有无重复即可
                                ps.print(num);

                                list.add(num);
                                break;

                            } else {//第2个及后面的文件除了检查自身有无重复外，还需要检查相同索引处数字是否重复

                                boolean flag = true;
                                aa:
                                for (List<Integer> integers : map.values()) {
                                    //判断每个文件的相同索引位置是否重复
                                    if (integers.size() > 0) {

                                        for (int k = 0; k < integers.size(); k++) {
                                            if (k == j && integers.get(k) == num) {

                                                flag = false;
                                                //跳出Map循环
                                                break aa;
                                            }

                                        }
                                    }
                                }

                                if (flag) {

                                    list.add(num);
                                    ps.print(num);

                                    break;
                                }

                            }

                        }

                    }
                }
                //记录每个文件的数字集合
                System.out.println("第【" + i + "】个集合" + list);
                map.put(i, list);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        System.out.println("======================执行完毕,耗时" + (System.currentTimeMillis() - time) + "========================");


    }

}
