package azul;

public class Thread2 {  
    public  void m4t1(Integer a) {  
         synchronized(this) 
         {  
              int i = 5;  
              while( i-- > 0) {  
                   System.out.println(Thread.currentThread().getName() + " : " + i);  
                   try {  
                        Thread.sleep(500);  
                   } catch (InterruptedException ie) {  
                   }  
              }  
         }  
    }  
    synchronized public  void m4t2(Integer a) {  
         int i = 5;  
         //synchronized(a) 
         {  
	         while( i-- > 0) {  
	              System.out.println(Thread.currentThread().getName() + " : " + i);  
	              try {  
	                   Thread.sleep(500);  
	              } catch (InterruptedException ie) {  
	              }  
	         }  
         }
    } 
}