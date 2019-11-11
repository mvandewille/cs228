package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 *  
 * @author Max Van de Wille
 *
 */

/**
 * 
 * This class evaluates input infix and postfix expressions. 
 *
 */

import java.util.HashMap;
import java.util.Scanner;

import org.omg.CosNaming._BindingIteratorImplBase;

/**
 * 
 * @author Max Van de Wille
 *
 */

public class InfixPostfix 
{

	/**
	 * Repeatedly evaluates input infix and postfix expressions.  See the project description
	 * for the input description. It constructs a HashMap object for each expression and passes it 
	 * to the created InfixExpression or PostfixExpression object. 
	 *  
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws UnassignedVariableException 
	 * @throws ExpressionFormatException 
	 **/
	public static void main(String[] args) throws FileNotFoundException, ExpressionFormatException, UnassignedVariableException 
	{
		System.out.println("Evaluation of Infix and Postfix Expressions");
		System.out.println("keys: 1 (standard input)  2 (file input)  3 (exit)");
		System.out.println("(Enter \"I\" Before an infix expression, \"P\" before a postfix expression)");
		int trial = 1;
		HashMap<Character, Integer> setHash = new HashMap<>();
 		String expression;
		char flag;
		while (true)
		{
			System.out.print("Trial " + trial + ": ");
			Scanner inputScan = new Scanner(System.in);
			int keyInput = Integer.parseInt(inputScan.nextLine());
			if (keyInput == 1)
			{
				System.out.print("Expression: ");
				expression = inputScan.nextLine();
				flag = expression.charAt(0);
				expression = expression.substring(2);
				if (flag == 'P')
				{
					ArrayList<Character> variables = new ArrayList<Character>();
 					PostfixExpression post = new PostfixExpression(expression);
					System.out.println("Postfix form: " + post.toString());
					System.out.println("where");
					for (int i = 0; i < expression.length(); i++)
					{
						char varPossible = expression.charAt(i);
						if (varPossible <= 'z' && varPossible >= 'a' && Character.isLowerCase(varPossible) && !variables.contains(varPossible))
						{
							variables.add(varPossible);
						}
					}
					if (!variables.isEmpty())
					{
						System.out.println("where");
						for (int i = 0; i < variables.size(); i ++)
						{
							char temp = variables.get(i);
							System.out.println(temp + " = ");
							int varValue = Integer.parseInt(inputScan.nextLine());
							setHash.put(temp, varValue);
						}
						post.setVarTable(setHash);
					}
					post.setVarTable(setHash);
					System.out.println("Expression value: " + post.evaluate());
				}
				else if (flag == 'I')
				{
					ArrayList<Character> variables = new ArrayList<Character>();
					InfixExpression inf = new InfixExpression(expression);
					System.out.println("Infix form: " + inf.toString());
					System.out.println("Postfix form: " + inf.postfixString());
					for (int i = 0; i < expression.length(); i++)
					{
						char varPossible = expression.charAt(i);
						if (varPossible <= 'z' && varPossible >= 'a' && Character.isLowerCase(varPossible) && !variables.contains(varPossible))
						{
							variables.add(varPossible);
						}
					}
					if (!variables.isEmpty())
					{
						System.out.println("where");
						for (int i = 0; i < variables.size(); i ++)
						{
							char temp = variables.get(i);
							System.out.println(temp + " = ");
							int varValue = Integer.parseInt(inputScan.nextLine());
							setHash.put(temp, varValue);
						}
						inf.setVarTable(setHash);
					}
					System.out.println("Expression value: " + inf.evaluate());
				}
			}
			else if (keyInput == 2)
			{
				System.out.println("Input from a file");
				System.out.print("Enter file name: ");
				String fileName = inputScan.next();
				File f = new File(fileName);
				Scanner fileScan = new Scanner(f); 
				while (fileScan.hasNextLine())
				{
					expression = fileScan.nextLine();
					if (expression.charAt(0) == 'P' || expression.charAt(0) == 'I')
					{
						flag = expression.charAt(0);
						expression = expression.substring(2);
						if (flag == 'I')
						{
							 InfixExpression infEx = new InfixExpression(expression);
								System.out.println("Infix form: " + infEx.toString());
								System.out.println("Postfix form: " + infEx.postfixString());
								System.out.println("Expression value: " + infEx.evaluate());
						}
						else if (flag == 'P')
						{
							PostfixExpression postEx = new PostfixExpression(expression);
							System.out.println("Postfix form: " + postEx.toString());
							System.out.println("Expression value: " + postEx.evaluate());
						}
					}
				}
			}
			else
			{
				break;
			}
			// TODO
			trial ++;
			System.out.println();
		}

		
	}
	// helper methods if needed
}
