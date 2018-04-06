package com.blackphoton.planetclicker.objectType;

/**
 * Stores ridiculously large numbers by splitting into decimal and a power of 10
 */
public class Exponent{
	private double start;
	private int exp;
	public Exponent(double Number, int timesTenToPowerOf){
		start=Number;
		exp=timesTenToPowerOf;
	}
	public long toLong(){
		return (long) (start * Math.pow(10, exp));
	}
	public long toInt(){
		return (int) (start * Math.pow(10, exp));
	}
	public float toFloat(){
		return (float) (start * Math.pow(10, exp));
	}
	public double toDouble(){
		return (start * Math.pow(10, exp));
	}
	public static Exponent toExponent(double number){
		double dble = number;
		int exponent = 0;
		for(;dble>=10; exponent++){
			dble=dble/10;
		}
		for(;dble<1; exponent--){
			dble=dble*10;
		}
		return new Exponent(dble, exponent);
	}
}
