import java.util.Scanner;
import javax.swing.JFrame;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class JavaAPI {
    
    public static void main(String[] args) {
        String id1 = "1244-AAAA-10267485";
        String id2 = "3843-AAAF-92715284";
    
        boolean matching = id1.regionMatches(true, 6, id2, 6, 4);
        
        if (matching) {
            System.out.println("Matching!");
        } else {
            System.out.println("Not Matching!");
        }
    }
    
    
    public JTextArea currentTxt;
    JButton clearButton = new JButton("Clear");
    JButton appendButton = new JButton("Append");
    
    public JavaAPI(String title) {
        JFrame frame = new JFrame(title);
        
        // Class Window
        frame.setVisible(true);
        
        frame.setBounds(0, 0, 300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        layout(frame);
    }
    
    public static void main(String[] args) {
        
        JavaAPI window = new JavaAPI("Fresh Green Beans");
        
        
        
        // Scanner scnr = new Scanner(System.in);
        
        // System.out.print("Email 1: ");
        // String email1 = scnr.nextLine();
        
        // System.out.print("Email 2: ");
        // String email2 = scnr.nextLine();
        
        // if (email1.endsWith(".edu") && email2.endsWith(".edu")) {
            // System.out.println("Both School Emails!");
        // } else {
            // System.out.println("Not Both School Email!");
        // }
    }
    
    private void layout(JFrame jframe) {
        FlowLayout template = new FlowLayout();
        
        jframe.setLayout(template);
        
        
        clearButton.addActionListener(this);
        appendButton.addActionListener(this);
        
        
        String initialTxt = "I love to eat fresh green beans!";
        JTextArea txtArea = new JTextArea(initialTxt, 5, 25);
        
        jframe.add(clearButton);
        jframe.add(txtArea);
        jframe.add(appendButton);
        
        currentTxt = txtArea;
    }
    
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == clearButton) {
            currentTxt.setText("");
        } else if (event.getSource() == appendButton) {
            currentTxt.setText(currentTxt.getText() + " Yum!");
        }
    }
}