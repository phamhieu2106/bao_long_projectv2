package org.example.sharedlibrary.base_prod_cons;

import org.example.sharedlibrary.base_class.BaseEvent;

public interface BaseConsumer<C, U, D> {

    void subscribe(BaseEvent event);

    void handleCreateEvent(C event);

    void handleUpdateEvent(U event);

    void handleDeleteEvent(D event);
}
