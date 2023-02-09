import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.*;

public class SwingControlDemo implements ActionListener {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JTextArea statusLabel;
    private JPanel controlPanel;
    private JPanel topPanel;

    private JMenuBar mb;
    private JMenu file, edit, help;
    private JMenuItem cut, copy, paste, selectAll;
    private JTextArea url;
    private int WIDTH=800;
    private int HEIGHT=700;
    private JTextArea search;
    private JTextArea results;
    private JLabel emptySpace;
    private JScrollPane scrollPane;




    public SwingControlDemo() {
        prepareGUI();
    }

    public static void main(String[] args) {
        SwingControlDemo swingControlDemo = new SwingControlDemo();
        swingControlDemo.showEventDemo();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new GridLayout(3, 1));

        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 3));
        cut = new JMenuItem("cut");
        copy = new JMenuItem("copy");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("selectAll");
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);

        mb = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        mb.add(file);
        mb.add(edit);
        mb.add(help);

        url = new JTextArea("url");
     //   url.setBounds(50, 5, WIDTH-100, HEIGHT-50);
        emptySpace = new JLabel("space");
        search = new JTextArea("search term");
      //  url.setBounds(50, 5, WIDTH-100, HEIGHT-50);



        mainFrame.add(mb);
        topPanel.add(url);
        topPanel.add(emptySpace);
        topPanel.add(search);

        mainFrame.setJMenuBar(mb);
        mainFrame.add(topPanel);

        headerLabel = new JLabel("", JLabel.CENTER);
        results = new JTextArea();
        results.setSize(350, 100);

        scrollPane = new JScrollPane(results);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);




        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

       // mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(scrollPane);
        mainFrame.setVisible(true);
    }

    public void htmlRead(){

        try{
            System.out.println();
            URL url1 = new URL(url.getText());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url1.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.contains("href")  && line.contains("http") ) {

                    int start = line.indexOf("http");
                    System.out.println("og"+line);

                    int end = start;
                    int end2 = line.indexOf("\"", start+4);
                    int end1 = line.indexOf("\'", start+4);
                    System.out.println("1:" +end1 +" 2: " +end2);

                    if(end1 == -1 && end2 == -1){

                        end = start;

                    } else if( end1 == -1)
                    {
                        end = end2;
                    } else if (end2 == -1){
                        end = end1;
                    } else if(end1< end2){
                        end = end1;
                    } else{
                        end = end2;
                    }



                    String newLine = line.substring(start, end);
                    if(newLine.contains(search.getText())) {
                        System.out.println(newLine);
                        results.append("\n" + newLine);
                    }
                }
            }
            reader.close();
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    private void showEventDemo() {
        headerLabel.setText("Control in action: Button");

        JButton okButton = new JButton("OK");
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        okButton.setActionCommand("OK");
        submitButton.setActionCommand("Submit");
        cancelButton.setActionCommand("Cancel");

        okButton.addActionListener(new ButtonClickListener());
        submitButton.addActionListener(new ButtonClickListener());
        cancelButton.addActionListener(new ButtonClickListener());

        controlPanel.add(okButton);
        controlPanel.add(submitButton);
        controlPanel.add(cancelButton);

        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cut)
            url.cut();
        if (e.getSource() == paste)
            url.paste();
        if (e.getSource() == copy)
            url.copy();
        if (e.getSource() == selectAll)
            url.selectAll();
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("OK")) {
                results.setText("Ok Button clicked.");
                htmlRead();
            } else if (command.equals("Submit")) {
                results.setText("Submit Button clicked.");
            } else {
                results.setText("Cancel Button clicked.");
            }
        }
    }
}