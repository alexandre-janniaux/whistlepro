package fr.enst.pact34.whistlepro.api2.test.threading;

import org.junit.Test;

import java.util.Hashtable;
import java.util.LinkedList;

import fr.enst.pact34.whistlepro.api2.threading.ThreadPool;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 16/04/16.
 */
public class ThreadPoolTest {

    @Test
    public void threadPoolTest()
    {
        System.out.println("thread id " + Thread.currentThread().getId());
        ThreadPool threadPool = new ThreadPool(2);
        int nbCalc = 1000;
        final LinkedList<Long> res = new LinkedList<>();

        for (int i = 0; i < nbCalc; i++) {
            final int finalI = i;
            threadPool.addWorkToDo(
                    new Runnable() {
                        @Override
                        public void run() {
                            //System.out.println("i = " + finalI + " ; thread id " + Thread.currentThread().getId());
                            synchronized (res) {
                                try {
                                    Thread.sleep((int)Math.random()*1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                res.add( Thread.currentThread().getId());
                            }
                        }
                    }
            );
        }

        while (threadPool.worksToDoCount()>0)
        {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assertEquals(nbCalc,res.size());

        Hashtable<Long,Integer> ids = new Hashtable<>();

        for (Long l : res
             ) {
            if(ids.containsKey(l)==false)
            {
                ids.put(l, 1);
            }
            else
            {
                ids.put(l,ids.get(l)+1);
            }

        }

        for (Long id: ids.keySet()
             ) {
            System.out.println("Thread " + id + " has done " + ids.get(id) + " works.");
        }
    }
}
