package com.correio.service.domain;
import com.correio.service.EmailAdress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class EmailMessage {
    @Nullable
    private long id;
    private long version;
    private String subject;
    private String body;
    private Set<EmailMenssageAttachment> attachments;
    private Set<EmailAdress> recipients;
    private EmailAdress replyTo;
    private boolean succeeded;
    private int sendAttempCount;
    private Date sentDate;

    public EmailMessage() {
    }

    public void addAttachment(EmailMenssageAttachment attachments){
        this.attachments.add(attachments);
    }

    public void addRecipients(EmailAdress emailAdress){
        this.recipients.add(emailAdress);
    }

    public void validate(){
    }


}
