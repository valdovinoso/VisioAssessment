package com.visiolending.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.visiolending.RulesEngine;
import com.visiolending.RulesLoader;
import com.visiolending.rules.Person;
import com.visiolending.rules.Product;
import com.visiolending.rules.Rule;

public class RulesEngineTest {
	
	private List<Rule> testRules = null;
	private List<Integer> creditScorceList = null;
	private List<String> stateList = null;
	private List<String> badStringList = null;
	private final static String DOT = ".";
	
	@Before
    public void init() {
		creditScorceList = new ArrayList<Integer>();
		creditScorceList.add(400);
		creditScorceList.add(720);
		creditScorceList.add(800);
		
		stateList = new ArrayList<String>();
		stateList.add("Florida");
		stateList.add("Texas");
		
		badStringList = new ArrayList<String>();
		badStringList.add(null);
		badStringList.add("   ");
		
		testRules = RulesLoader.loadRules("com/visiolending/test/resources/test.json");
    }
	
	/**
	 * Test 7-1 ARM product.
	 */
	@Test
	public void run71ArmRulesTest() {
		for(String state : stateList) {
			for(Integer score : creditScorceList) {
				// create key to retrieve correct result
				StringBuilder sb = new StringBuilder();
				sb.append("71ARM").append(DOT).append(state).append(DOT).append(score).append(DOT);
				// formula
				StringBuilder sbFormula = new StringBuilder(sb).append("formula");
				String correctFormula = RulesConstants.getCorrectFormula(sbFormula.toString());
				// rate
				StringBuilder sbRate = new StringBuilder(sb).append("rate");
				Double correctRate = RulesConstants.getCorrectRate(sbRate.toString());
				// disqualification
				StringBuilder sbDisqual = new StringBuilder(sb).append("disqual");
				Boolean correctDisqual = RulesConstants.getCorrectDisqual(sbDisqual.toString());
				
				// create input data for rules engine
				Product product = new Product("7-1 ARM", 5.0d);
				Person person = new Person(score, state);
				
				// call rules and generate a formula
				String formula = runRulesEngineAndTrimFormula(product, person, testRules);

				assertTrue("Engine Result for formula: \n" + formula + " : should be " + correctFormula, 
						correctFormula.equals(formula));
				assertTrue("Engine Result for rate: \n" + product.getInterestRate() + " : should be " + correctRate, 
						correctRate.equals(product.getInterestRate()));
				assertTrue("Engine Result for disqualified: \n" + product.isDisqualified() + " : should be " + correctDisqual,
						correctDisqual.equals(product.isDisqualified()));
			}
		}
	}
	
	/**
	 * Test bad a format on one of the rules in the JSON file. For example incorrect spelled words.
	 */
	@Test
	public void runBadFormattedRulesTest() {
		List<Rule> testRulesbadFormatRules = RulesLoader.loadRules("com/visiolending/test/resources/badFormat.json");
		// create input data
		Product product = new Product("7-1 ARM", 5.0d);
		Person person = new Person(720, "Florida");
		
		runRulesEngineAndTrimFormula(product, person, testRulesbadFormatRules);
		
		assertTrue("Engine Result for bad format json: \n" + product.toString() + " : should be " + RulesConstants.ERROR_MSG,
				RulesConstants.ERROR_MSG.equals(product.toString()) );
		
	}
	
	/**
	 * Test that the engine will not display any information if a rule is missed during runtime. Example a new rule is added but never triggered.
	 */
	@Test
	public void runMissedRuleTest() {
		List<Rule> testRulesbadFormatRules = RulesLoader.loadRules("com/visiolending/test/resources/ruleMissed.json");
		// create input data
		Product product = new Product("7-1 ARM", 5.0d);
		Person person = new Person(720, "Florida");
		
		runRulesEngineAndTrimFormula(product, person, testRulesbadFormatRules);
		
		assertTrue("Engine Result for bad format json: \n" + product.toString() + " : should be " + RulesConstants.ERROR_MSG,
				RulesConstants.ERROR_MSG.equals(product.toString()) );
		
	}
	
	/**
	 * Basic single test for testing changes in your local code. 
	 */
	@Test
	public void runBasicRulesTest() {
		List<Rule> enhancementRules = RulesLoader.loadRules("com/visiolending/test/resources/enhancements.json");
		
		Product product = new Product("7-1 ARM", 5.0d, 20);
		Person person = new Person(815, "Texas");
		
		String formula = runRulesEngineAndTrimFormula(product, person, enhancementRules);
		
		assertTrue("Engine Result for rate: \n" + product.getInterestRate() + " : should be " + 4.8, 
				product.getInterestRate() == 5.7);
		assertTrue("Engine Result for formula: \n" + formula, 
				"5.0 + 0.7".equals(formula));
	}
	
