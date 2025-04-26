import java.io.*;
import java.util.*;

/**
 * FileHandler - performs a number operations on file
 * 
 * A FileHandler object performs operations on files. After each operation
 * the FileHandler keeps track of the result of the operations, which the
 * caller may query by invoking the 'getMessage' operation on the object. The
 * 'getMessage' method returns a String.
 *
 * A FileHandler object performs the following operations:
 *   - createEmptyFile - creates an new (empty) file
 *   - deleteFile - deletes a file
 *   - listContents: reads all the bytes in the file. The result of a 'lastMessage'
 *     after this operation is the String containing the contents of the file, where
 *     each byte in the file is treated as a char.
 *   - countBytes - counts the number of bytes in the file. The result of the
 *     'lastMessage' will be a string that tells the number of bytes counted.
 *   - copyFile - copies the contents of one file to another. The 'lastMessage' string
 *     is an indication as to whether the copying was successfully done. *   
 *   - appendString - appends a string to a text file.
 *   - copyFileInvertCase - copies the contents of a source file to a second file, except upper
       and lowercase letters are swapped.
 */
public class FileHandler {

    /**
     * Instance Variables
     */

    // the message to give to the caller, if asked
    private String lastMessage;

    // machine-independent representation of newline character-sequence
    private static String EOL = System.getProperty("line.separator");

    /**
     * FileHandler constructor - creates a new FileHandler object
     */
    public FileHandler() {
        lastMessage = "";
    }

    /**
     * Gives the message that resulted from the most recent file operation
     * @return The object's message.
     */
    public String getMessage() { 
        return lastMessage; 
    }

    /**
     * Creates a file with no bytes in it.
     * 
     * The object's message is changed to reflect the method's
     * status. If a file by that name already exists, it is deleted.
     *     
     * @param fileName - the name of the file to be created.
     */
    public void createEmptyFile(String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.close();
            lastMessage = fileName + " created successfully!";
        } catch (Exception e) {
            lastMessage = e.getMessage();
        }
    }

    /**
     * Deletes a file.
     * 
     * The object's message is changed to reflect the method's
     * status.
     * 
     * @param fileName - the name of the file to be deleted.
     */
    public void deleteFile(String fileName) {
        File delete = new File(fileName);
        boolean success = delete.delete();
        if (success) {
            lastMessage = fileName + " deleted successfully!";
        } else {
            lastMessage = fileName + " failed to delete!";
        }
    }

    /**
     * Set's this object's message to contents of a file.
     * 
     * The object's message is changed.  If the file-read was successful,
     * then the message consists of the file's contents.  Otherwise it consists
     * of a message that the file was not successfully read.
     * 
     * @param fileName - The name of the file whose contents are to be examined
     */
    public void listContents(String fileName) {
        try {
            lastMessage = "";
            File doc = new File(fileName);
            Scanner reader = new Scanner(doc);
            while (reader.hasNextLine()) {
                lastMessage = lastMessage + reader.nextLine() + EOL;
            }
            reader.close();
        } catch (Exception e) {
            lastMessage = e.getMessage();
        }        
    }

    /**
     * Counts the number of bytes in a file
     * 
     * The object's message is changed to reflect the method's
     * status.
     *      
     * @param fileName - The name of the file whose bytes are to be counted
     * @return the number of bytes in the file, or -1 if there was an error 
     *          in opening and/or reading the file.
     */
    public int countBytes(String fileName) {
        int bytes = 0;
        try {
            FileInputStream fos = new FileInputStream(fileName);
            
            for (;;) {
                int currByte = fos.read();
                if (currByte == -1) {
                    break;
                } else {
                    bytes++;
                }
            }
            fos.close();
            return bytes;
        } catch (Exception e) {
            lastMessage = e.getMessage();
            return -1;
        }        
    }

    /**
     * Copies the contents of a source file to a second file.
     *
     * The file whose name is given by 'outFileName' is created; the contents
     * of the 'inFileName' file are copied into it.  The object's message-
     * string is changed to reflect the status of the operation.  If a file
     * already exists by that name, it is deleted.
     *
     * @param srcName - The name of the source file.
     * @param dstName - The name of the new file to create and copy the contents of the source file to.
     */
    public void copyFile(String srcName, String dstName) {
        if (srcName.equals(dstName)) {
            lastMessage = "Cannot Copy Identical Files!";
            return;
        }
    
        
        try {
            FileInputStream fis = new FileInputStream(srcName);
            FileOutputStream fos = new FileOutputStream(dstName);
                
            for (;;) {
                int currByte = fis.read();
                if (currByte == -1) {
                    break;
                } else {
                    fos.write(currByte);
                }
            }
            
            fis.close();
            fos.close();
            lastMessage = srcName + " copied to " + dstName;
        } catch (Exception e) {
            lastMessage = e.getMessage();
        }
    }    

    /**
     * Appends a string to a file.
     *
     * @param str - the string to append
     * @param fileName - the name of the file that the string should be appended to.
     */
    public void appendString(String str, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName, true);
            PrintStream ps = new PrintStream(fos);
            
            ps.println(str);
            
            ps.close();
            lastMessage = fileName + " successful append!";
        } catch (Exception e) {
            lastMessage = e.getMessage();
        }
    }
    
    /**
     * Copies the contents of a source file to a second file, except upper
     * and lowercase letters are swapped.
     *
     * The file whose name is given by 'outFileName' is created; the contents
     *      * of the 'inFileName' file are copied into it.  The object's message-
     *      * string is changed to reflect the status of the operation.  If a file
     *      * already exists by that name, it is deleted.
     *
     * @param srcName - The name of the source file.
     * @param dstName - The name of the new file to create and copy the contents of the source file to.
     */
    public void copyFileInvertCase(String srcName, String dstName) {
        if (srcName.equals(dstName)) {
            lastMessage = "Cannot Copy Identical Files!";
            return;
        }
    
        
        try {
            FileInputStream fis = new FileInputStream(srcName);
            FileOutputStream fos = new FileOutputStream(dstName);
                
            for (;;) {
                int currByte = fis.read();
                if (currByte == -1) {
                    break;
                } else {
                    if (Character.isUpperCase(currByte)) {
                        currByte = Character.toLowerCase(currByte);
                    } else if (Character.isLowerCase(currByte)) {
                        currByte = Character.toUpperCase(currByte);
                    }
                    fos.write(currByte);
                }
            }
            
            fis.close();
            fos.close();
            lastMessage = srcName + " copied and inverted into " + dstName;
        } catch (Exception e) {
            lastMessage = e.getMessage();
        }
    }
}//class FileHandler
