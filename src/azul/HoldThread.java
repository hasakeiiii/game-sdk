package azul;

import java.util.concurrent.CountDownLatch;

class HoldThread extends Thread {  
    CountDownLatch cdl = new CountDownLatch(1);  
  
    public HoldThread() {  
        this.setDaemon(true);  
    }  
  
    public void run() {  
        try {  
            cdl.await();  
        } catch (InterruptedException e) {  
        }  
    }  
}  