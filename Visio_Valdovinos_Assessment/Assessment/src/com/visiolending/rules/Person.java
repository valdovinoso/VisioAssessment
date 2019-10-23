package com.visiolending.rules;

public class Person {

	private Integer creditScore;
	private String state;

	public Person(int score, String state) {
		// TODO validation ranges should be pulled from a properties file
		// TODO confirm with finance team on the validation
		if(0 < score && 1000 > score) 
			this.creditScore = score;

		// TODO confirm with finance team on the validation
		if(null != state && 0 < state.trim().length())
			this.state = state;
	}

	/**
	 * @return the creditScore
	 */
	public Integer getCreditScore() {
		return creditScore;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

}