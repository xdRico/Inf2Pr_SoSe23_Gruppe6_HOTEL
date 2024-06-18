package de.gruppe6.hotel.frontend.base.interactions;

public enum CardSide {
	front, back;
	
	public CardSide turn() {
		if(this == CardSide.front)
			return CardSide.back;	
		return CardSide.front;
	}
	
	public static CardSide turn(CardSide side) {
		if(side == CardSide.front)
			return CardSide.back;
		return CardSide.front;
	}
}
