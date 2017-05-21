package main.java.com.docusign.app;

import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.io.File;

import java.util.Arrays;

/**
 * Creates a Dress class so that Dress classes for "HOT" and "COLD" 
 * weather can be created. 
 * It contains methods that check if the input numerical commands are
 * valid and create the appropriate string to be returned. 
 * @author Kushal
 *
 */
class Dress {
    
    /*
     * Variables that are used for computations. Takes values 
     * from the calling method of the ColdDress or HotDress 
     * object that created the Dress instance.
     */
    private ArrayList<Integer> inputArr;
    private String[] outArr;
    private String[] map;
    private boolean valid;
    
    /**
     * Makes and returns the string containing the commands to be
     * output for both HOT and COLD weather conditions.  
     * @param  map array used as a hash table with indices used as
     *         command numbers and the values are string commands.
     *         The HotDress and ColdDress classes pass in their 
     *         respective maps.
     * @param  inputArr ArrayList containing input commands in
     *         numerical form.  
     * @return output string representing commands for either HOT
     *         or COLD conditions.
     */
    String makeString(String[] map, ArrayList<Integer> inputArr) { 
        this.inputArr = inputArr;
        
        /* map array used as a hash table to map numbers to commands. */
        this.map = map;                                    
        
        /* Array to hold the commands to be output */
        this.outArr = new String[this.inputArr.size()];
        
        /* Iterate through input commands and create output commands 
         * after checking if the commands are valid */
        for (int i = 0; i < this.inputArr.size(); i++) {                                       
            valid = this.checkValid(this.inputArr, i);
            if (valid) {
                
                /* The command number from input used to index the map
                 * and obtain the correct string to be output when
                 * it's valid */
                this.outArr[i] = this.map[this.inputArr.get(i) - 1];                      
            }
            else {
                
                /* Output "fail" when it's invalid and stop processing
                 * commands */
                this.outArr[i] = "fail";
                break;
            }
        }
        return joinArray(this.outArr);
    }
    
    /**
     * Checks if the given (current) numerical command is
     * allowed to be the next command to be executed.
     * @param arr the ArrayList containing the numerical commands.
     * @param index the current index of arr whose command is
     *        being evaluated.
     * @return true if it's valid and 
     *         false if it's invalid.
     */
    private boolean checkValid(ArrayList<Integer> arr, int index) {
        int validLen = arr.size();
        
        /* Can't wear item already worn */
        if (arr.subList(0, index).contains(arr.get(index)))             
            return false;       
        
        /* It's first command */
        if (index == 0) {       
            
            /* Can't do anything before 
             * removing pajamas */
            if (arr.get(index) != 8)                 
                return false;                        
            return true;
        }
        
        /* If size of arr is greater than 8, assume validLen 
         * to be 8 in order to check if 8th command is 7,
         * not the very last command when arr is longer than 8 */ 
        if (validLen > 8)
            validLen = 8;      
        
        /* It's last of validLen number of commands */
        if (index == validLen - 1) {                
            
            /* Last command of validLen commands has to be 7 */
            if (arr.get(index) != 7)    
                return false;
            
            /* If it's HOT, can't leave without 
             * wearing all items of HotDress */
            if (this.map[2].equals("fail")) {
                if (!arr.containsAll(Arrays.asList(1, 2, 4, 6))) 
                    return false;
            }
            
            /* If it's COLD, can't leave without 
             * wearing all items of ColdDress */
            else if (!arr.containsAll(Arrays.asList(1, 2, 3, 4, 5, 6))) { 
                return false;
            }
            
            /* Can leave when wearing all items of 
             * Dress (HotDress or ColdDress) */
            return true;                
        }                      
        
        /* Commands from input array as switch cases - check if commands
         * with conditions are (or aren't) already traversed to determine
         * if valid */
        switch(arr.get(index)) {
            case 1:
                
                /* Wearing footwear before pants */
                if (!arr.subList(0, index).contains(6)) {
                    
                    return false;           
                }
                /* When it's COLD, wearing footwear before socks */
                if ((!this.map[2].equals("fail")) && (!arr.subList(0, 
                                                index).contains(3))) {
                    return false;
                }
                break;
            case 2:
                
                /* Wearing headwear before shirt */
                if (!arr.subList(0, index).contains(4)) 
                    return false;
                break;              
            case 3:
                
                /* When it's HOT, wearing socks */
                if (this.map[2].equals("fail")) 
                    return false;
                break;
            case 4:
                
                /* Headwear and jacket cases already handled,
                 * so they can't be worn before shirt */
                ;       
                break;
            case 5:
                
                /* Wearing jacket when it's HOT */
                if (this.map[2].equals("fail")) 
                    return false;
                
                /* can't wear jacket before shirt */
                if (!arr.subList(0, index).contains(4)) 
                    return false;
                break;
            case 6:
                
                /* Socks and footwear commands already handled, 
                 * so they can't be worn before pants
                 */
                ;       
                break;          
            default:
                
                /* Leaving house before wearing all required items or 
                 * input some other command 
                 */
                return false;           
        }
            
    /* When command is valid */ 
    return true;                    
    }
    
