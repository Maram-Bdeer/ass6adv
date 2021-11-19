# ass6adv

# Code-refectory


Advanced software development
Homework Assignment # 6
*******************************************************************************************

Refactory code rules I used in this code
******************************************************************************************

1- I changed the names of the incomprehensible variables and method to names that are understandable 
and clear to the reader example e to event ,ans to result ,fr to read file ... etc 

2- The fictitious names are verbs so that the first letter is lowercase and
  the second letter is uppercase, such as buildMenu ... etc 

3- Variables are names and all of them are lowercase and are private 
 inside the class like changed , selectall .. etc 

4- I had more than one similar code I made a method and put the code in it and 
then I called it like saveFile();  This is called Extract Method

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
    
    One of its benefits is to reduce the size of the code and reduce repetition  in it so that
     it is more understandable and the code is tidy and easy to read
    
    Also the code for printWriter()
    
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


I used it in two methods which are actionPerformed()
and newFile()
to reduce the code and make it more clear


5- I deleted the unnecessary comment because it was replaced by the name of the variables
and the way is clear and intuitive so it was dispensed with.

The saveFileAction method was used in the actionPerformed() method

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

This is because the method is very long and to be clearer and easier to modify
