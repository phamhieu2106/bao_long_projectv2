package org.example.sharedlibrary.base_handler;

import org.example.sharedlibrary.base_class.BaseCommand;
import org.example.sharedlibrary.base_response.WrapperResponse;

public interface BaseCommandService
        <C extends BaseCommand, U extends BaseCommand, D extends BaseCommand> {

    WrapperResponse create(C command);

    WrapperResponse update(U command);

    WrapperResponse delete(D command);
}
