package com.visiolending;

import java.util.List;

import com.visiolending.rules.Person;
import com.visiolending.rules.Product;
import com.visiolending.rules.Rule;

/**
 * This feature will takes in parameters to create a product and person. This
 * information will be run against a rules engine driven by a JSON file. It will
 * then go through each rule provided by the JSON file and output the results.
 * 
 * How to add a NEW RULE. 
 * Add a JSON object to the file, verify the wording
 * follows the rules structure. Otherwise, add new logic to the rules
 * engine. If any rules are not run do not return incorrect calculations.
 */
public class Executor {
	/*
	 * TODO: confirm with functional on how to handle user input. Should it
	 * validate before creating objects or after. Validation currently handled by in the
	 * constructor and results in a exception as a last resort.
	 */
	public static void main(String[] args) {
		// requirement
		Product product = new Product("7-1 ARM", 5.0d);
		Person person = new Person(720, "Florida");
		
		List<Rule> rules = RulesLoader.loadRules("rules.json");
		Product result = RulesEngine.runRules(product, person, rules);
		System.out.println(result.toString());
	}
}
