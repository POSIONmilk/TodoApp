import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class gui implements ActionListener{
    public JButton add, current, remove, help, home, submit, save;
    public JTextArea title, body;
    public JTextArea lab_head,lab_body;
    public Queue temp;



    public gui() throws IOException, ClassNotFoundException {

        parseEvent();

        JFrame frame = new JFrame();//Frame of the GUI
        frame.setLayout(null);
        JPanel panel = new JPanel();
        frame.setResizable(false);//Set the window of the reminder to be not resizable.

        panel.setLayout(null);
        frame.setBounds(0,0,600,400);//Set size of the window
        panel.setBounds(0,0,600,400);
        panel.setBackground(new Color(153, 255, 214));//Set Background color for the window

        add = new JButton("Add");//Button for the "add" function. This declares the variable
        add.setBounds(20, 10 , 70, 30);
        add.addActionListener(this);
        current = new JButton("Current");//Declaring buttons
        current.setBounds(100, 10 , 100, 30);
        current.addActionListener(this);
        remove = new JButton("Finish");//Declaring buttons
        remove.setBounds(210, 10 , 100, 30);
        remove.addActionListener(this);
        help = new JButton("Help");//Declaring buttons
        help.setBounds(320, 10 , 70, 30);
        help.addActionListener(this);
        home = new JButton("Home");//Declaring buttons
        home.setBounds(400,10,70,30);
        home.addActionListener(this);
        submit = new JButton("Submit");//Declaring buttons
        submit.setBounds(470,290,100,30);
        submit.addActionListener(this);
        save = new JButton("Save");
        save.setBounds(500,10,70,30);
        save.addActionListener(this);


        lab_head = new JTextArea();//Text Area for displaying the title of the task/ name of the task
        lab_head.setBounds(20,50,550,20);
        lab_head.setLineWrap(true);
        lab_head.setEditable(false);
        lab_head.setBackground(new Color(153, 255, 214));//
        lab_body = new JTextArea();//Text Area for displaying the description of the task, also to display any important message that the program want to send to the Users.
        lab_body.setBounds(20,80,550,200);
        lab_body.setLineWrap(true);
        lab_body.setEditable(false);
        lab_body.setBackground(new Color(153, 255, 214));
        lab_body.setText("Welcome the EG reminder, click help to learn more about the functions.");

        frame.add(panel);//Adding elements to the panel.
        panel.add(add);
        panel.add(current);
        panel.add(remove);
        panel.add(help);
        panel.add(home);
        panel.add(submit);
        panel.add(lab_body);
        panel.add(save);

        title = new JTextArea();//Textareas for user inputs.
        title.setBounds(20,50 , 550, 20);
        title.setLineWrap(true);
        body = new JTextArea();
        body.setBounds(20,80,550, 200);
        body.setLineWrap(true);

        panel.add(title);//Adding more elements to the panel.
        panel.add(body);
        panel.add(lab_body);
        panel.add(lab_head);

        empty();
        frame.setVisible(true);
        lab_body.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void empty(){
        title.setVisible(false);
        body.setVisible(false);
        submit.setVisible(false);
        lab_head.setVisible(false);
        lab_body.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==add) {//Toggles a different page
            empty();
            title.setVisible(true);
            body.setVisible(true);
            submit.setVisible(true);
            title.setText("");//Clear the page
            body.setText("");
        } else if(e.getSource() == home){//clear the page amd return to default.
            empty();
            lab_body.setText("Welcome the EG reminder, click help to learn more about the functions.");
            lab_body.setVisible(true);
        } else if(e.getSource() == remove){
            if (temp.isEmpty()){
                empty();
                lab_body.setVisible(true);
                lab_body.setText("There is nothing to do right now.");
            }else{
                empty();
                lab_body.setVisible(true);
                temp.dequeueTask();
                lab_body.setText("Current task finished.");
            }
        } else if(e.getSource() == submit){//Add stuff the queues.
            if(title.getText().isEmpty()||body.getText().isEmpty()){
                empty();
                lab_head.setVisible(true);
                lab_head.setText("Invalid input.");
            }else {
                empty();
                Task added =  new Task(title.getText(), body.getText());
                temp.enqueueTask(added);
                lab_body.setVisible(true);
                lab_body.setText("Plan added.");

            }
        } else if(e.getSource() == help){
            lab_body.setVisible(true);
            lab_body.setText("Welcome to the EG (Stand for easy to use, good for life) reminder. In this reminder, you will receive the premium experience that will help you to go through the tough days of working and studying. By clicking the “Add” button, you can toggle the page to add task for plans in the future. The remove button is for you to remove after you finish the tasks.");
        } else if(e.getSource() == current){
            if(temp.isEmpty()){
                empty();
                lab_body.setVisible(true);
                lab_body.setText("There is no task that is lining up. Go have fun.");
            }else{
                empty();
                lab_head.setVisible(true);
                lab_body.setVisible(true);
                Task curr = temp.currTask();

                lab_head.setText(curr.getTitle());
                lab_body.setText(curr.getDescription());
            }
        }else if(e.getSource() == save){
            System.out.println(this.temp.currTask().getTitle());
            System.out.println(this.temp.currTask().getDescription());
            try {
                saveEventQueue();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    public void parseEvent() throws IOException,ClassNotFoundException {
        String FileName = "Event.txt";
        File newFile = new File(FileName);
        this.temp = new Queue();
        if (newFile.createNewFile()){
            System.out.println("File is creted");
        }else {
            BufferedReader buffRead = new BufferedReader(new FileReader(FileName));

            while(buffRead.ready()){
                buffRead.readLine();
                String title = buffRead.readLine();
                String Description = buffRead.readLine();
                temp.enqueueTask(new Task(title,Description));
            }
        }

    }

    public void saveEventQueue( ) throws IOException {
        this.temp = new Queue();
        FileWriter myWriter = new FileWriter("Event.txt");
        while(!this.temp.isEmpty()){
            myWriter.write(this.temp.currTask().getTitle()+ "\n");
            myWriter.write(this.temp.currTask().getDescription()+"\n");
            System.out.println(this.temp.currTask().getTitle());
            System.out.println(this.temp.currTask().getDescription());
            this.temp.dequeueTask();
        }
        myWriter.close();
    }


}

