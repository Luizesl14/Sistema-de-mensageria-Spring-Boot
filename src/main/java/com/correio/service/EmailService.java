package com.correio.service;

import com.correio.service.domain.EmailMessage;

public interface EmailService {
    void enqueue(final EmailMessage message);
}
