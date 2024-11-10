package org.chiches.foodrootservir.services;

import org.chiches.foodrootservir.misc.StompDestination;

public interface NotificationService {
    void sentToAll(StompDestination destination, Object message);

    void sentToUser(StompDestination destination, Object message, String username);
}
