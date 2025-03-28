/**
 * Class Lab2 - Defines methods to perform various string manipulations.
 * 
 * @author Bryce Kwon
 * @version 2022-09-05
 */
public class Lab2 {
    // constructor for the 'Lab2' object.
    public Lab2() {
    }

    /** #0: trimmed **/
    public String stringTrim(String input) {
        input = input.trim();
        
        return input;
    } // stringTrim
    
    /** #1: trimmed, converted to upper case **/
    public String stringUpperCase(String input) {
        input = input.trim();
        input = input.toUpperCase();
        
        return input;
    } // stringUpperCase
    
    /** #2: trimmed, with each '.' character replaced with '?' **/
    public String stringReplacePeriodWithQuestion(String input) {
        input = input.trim();
        input = input.replace(".", "?");
        
        return input;
    }
    
    /** #3: trimmed, with trimmed length appended in parentheses **/
    public String stringAppendLength(String input) {
        input = input.trim();
        input = input + " (" + input.length() + ")";
        
        return input;
    }
       
    /** #4: trimmed, with first 10 characters removed **/
    public String stringMinusFirstTenChars(String input) {
        input = input.trim();
        
        if (input.length() < 10) {
            input = "";
        } else {
            input = input.substring(10);   
        }
        
        return input;
    }
    
    /** #5: trimmed, with last 10 characters removed **/
    public String stringMinusLastTenChars(String input) {
        input = input.trim();
        
        if (input.length() < 10) {
            input = "";
        } else {
            input = input.substring(0, input.length() - 10);   
        }
        
        return input;
    }
    
    /** #6: trimmed, with '-' inserted after the first and second character **/
    public String stringInsert(String input) {
        input = input.trim();
        
        if (input.length() < 2) {
            input = "";
        } else {
            input = input.substring(0,1) + "-" + input.substring(1,2) + "-" + input.substring(2); 
        }
        
        return input;
    }        

    /** #7: trimmed, with the first word only **/
    public String stringFirstWord(String input) {
        input = input.trim();
        
        if (input.lastIndexOf(" ") == -1) {
            input = input;
        } else if (input.lastIndexOf(" ") >= 0) {
            input = input.substring(0, input.indexOf(" ")); 
        }
        
        return input;
    }
    
    /** #8: trimmed, with everything except the last word converted to lower case **/
    public String stringLowerCaseExceptLastWord(String input) {
        input = input.trim();
        
        if (input.lastIndexOf(" ") == -1) {
            input = input;
        } else if (input.lastIndexOf(" ") >= 0) {
            int space = input.lastIndexOf(" ");
            input = input.substring(0, space).toLowerCase() + input.substring(space);   
        }
        
        return input;
    }  
    
    
    /** #9: trimmed, with '...' inserted in the middle (or slight to the right) **/
    public String stringMiddleEllipsis(String input) {
        input = input.trim();
        
        int midIndex = ((input.length() / 2) + (input.length() % 2));
        input = input.substring(0, midIndex) + "..." + input.substring(midIndex);
        
        return input;
    }
    
    /** #10: trimmed, with all but the second-to-last word **/
    public String stringAllButSecondToLastWord(String input) {
        input = input.trim();
        
        if (input.lastIndexOf(" ") == -1) {
            input = input;
        } else if (input.lastIndexOf(" ") >= 0) {
            String lastWord = input.substring(input.lastIndexOf(" "));
            input = input.substring(0, input.lastIndexOf(" "));
            
            if (input.lastIndexOf(" ") == -1) {
                input = lastWord.trim();
            } else if (input.lastIndexOf(" ") >= 0) {
                input = input.substring(0, input.lastIndexOf(" "));
                input = input + lastWord;
            }
        }
        
        return input;
    }
    
    /** Extra Credit #1: trimmed, with the first 3/4 of the string (or slightly more) **/
    public String stringFirstThreeQuarters(String input) {
        input = input.trim();
        
        int remainder = input.length() % 4;
        int strLen = input.length() / 4 * 3;
        input = input.substring(0, strLen + remainder);
        
        return input;
    }
}