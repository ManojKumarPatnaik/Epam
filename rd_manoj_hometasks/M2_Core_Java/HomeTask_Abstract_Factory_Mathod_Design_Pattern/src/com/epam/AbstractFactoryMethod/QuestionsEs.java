package com.epam.AbstractFactoryMethod;

public class QuestionsEs implements Questions {

	@Override
	public String askTime() {
		return "�qu� hora es?";
	}

	@Override
	public String askWeather() {
		return "�qu� tiempo hace?";
	}

}