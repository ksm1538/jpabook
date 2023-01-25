package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException{
	
	public NotEnoughStockException() {
	}
	
	public NotEnoughStockException(String m) {
		super(m);
	}
	
	public NotEnoughStockException(String m, Throwable throwable) {
		super(m, throwable);
	}
	
	public NotEnoughStockException(Throwable throwable) {
		super(throwable);
	}
}
