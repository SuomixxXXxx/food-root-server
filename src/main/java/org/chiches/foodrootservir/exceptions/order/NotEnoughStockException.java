package org.chiches.foodrootservir.exceptions.order;

public class NotEnoughStockException extends OrderException {
    public NotEnoughStockException(String message) {
        super(message);
    }
}
