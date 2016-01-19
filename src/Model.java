
/**
 * @author Stefan
 *
 */
/**
 * @author Stefan
 *
 */
public class Model extends java.util.Observable
{
	private String operand,operatee,inprogress,display;
	int operation; //Numeric representation of the latest operation
	final int eqPlace = 8; //What operation # is equals?
	final int[] oneOpFuncs = new int[] {4,5}; //List of all operation # associated with one # functions
	
	/**
	 * Constructor for my model
	 */
	Model()
	{
		operand=operatee=inprogress="";
		operation = -1;
		display = "0";
	}
	
	/**
	 * Getter for the value the gui will display
	 * @return the value of display
	 */
	public String getDisplay()
	{
		return display;
	}
	
	/**
	 * Is the current function one that only needs one function?
	 * @return is it?
	 */
	private boolean oneNum()
	{
		boolean temp = false;
		for(int i = 0; i < oneOpFuncs.length; i++)
		{
			if(operation == oneOpFuncs[i])
				temp = true;
		}
		return temp;
	}
	
	/**
	 * Is the passed operation a one num function?
	 * @param op the numeric representation of the function in question
	 * @return
	 */
	private boolean oneNum(int op)
	{
		boolean temp = false;
		for(int i = 0; i < oneOpFuncs.length; i++)
		{
			if(op == oneOpFuncs[i])
				temp = true;
		}
		return temp;
	}
	
	/**
	 * If a number button is pressed, this function is called
	 * @param value which number was pressed?
	 */
	public void numberPress(String value)
	{
		if(operation == eqPlace || oneNum()) //If the last operation entered was a 1 # function or "="
		{
			Clear(); //Start a fresh calculation
		}
		if(value.equals("10")) //Add decimal point iff no other decimal point exists
		{
			if(inprogress.equals(""))//If inprogress is empty, start with "0."
				inprogress = "0.";
			if(!inprogress.contains(".")) //If no other ".", add one
				inprogress = inprogress + ".";
		}
		else
			inprogress = inprogress + value; //Add the newly inputted val onto inprogress
		display = inprogress; //Update the display value		
	}
	
	/**
	 * If a function button is pressed, this function is called
	 * @param oper which function was pressed?
	 */
	public void functionPress(int oper)
	{
		boolean iP = false; //If, a 1 # func being pressed, does the result get passed into inprogress?
		//If the function executes immediately, namely 1 # functions or clears...
		if(oneNum(oper) || oper == (eqPlace - 1) || oper == (eqPlace -2))
		{
			//If it's a 1 number operation, execute it return the value to where it came from
			if(oneNum(oper))
			{
				//If there isn't any current user input...
				if(inprogress.equals("")) 
				{
					//If no input at all yet, use 0
					if(operand.equals(""))
					{
						operand = oneNumFunctions(oper, "0");
					}
					//Otherwise use operand
					else
						operand = oneNumFunctions(oper, operand);
				}
				//Otherwise, perform the operation on the current user input
				else
				{
					iP = true;
					inprogress = oneNumFunctions(oper, inprogress);
				}
			}
			//If the clear button is pressed...
			else if(oper == (eqPlace - 2))
			{
				Clear();
			}
			//Otherwise the clearentry button pressed
			else
			{
				clearEntry();
			}
		}
		//If there is no value in operand...
		else if(operand.equals(""))
		{
			if(operation == -1) //If it is the first function to be entered in this calc...
			{
				if(operand.equals("")) //If there is no stored number...
				{
					if(inprogress.equals("")) //If there is no user input yet...
						operand = "0"; //use 0 as the default
					else //Otherwise set the user input to be operand
					{
						operand = inprogress; //Store the user input
						inprogress = ""; //Reset the user input
					}
				}
			}
		}
		else //If you have an operation, and an operand... 
		{
				operatee = inprogress;
				inprogress = "";
				executeFunction();
				operatee = "";
		}

		//If it's a one operation, set the result to the display
		if(oneNum(oper))
		{
			if(iP)
			{
				display = inprogress;
			}
			else
				display = operand;
		}
		//If the clear or clear entry button is pressed, set the display to 0
		else if(oper == (eqPlace - 1) || oper == (eqPlace - 2))
		{
			display = "0";
		}
		//Otherwise update operation and set display to the result
		else
		{
			display = operand;
			operation = oper;
		}	
	}

	/**
	 * Reset model
	 */
	public void Clear()
	{
		operand=operatee=inprogress="";
		operation = -1;
		display = "0";
	}
	
	/**
	 * If a one # function is pressed, this determines which function to call 
	 * @param op the operation to be executed
	 * @param val The (hopefully) numeric value to be operated on
	 * @return The result of the operation
	 */
	String oneNumFunctions(int op, String val)
	{
		if(op == 4)
			return negate(val);
		else if(op == 5)
			return invert(val);
		
		return "Invalid selection";
	}
	
	/**
	 * Multiplies a passed number by -1
	 * @param val The passed number
	 * @return The result
	 */
	private String negate(String val)
	{
		try
		{
			return ""+(Double.parseDouble(val) * -1);
		}
		catch(NumberFormatException e)
		{
			return "Not a number.";
		}
	}
	
	/**
	 * Takes passed number^-1
	 * @param val the passed number
	 * @return the result
	 */
	private String invert(String val)
	{
		double temp;
		if(isNumber(val))
		{
			temp = Double.parseDouble(val);
			if(temp == 0)
				return "Error: Divide by 0.";
			else
				return "" + (1/temp);
		}
		else
			return "Not a number";
	}
	
	/**
	 * Calls the appropriate function
	 */
	private void executeFunction()
	{
		switch(operation)
		{
			case 0: add();
					break;
			case 1: subtract();
					break;
			case 2: multiply();
					break;
			case 3: divide();
					break;
			case 6: Clear();
					break;
			case 7: clearEntry();
					break;
		}
	}
	
	/**
	 * Clears the current entry
	 */
	private void clearEntry()
	{
		inprogress = "";
	}
	
	/**
	 * Adds operand and operatee together
	 */
	private void add()
	{
		try
		{
			operand = "" + (Double.parseDouble(operand)+Double.parseDouble(operatee));
		}
		catch(NumberFormatException e)
		{
			operand = "Not a number.";
		}
	}

	/**
	 * Subtracts operatee from operand
	 */
	private void subtract()
	{
		try
		{
			operand = "" + (Double.parseDouble(operand)-Double.parseDouble(operatee));
		}
		catch(NumberFormatException e)
		{
			operand = "Not a number.";
		}
	}
	
	/**
	 * Multiplies operand by operatee
	 */
	private void multiply()
	{
		try
		{
			operand = "" + (Double.parseDouble(operand)*Double.parseDouble(operatee));
		}
		catch(NumberFormatException e)
		{
			operand = "Not a number.";
		}
	}

	/**
	 * Divides operand by operatee
	 */
	private void divide()
	{
		try
		{
			if(Double.parseDouble(operatee) != 0)
				operand = "" + (Double.parseDouble(operand)/Double.parseDouble(operatee));
			else
				operand = "Error: divide by 0";
		}
		catch(NumberFormatException e)
		{
			operand = "Not a number.";
		}
	}
	
	/**
	 * Is the passed string convertable to a double?
	 * @param op passed number
	 * @return Is it a number?
	 */
	//Is the character input convertable to a number
	private boolean isNumber(String op)
	{
		try
		{
			Double.parseDouble(op);
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
	}

}