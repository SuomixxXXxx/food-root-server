package org.chiches.foodrootservir.exceptions.order;

public abstract class OrderException extends RuntimeException {
    public OrderException(String message) {
        super(message);
    }
}
