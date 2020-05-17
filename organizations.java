package se;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class organizations extends Frame
{
	Connection connection;
	Statement statement;
	Button givedetailsButton;
	TextField nameText,placeText,type_of_helpText,phone_noText;
	TextArea errorText;
	public organizations() 
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} 
		catch (Exception e) 
		{
			System.err.println("Unable to find and load driver");
			System.exit(1);
		}
		connectToDB();
	}
	
	
	public void connectToDB() 
    {
		try 
		{
		  connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ruchi","04032001");
		  statement = connection.createStatement();

		} 
		catch (SQLException connectException) 
		{
		  System.out.println(connectException.getMessage());
		  System.out.println(connectException.getSQLState());
		  System.out.println(connectException.getErrorCode());
		  System.exit(1);
		}
    }
	
	public void buildGUI() 
	{		
		//Handle Insert Block Button nameText,placeText,type_of_helpText,phone_noText
		givedetailsButton = new Button("Submit");
		givedetailsButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
				  String query= "INSERT INTO organization VALUES(" +"'"+ nameText.getText() + "','" + placeText.getText() + "','"+ type_of_helpText.getText() +"','" +phone_noText.getText() +"')";
				  int i = statement.executeUpdate(query);
				  errorText.append("\nInserted " + i + " rows successfully");
				} 
				catch (SQLException insertException) 
				{
				  	displaySQLErrors(insertException);
				}
			}
		});

	
		nameText = new TextField(15);
		placeText = new TextField(15);
		type_of_helpText = new TextField(15);
		phone_noText= new TextField(15);
		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("org. name:"));
		first.add(nameText);
		first.add(new Label("Place:"));
		first.add(placeText);
		first.add(new Label("Type_of_help:"));
		first.add(type_of_helpText);
		first.add(new Label("phone_No:"));
		first.add(phone_noText);
		
		first.setBounds(125,90,200,100);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(givedetailsButton);
        second.setBounds(125,220,150,100);         
		
		Panel third = new Panel();
		third.add(errorText);
		third.setBounds(125,320,300,200);
		
		setLayout(null);

		add(first);
		add(second);
		add(third);
	    
		setTitle("Give organization Details");
		setSize(500, 600);
		setVisible(true);
	}

	private void displaySQLErrors(SQLException e) 
	{
		errorText.append("\nSQLException: " + e.getMessage() + "\n");
		errorText.append("SQLState:     " + e.getSQLState() + "\n");
		errorText.append("VendorError:  " + e.getErrorCode() + "\n");
	}

	public static void main(String[] args) 
	{
		organizations sail = new organizations();

		sail.addWindowListener(new WindowAdapter(){
		  public void windowClosing(WindowEvent e) 
		  {
			System.exit(0);
		  }
		});
		
		sail.buildGUI();
	}
}
