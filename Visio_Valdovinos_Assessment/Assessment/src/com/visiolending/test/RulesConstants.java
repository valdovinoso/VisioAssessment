package com.visiolending.test;
/*
 * Review new entries and expected outcome with finance and testing team before check-in.
 */
public class RulesConstants {
	public final static String getCorrectFormula(String key) {
		switch (key) {
		case "71ARM.Florida.400.formula":
			return "5.0 + 0.5 + 0.5";
		case "71ARM.Florida.720.formula":
			return "5.0 - 0.3 + 0.5";
		case "71ARM.Florida.800.formula":
			return "5.0 - 0.3 + 0.5";
		case "71ARM.Texas.400.formula":
			return "5.0 + 0.5 + 0.5";
		case "71ARM.Texas.720.formula":
			return "5.0 - 0.3 + 0.5";
		case "71ARM.Texas.800.formula":
			return "5.0 - 0.3 + 0.5";
		// enhancement 1.0
		case "term.10.formula":
			return "5.0";
		case "term.20.formula":
			return "5.0 + 0.7";
		default:
			return null;
		}
	}

	public final static Double getCorrectRate(String key) {
		switch (key) {
		case "71ARM.Florida.400.rate":
			return 6.0d;
		case "71ARM.Florida.720.rate":
			return 5.2d;
		case "71ARM.Florida.800.rate":
			return 5.2d;
		case "71ARM.Texas.400.rate":
			return 6.0d;
		case "71ARM.Texas.720.rate":
			return 5.2d;
		case "71ARM.Texas.800.rate":
			return 5.2d;
		// enhancement 1.0
		case "term.10.rate":
			return 5.0d;
		case "term.20.rate":
			return 5.7d;
		default:
			return null;
		}
	}
	
	public final static Boolean getCorrectDisqual(String key) {
		switch (key) {
		case "71ARM.Florida.400.disqual":
			return true;
		case "71ARM.Florida.720.disqual":
			return true;
		case "71ARM.Florida.800.disqual":
			return true;
		case "71ARM.Texas.400.disqual":
			return false;
		case "71ARM.Texas.720.disqual":
			return false;
		case "71ARM.Texas.800.disqual":
			return false;
		default:
			return null;
		}
	}
	
	public final static String ERROR_MSG = "Unable to provide a result at this time.";
}
