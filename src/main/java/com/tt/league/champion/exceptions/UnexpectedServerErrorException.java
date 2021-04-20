package com.tt.league.champion.exceptions;

public class UnexpectedServerErrorException extends RuntimeException{

	public UnexpectedServerErrorException() {
		super("Unexpected Error occurred");
	}
	
	public UnexpectedServerErrorException(String msg) {
		super(msg);
	}
}
