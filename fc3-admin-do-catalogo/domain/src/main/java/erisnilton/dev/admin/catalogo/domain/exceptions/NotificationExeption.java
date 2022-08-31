package erisnilton.dev.admin.catalogo.domain.exceptions;

import erisnilton.dev.admin.catalogo.domain.validation.Error;
import erisnilton.dev.admin.catalogo.domain.validation.handler.Notification;

import java.util.List;

public class NotificationExeption extends DomainException{
    public NotificationExeption(final String aMessage, final Notification notification) {
        super(aMessage, notification.getErrors());
    }
}