	/**
	 *  Enhancement 1.0
	 *  Test the enhancement to the rules engine.
	 */
	@Test
	public void runEnhancementTest() {
		List<Rule> enhancementRules = RulesLoader.loadRules("com/visiolending/test/resources/enhancements.json");
		List<Integer> termList = new ArrayList<Integer>();
		termList.add(10);
		termList.add(20);
		
		for(Integer term : termList) {
			StringBuilder sb = new StringBuilder();
			sb.append("term").append(DOT).append(term).append(DOT);
			StringBuilder sbFormula = new StringBuilder(sb).append("formula");
			String correctFormula = RulesConstants.getCorrectFormula(sbFormula.toString());
			StringBuilder sbRate = new StringBuilder(sb).append("rate");
			Double correctRate = RulesConstants.getCorrectRate(sbRate.toString());

			Product product = new Product("7-1 ARM", 5.0d, term);
			Person person = new Person(815, "Texas");
			
			String formula = runRulesEngineAndTrimFormula(product, person, enhancementRules);
			
			assertTrue("Engine Result for formula: \n" + formula + " : should be " + correctFormula, 
					correctFormula.equals(formula));
			assertTrue("Engine Result for rate: \n" + product.getInterestRate() + " : should be " + correctRate, 
					correctRate.equals(product.getInterestRate()));
		}
		
	}
	
	/**
	 * Test when the person's credit score validation impacts the rules engine.
	 */
	@Test
	public void runBadInputPersonCreditScoreTest() {
		for(int i = 0; i < 1002; i = i + 1001) {
			Product product = new Product("7-1 ARM", 5.0d, 20);
			Person person = new Person(i, "Texas");
			
			runRulesEngineAndTrimFormula(product, person, testRules);
			
			assertTrue("Result from rule engine: \n" + product.toString() + " : should be " + RulesConstants.ERROR_MSG,
					RulesConstants.ERROR_MSG.equals(product.toString()) );
		}
	}
	
	/**
	 * Test when the person's state validation impacts the rules engine.
	 */
	@Test
	public void runBadInputPersonStateTest() {
		for(String state : badStringList ) {
			Product product = new Product("7-1 ARM", 5.0d, 20);
			Person person = new Person(720, state);
			
			runRulesEngineAndTrimFormula(product, person, testRules);
			
			assertTrue("Result from rule engine: \n" + product.toString() + " : should be " + RulesConstants.ERROR_MSG,
					RulesConstants.ERROR_MSG.equals(product.toString()) );
		}
	}
	
	/**
	 * Test when the product name validation impacts the rules engine.
	 */
	@Test
	public void runBadInputProductNameTest() {
		for(String name : badStringList ) {
			Product product = new Product(name, 5.0d, 20);
			Person person = new Person(720, "Florida");
			
			runRulesEngineAndTrimFormula(product, person, testRules);
			
			assertTrue("Result from rule engine: \n" + product.toString() + " : should be " + RulesConstants.ERROR_MSG,
					RulesConstants.ERROR_MSG.equals(product.toString()) );
		}
	}
	
	/**
	 * Test when the product starting rate validation impacts the rules engine.
	 */
	@Test
	public void runBadInputProductStartingRateTest() {
		for(int i = 4; i < 6;  i = i+2) {
			Product product = new Product("7-1 ARM", i);
			Person person = new Person(720, "Florida");
			
			runRulesEngineAndTrimFormula(product, person, testRules);
			
			assertTrue("Result from rule engine: \n" + product.toString() + " : should be " + RulesConstants.ERROR_MSG,
					RulesConstants.ERROR_MSG.equals(product.toString()) );
		}
		// TODO: this should be part of the PRODUCT test class
		Product product = new Product("7-1 ARM", 5.0d);
		assertTrue("Product rate is should be set to 5.0 for all products: ", product.getInterestRate()==5.0d);
	}
	
	/**
	 * Enhancement 1.0
	 * Test when the product term validation impacts the rules engine.
	 */
	@Test
	public void runBadInputProductTermTest() {
		List<Rule> enhancementRules = RulesLoader.loadRules("com/visiolending/test/resources/enhancements.json");
		for(int i = 0; i < 46;  i = i+46) {
			Product product = new Product("7-1 ARM", 5.0d, i);
			Person person = new Person(720, "Florida");
			
			runRulesEngineAndTrimFormula(product, person, enhancementRules);
			
			assertTrue("Result from rule engine: \n" + product.toString() + " : should be " + RulesConstants.ERROR_MSG,
					RulesConstants.ERROR_MSG.equals(product.toString()) );
		}
	}
		
	/**
	 * Run the rules engine and return the formula created by the engine.
	 * @param product
	 * @param person
	 * @param testRules
	 * @return
	 */
	private String runRulesEngineAndTrimFormula(Product product, Person person, List<Rule> testRules) {
		// run rules engine
		Product result = RulesEngine.runRules(product, person, testRules);
		// generate formula
		String formula = null;
		int startPoint = result.toString().indexOf(  "("  ) + 1;
		int endPoint = result.toString().indexOf(  ")"  );
		if(0 < startPoint && 0 < endPoint) {
			formula =  result.toString().substring(startPoint, endPoint).trim();
		}  else {
			formula = result.toString();
		}
		return formula;
	}
	
}
