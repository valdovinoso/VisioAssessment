package com.visiolending;

import java.util.List;

import com.visiolending.rules.Person;
import com.visiolending.rules.Product;
import com.visiolending.rules.Rule;

public class RulesEngine {

	/**
	 *  Rules engine driven by a JSON file.
	 * @param product
	 * @param person
	 * @param rules
	 * @return
	 */
	public static Product runRules(Product product, Person person, List<Rule> rules) {
		
		try {
			// counter to verify if all rules were considered.
			int numOfRules = rules.size();
			
			for (Rule rule : rules) {
				// rule variables
				String parameter = rule.getParameter();
				String[] params = parameter.split(" ");
				String noun = params[0];
				String adj = parameter.substring(parameter.indexOf(" ")).trim();
				String condition = rule.getCondition();
				String value = rule.getValue();
				String action = rule.getAction();
				
				switch (noun) {
					// PRODUCT section of the JSON File	
					case "product": 
						switch  (adj) {
							case "name": 
								switch (condition) {
									case "equals":
										if (product.getName().equalsIgnoreCase(value)) {
											doAction(action, product);
										}
										numOfRules--;
										break;
								}
								break;
								// enhancement 1.0
							case "term year": 
								switch (condition) {
									case "greater than":
										if (product.getTerm() > Integer.valueOf(value)) {
											doAction(action, product);
										}
										numOfRules--;
										break;
								}
								// end of enhancement
							}
						break;
					// PERSON section of the JSON File	
					case "person's": 
						switch  (adj) {
							case "credit score": 
								switch (condition) {
									case "equals or greater than":
										if (person.getCreditScore() >= Integer.valueOf(value)) {
											doAction(action, product);
										}
										numOfRules--;
										break;
									case "less than":
										if (person.getCreditScore() < Integer.valueOf(value)) {
											doAction(action, product);
										}
										numOfRules--;
										break;
								}
								break;
							case "state":
								switch (condition) {
									case "equals":
										if (person.getState().equalsIgnoreCase(value)) {
											doAction(action, product);
										}
										numOfRules--;
										break;
								}
								break;
							}
						break;
				}
			} // end of for in loop
			
			// Verify every rule ran to avoid presenting incorrect information
			if(0 < numOfRules) {
				product.setRanAllRules(false);
			}
		} catch (Exception e) {
			//TODO log full stack trace
			product.setRanAllRules(false);
		}
		
		return product;
	}

	/**
	 *  This function will perform the action based on the wording in a JSON file.
	 * @param action
	 * @param product
	 */
	private static void doAction(String action, Product product) {
		String[] params = action.split(" ");
		String targetParamName = params[0];
		// get the last two words of the action to determine action
		if (params.length > 1) {
			targetParamName = params[params.length-2] + params[params.length-1];
		}
		switch (targetParamName) {
			case "interestrate":
				String operation = params[0];
				Double amount = Double.valueOf(params[1]);
				setInterestRate(product, operation, amount);
				break;
			case "disqualified":
				product.setDisqualified(true);
				break;
			default:
				product.setRanAllRules(false);
		}
	}

	/**
	 * This function will manipulate the interest rate and build the formula to display.
	 * @param product
	 * @param operation
	 * @param amount
	 */
	private static void setInterestRate(Product product, String operation, Double amount) {
		Double currentRate = product.getInterestRate();
		
		switch (operation) {
			case "add":
				product.setInterestRate(currentRate + amount);
				product.setRateFormula(" + ");
				product.setRateFormula(amount.toString());
				break;
			case "reduce":
				product.setInterestRate(currentRate - amount);
				product.setRateFormula(" - ");
				product.setRateFormula(amount.toString());
				break;
			case "subtract":
				product.setInterestRate(currentRate - amount);
				product.setRateFormula(" - ");
				product.setRateFormula(amount.toString());
				break;
			default:
				product.setRanAllRules(false);
		}
	}
	
}
