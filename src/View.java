import java.awt.Button;
import java.awt.Panel;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.Label;
import java.awt.event.WindowEvent;	//for CloseListener()
import java.awt.event.WindowAdapter;	//for CloseListener()
import java.lang.Integer;		//int from Model is passed as an Integer
import java.util.Observable;		//for update();
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;	//for addController()
import java.awt.*;

public class View implements ActionListener
{
	final int numOfFunctions = 9; //how many functions will the user be able to use
	String[] functions = new String[numOfFunctions]; //list of symbols on funcButtons
	
	Model model = new Model();
	
	TextField display = new TextField("0"); //The display box of the calculator
	Button[] numButtons = new Button[11]; //The collection of number buttons
	Button[] funcButtons = new Button[numOfFunctions]; //The collection of function buttons
	
	/**
	 * The constructor for view
	 */
	View()
	{
		//Initializing functions
		functions[0] = "+";
		functions[1] = "-";
		functions[2] = "*";
		functions[3] = "/";
		functions[4] = "+/-";
		functions[5] = "1/x";
		functions[functions.length - 3] = "C";
		functions[functions.length - 2] = "CE";
		functions[functions.length-1] = "=";
		//Setting up GUI
		Frame frame = new Frame("Calculator"); //Overall pane
		GridLayout overall = new GridLayout(2,1);
		frame.setLayout(overall);
		Panel numPad = new Panel();//Holds & organizes buttons
		GridLayout buttons = new GridLayout(5,4);
		numPad.setLayout(buttons);
		
		//Initializing the nummber buttons and decimal point button
		for (int i = 0; i < 10; i++)
		{
			numButtons[i] = new Button("" + i);
			numButtons[i].addActionListener(this);
		}
		numButtons[10] = new Button(".");
		numButtons[10].addActionListener(this);
		
		//Initializing my function buttons
		for (int i = 0; i < numOfFunctions; i++)
		{
			funcButtons[i] = new Button(functions[i]);
			funcButtons[i].addActionListener(this);
		}
		
		//Adding my buttons to numPad
		//1 2 3 +
		numPad.add(numButtons[1]);
		numPad.add(numButtons[2]);
		numPad.add(numButtons[3]);
		numPad.add(funcButtons[0]);
		//4 5 6 -
		numPad.add(numButtons[4]);	
		numPad.add(numButtons[5]);
		numPad.add(numButtons[6]);
		numPad.add(funcButtons[1]);
		//7 8 9 *
		numPad.add(numButtons[7]);
		numPad.add(numButtons[8]);
		numPad.add(numButtons[9]);
		numPad.add(funcButtons[2]);
		//+/- 0 1/x /
		numPad.add(funcButtons[4]);
		numPad.add(numButtons[0]);
		numPad.add(funcButtons[5]);
		numPad.add(funcButtons[3]);
		//. C CE =
		numPad.add(numButtons[10]);
		numPad.add(funcButtons[6]);
		numPad.add(funcButtons[7]);
		numPad.add(funcButtons[functions.length - 1]);
		
		display.setEditable(false); //Don't let the user type in the display
		
		frame.add(display);
		frame.add(numPad);
		
		frame.addWindowListener(new CloseListener());
		frame.setSize(300,200);
		frame.setVisible(true);
	}
	
	/**
	 * Allows the close button to close the program
	 * @author Stefan
	 */
	public static class CloseListener extends WindowAdapter 
	{
		public void windowClosing(WindowEvent e) 
		{
			e.getWindow().setVisible(false);
			System.exit(0);
		} //windowClosing()
	} //CloseListener

	/**
	 * If a button is pressed...
	 */
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		// TODO Auto-generated method stub
		for(int i = 0; i < 11; i++)
		{	
			if(event.getSource()==numButtons[i])
			{
				model.numberPress("" + i);
			}
		}
		
		for(int i = 0; i < functions.length; i++)
		{
			if(event.getSource()==funcButtons[i])
			{
				model.functionPress(i);
			}
		}
		display.setText(model.getDisplay()); //Update the display
	}


}
