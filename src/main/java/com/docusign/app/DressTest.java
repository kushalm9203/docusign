package main.java.com.docusign.app;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

public class DressTest {
    @Test
    public void makeStringTestFails1() {
        Dress d = new Dress();
        
        /* First command not "Removing PJs" */
        String returnedStr = d.makeString(d.readXML("hotMap"), 
              new ArrayList<Integer>(Arrays.asList(5, 6, 4)));
        assertEquals("It's fail: ", "fail", returnedStr);
        
    }
    
    @Test
    public void makeStringTestFails2() {
        Dress d = new Dress();
        
        /* Shirt worn more than once */
        String returnedStr = d.makeString(d.readXML("coldMap"), 
                new ArrayList<Integer>(Arrays.asList(8, 4, 4, 2, 5, 6, 1, 7)));
        assertEquals("It's fail: ", "Removing PJs, shirt, fail", returnedStr);       
    }
    
    @Test
    public void makeStringTestFails3() {
        Dress d = new Dress();
        
        /* Socks when it's hot */
        String returnedStr = d.makeString(d.readXML("hotMap"), 
                new ArrayList<Integer>(Arrays.asList(8, 3, 4, 2, 1, 7)));
        assertEquals("It's fail: ", "Removing PJs, fail", returnedStr);
        
    }
    
    @Test
    public void makeStringTestFails4() {
        Dress d = new Dress();
        
        /* Shoes before socks */
        String returnedStr = d.makeString(d.readXML("coldMap"), 
                new ArrayList<Integer>(Arrays.asList(8, 6, 1, 3, 4, 2, 7)));
        assertEquals("It's fail: ", "Removing PJs, pants, fail", returnedStr);
        
    }
    
    @Test
    public void makeStringTestFails5() {
        Dress d = new Dress();
        
        /* Shoes before pants */
        String returnedStr = d.makeString(d.readXML("coldMap"), 
                new ArrayList<Integer>(Arrays.asList(8, 3, 1, 6, 4, 2, 7)));
        assertEquals("It's fail: ", "Removing PJs, socks, fail", returnedStr);
        
    }
    
    @Test
    public void makeStringTestFails6() {
        Dress d = new Dress();
        
        /* Headwear before shirt */
        String returnedStr = d.makeString(d.readXML("coldMap"), 
                new ArrayList<Integer>(Arrays.asList(8, 6, 3, 2, 4, 5, 1, 7)));
        assertEquals("It's fail: ", "Removing PJs, pants, socks, fail", returnedStr);
        
    }

    @Test
    public void makeStringTestFails7() {
        Dress d = new Dress();
        
        /* Jacket before shirt */
        String returnedStr = d.makeString(d.readXML("coldMap"), 
                new ArrayList<Integer>(Arrays.asList(8, 6, 3, 5, 4, 2, 1, 7)));
        assertEquals("Success: ", "Removing PJs, pants, socks, fail", returnedStr);
    }
  
    @Test
    public void makeStringTestFails8() {
        Dress d = new Dress();
        
        /* Leave house before wearing all required items */
        String returnedStr = d.makeString(d.readXML("coldMap"), 
                new ArrayList<Integer>(Arrays.asList(8, 6, 7)));
        assertEquals("It's fail: ", "Removing PJs, pants, fail", returnedStr);
                                                                                        
    }
    
    @Test
    public void makeStringTestFails9() {
        Dress d = new Dress();
        
        /* Jacket when it's hot */
        String returnedStr = d.makeString(d.readXML("hotMap"), 
                new ArrayList<Integer>(Arrays.asList(8, 6, 4, 5, 2, 1, 7)));
        assertEquals("Success: ", "Removing PJs, shorts, t-shirt, fail", returnedStr);
                                                                                    
    }
    

    @Test
    public void makeStringTestSucceeds1() {
        Dress d = new Dress();
    String returnedStr = d.makeString(d.readXML("hotMap"), 
            new ArrayList<Integer>(Arrays.asList(8, 6, 4, 2, 1, 7)));
    assertEquals("Success: ", "Removing PJs, shorts, t-shirt, sun visor, sandals, leaving house",
            returnedStr);
    }
    
    @Test
    public void makeStringTestSucceeds2() {
        Dress d = new Dress();
    String returnedStr = d.makeString(d.readXML("coldMap"), 
            new ArrayList<Integer>(Arrays.asList(8, 6, 3, 4, 2, 5, 1, 7)));
    assertEquals("Success: ", "Removing PJs, pants, socks, shirt, hat, jacket, boots, leaving house",
                                                                                        returnedStr);
    }
    
}
