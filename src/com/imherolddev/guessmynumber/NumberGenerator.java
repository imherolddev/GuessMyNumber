package com.imherolddev.guessmynumber;

public class NumberGenerator {
	
	private int number = 0;
    
    public int makeNumber(int min, int max) {
        
        this.number = min + (int)(Math.random() * ((max - min) + 1));
        return this.number;
        
    }
    
    @Override
    public String toString() {
    	
    	return String.valueOf(number);
    	
    }

}
