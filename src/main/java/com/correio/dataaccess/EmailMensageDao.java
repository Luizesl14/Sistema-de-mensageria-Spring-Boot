package com.correio.dataaccess;
import com.correio.service.domain.EmailMessage;

import java.util.Date;
import java.util.List;

public interface EmailMensageDao {
    void insert(EmailMessage emailMessage);
    List<EmailMessage> getUnset();
    void delete(EmailMessage emailMessage);
    void update(long id, int sendAttemptCount, boolean success, Date date);
}