    /**
     * Joins the array containing output commands to form 
     * the output string without appending any nulls.
     * @param arr contains output commands.
     * @return outStr output string.
     */
    private String joinArray(String[] arr){
        String outStr = arr[0]; 
        for(int i = 1; i < arr.length; i++) {     
            if (arr[i] != null)
                outStr += ", " + arr[i];
        }
        return outStr;
    }
    
    /**
     * Gets the required map from the XML file.
     * @param mapType either cold or hot depending on which object
     * it's called on.
     * @return map - a string array
     */
    String[] readXML(String mapType) {
        String[] arr = null;
        try {
            File xmlFile = new File(System.getProperty("user.dir") 
                    + "\\src\\main\\java\\com\\docusign\\app\\maps.xml");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xmlFile);
            
            doc.getDocumentElement().normalize();        
            Node node = doc.getElementsByTagName(mapType).item(0);
            NodeList nodeList = node.getChildNodes();
            arr = new String[nodeList.getLength()];
            for (int n = 0; n < nodeList.getLength(); n++) {
                arr[n] = nodeList.item(n).getTextContent();
           
            }
        } catch (Exception e) {
            e.printStackTrace();
            }
        
        return arr;
    }

}


/**
 * This class (HotDress) is composed using an instance
 * of the Dress class. It contains a method that calls
 * the Dress instance's method to create the string to
 * be displayed when there are HOT weather conditions.
 * @author Kushal
 */
class HotDress {
    private Dress d;
    private ArrayList<Integer> inputArr;
    
    /* The map contains all items that can be worn when it is HOT. */
    private String[] map;
    
    /**
     * Constructs the instance of Dress class.
     * @param inputData the numerical input 
     *        commands.
     */
    HotDress(ArrayList<Integer> inputData) {
        this.d = new Dress();
        
        /* Retrieves the map for HOT conditions */
        this.map = d.readXML("hotMap");
        this.inputArr = inputData;
    }
    
    /**
     * Makes and returns the string containing the correct 
     * commands for HOT weather condition using the method
     * belonging to the instance of Dress.
     * @return string containing the commands for COLD weather 
     *         to be displayed. 
     */
    String makeString()  {
        return d.makeString(this.map, this.inputArr);
    }
}


/**
 * This class (ColdDress) is composed using an instance
 * of the Dress class. It contains a method that calls
 * the Dress instance's method to create the string to
 * be displayed when there are COLD weather conditions.
 * @author Kushal
 */
class ColdDress {
    private Dress d;
    private ArrayList<Integer> inputArr;
    
    /* The map contains all items that can be worn when it is COLD */
    private String[] map;
    
    /**
     * Constructs the instance of Dress class.
     * @param inputData the numerical input 
     *        commands.
     */
    ColdDress(ArrayList<Integer> inputData) {        
        this.d = new Dress();
        
        /* Retrieves the map for COLD conditions */
        this.map = d.readXML("coldMap");
        this.inputArr = inputData;
    }
    
    /**
     * Makes and returns the string containing the correct 
     * commands for COLD weather conditions using the method
     * belonging to the instance of Dress.
     * @return string containing the commands for COLD weather 
     *         to be displayed.
     */
    String makeString()  {
        return d.makeString(this.map, this.inputArr);
    }
}
