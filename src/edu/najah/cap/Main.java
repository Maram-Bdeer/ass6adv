package edu.najah.cap;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
class Editor extends JFrame implements ActionListener, DocumentListener {

    public static  void main(String[] args) {new Editor();}

    public JEditorPane textpanel;
    private JMenuBar menu;//Menu
    private JMenuItem copy, paste, cut, move;
    private boolean changed = false;
    private File file;

    public Editor() {
        //Editor the name of our application
        super("Editor ");
        textpanel = new JEditorPane();
        // center means middle of container.
        add(new JScrollPane(textpanel), "Center");
        textpanel.getDocument().addDocumentListener(this);

        menu = new JMenuBar();
        setJMenuBar(menu);
        buildMenu();
        //The size of window
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void buildMenu() {
        buildFileMenu();
        buildEditMenu();
    }

    private void buildFileMenu() {
        JMenu file = new JMenu("File");
        file.setMnemonic('F');
        menu.add(file);
        JMenuItem newitem = new JMenuItem("New");
        newitem.setMnemonic('N');
        JMenuItem open = new JMenuItem("Open");
        file.add(open);
        open.addActionListener(this);
        open.setMnemonic('O');
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        JMenuItem save = new JMenuItem("Save");
        file.add(save);
        save.setMnemonic('S');
        save.addActionListener(this);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        JMenuItem saveas = new JMenuItem("Save as...");
        saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
        file.add(saveas);
        saveas.addActionListener(this);
        JMenuItem quit = new JMenuItem("Quit");
        file.add(quit);
        quit.addActionListener(this);
        quit.setMnemonic('Q');
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
    }

    private void buildEditMenu() {
        JMenu edit = new JMenu("Edit");
        menu.add(edit);
        edit.setMnemonic('E');
        // cut
        cut = new JMenuItem("Cut");
        cut.addActionListener(this);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        cut.setMnemonic('T');
        edit.add(cut);
        // copy
        copy = new JMenuItem("Copy");
        copy.addActionListener(this);
        copy.setMnemonic('C');
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        edit.add(copy);
        // paste
        paste = new JMenuItem("Paste");
        paste.setMnemonic('P');
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        edit.add(paste);
        paste.addActionListener(this);
        //move

		move = new JMenuItem("Move");
		move.setMnemonic('M');
		move.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		edit.add(move);
		move.addActionListener(this);

        // find
        JMenuItem find = new JMenuItem("Find");
        find.setMnemonic('F');
        find.addActionListener(this);
        edit.add(find);
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        // select all
        JMenuItem selectall = new JMenuItem("Select All");
        selectall.setMnemonic('A');
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        selectall.addActionListener(this);
        edit.add(selectall);
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();
        if (action.equals("Quit")) {
            System.exit(0);
        } else if (action.equals("Open")) {
            loadFile();
        } else if (action.equals("Save")) {
            //Save file
            saveFileAction();

        } else if (action.equals("New")) {
            newFile();
        }
        else if (action.equals("Save as...")) {
            saveAs("Save as...");
        } else if (action.equals("Select All")) {
            textpanel.selectAll();
        } else if (action.equals("Copy")) {
            textpanel.copy();
        } else if (action.equals("Cut")) {
            textpanel.cut();
        } else if (action.equals("Paste")) {
            textpanel.paste();
        } else if (action.equals("Find")) {
           /* FindDialog find = new FindDialog(this, true);
            find.showDialog();*/
        }
    }

    private void saveFileAction() {
        int result = 0;
        if (changed) {
            // 0 means yes and no option, 2 Used for warning messages.
            result = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file", 0, 2);
        }
        //1 value from class method if NO is chosen.
        if (result != 1) {
            if (file == null) {
                saveAs("Save");
            } else {
                printWriter();

            }
        }
    }

    private void printWriter() {

        String text = textpanel.getText();
        System.out.println(text);
        try (PrintWriter writer = new PrintWriter(file);){
            if (!file.canWrite())
                throw new Exception("Cannot write file!");
            writer.write(text);
            changed = false;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void newFile() {
        if (changed) {
            //Save file
            if (changed) {
                // 0 means yes and no option, 2 Used for warning messages.
                int result = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",
                        0, 2);
                //1 value from class method if NO is chosen.
                if (result == 1)
                    return;
            }
            if (file == null) {
                saveAs("Save");
                return;
            }
            printWriter();

        }
        file = null;
        textpanel.setText("");
        changed = false;
        setTitle("Editor");
    }

    private void loadFile() {
        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
        dialog.setMultiSelectionEnabled(false);
        try {
            int result = dialog.showOpenDialog(this);

            if (result == 1)//1 value if cancel is chosen.
                return;
            if (result == 0) {// value if approve (yes, ok) is chosen.
                saveFile();
            }
        } catch (Exception event) {
            event.printStackTrace();
            //0 means show Error Dialog
            JOptionPane.showMessageDialog(null, event, "Error", 0);
        }
    }

    private void saveFile() {
        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
        dialog.setMultiSelectionEnabled(false);
        if (changed){
            //Save file
            if (changed) {
                int ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",
                        0, 2);//0 means yes and no question and 2 mean warning dialog
                if (ans == 1)// no option
                    return;
            }
            if (file == null) {
                saveAs("Save");
                return;
            }
            String text = textpanel.getText();
            System.out.println(text);
            try (PrintWriter writer = new PrintWriter(file);){
                if (!file.canWrite())
                    throw new Exception("Cannot write file!");
                writer.write(text);
                changed = false;
            } catch (Exception event) {
                event.printStackTrace();
            }
        }
        file = dialog.getSelectedFile();
        //Read file
        StringBuilder readstring = new StringBuilder();
        try (	FileReader fileread = new FileReader(file);
                 BufferedReader reader = new BufferedReader(fileread);) {
            String line;
            while ((line = reader.readLine()) != null) {
                readstring.append(line + "\n");
            }
        } catch (IOException event) {
            event.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot read file !", "Error !", 0);//0 means show Error Dialog
        }

        textpanel.setText(readstring.toString());
        changed = false;
        setTitle("Editor - " + file.getName());
    }

    private void saveAs(String dialogTitle) {
        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
        dialog.setDialogTitle(dialogTitle);
        int result = dialog.showSaveDialog(this);
        if (result != 0)//0 value if approve (yes, ok) is chosen.
            return;
        file = dialog.getSelectedFile();
        try (PrintWriter writer = new PrintWriter(file);){
            writer.write(textpanel.getText());
            changed = false;
            setTitle("Editor - " + file.getName());
        } catch (FileNotFoundException event) {
            event.printStackTrace();
        }
    }

    private void saveAsText(String dialogTitle) {
        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
        dialog.setDialogTitle(dialogTitle);
        int result = dialog.showSaveDialog(this);
        if (result != 0)//0 value if approve (yes, ok) is chosen.
            return;
        file = dialog.getSelectedFile();
        try (PrintWriter writer = new PrintWriter(file);){
            writer.write(textpanel.getText());
            changed = false;
            setTitle("Save as Text Editor - " + file.getName());
        } catch (FileNotFoundException event) {
            event.printStackTrace();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent event) {
        changed = true;
    }

    @Override
    public void removeUpdate(DocumentEvent event) {
        changed = true;
    }

    @Override
    public void changedUpdate(DocumentEvent event) {
        changed = true;
    }

}