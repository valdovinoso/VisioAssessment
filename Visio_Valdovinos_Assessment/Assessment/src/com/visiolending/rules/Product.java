package com.visiolending.rules;

public class Product {
	// variables
	private String name;
	private Double interestRate;
	private Double baseRate;
	private boolean disqualified;
	private StringBuilder rateFormula = new StringBuilder();
	boolean ranAllRules = true;
	// enhancement 1.0
	private Integer term;

	public Product(String name, double rate) {
		// TODO validation ranges should be pulled from a properties file
		// TODO confirm with finance team on the validation
		if(null != name && 0 < name.trim().length())
			this.name = name;
		
		if(5.0d == rate)
			this.interestRate = rate;
		// TODO confirm with finance team on the validation
		if(null != this.interestRate)
			this.baseRate = rate;
	}
	
	// enhancement 1.0
	public Product(String name, double rate, int term) {
		this(name, rate);
		// TODO confirm with finance team on the validation
		if(0 < term && 45 > term)
			this.term = term;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the interestRate
	 */
	public Double getInterestRate() {
		return interestRate;
	}

	/**
	 * @param interestRate the interestRate to set
	 */
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * @return the disqualified
	 */
	public boolean isDisqualified() {
		return disqualified;
	}

	/**
	 * @param rateFormula the rateFormula to set
	 */
	public void setRateFormula(String rateFormula) {
		this.rateFormula.append(rateFormula);
	}

	/**
	 * @param disqualified the disqualified to set
	 */
	public void setDisqualified(boolean disqualified) {
		this.disqualified = disqualified;
	}
	
	/**
	 * @param ranAllRules the ranAllRules to set
	 */
	public void setRanAllRules(boolean ranAllRules) {
		this.ranAllRules = ranAllRules;
	}
	
	/**
	 * @return the term
	 */
	public Integer getTerm() {
		return term;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if( this.ranAllRules) {
			sb.append("product.interest_rate == ");
			sb.append(this.getInterestRate());
			sb.append(" ( ");
			sb.append(this.baseRate);
			sb.append(this.rateFormula.toString());
			sb.append(" ) ");
			sb.append("\n");
			sb.append("product.disqualified == ");
			sb.append(this.disqualified);
			sb.append("\n");
		} else {
			// TODO confirm with finance team on the output
			sb.append("Unable to provide a result at this time.");
		}
		
		return sb.toString();
	}
}
