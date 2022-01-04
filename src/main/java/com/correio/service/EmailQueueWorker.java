package com.correio.service;

import com.correio.dataaccess.EmailMensageDao;
import com.correio.service.domain.EmailMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;

import static jdk.incubator.foreign.MemoryAccess.getAddress;

@Getter
@Setter
@ManagedResource(objectName = "com.correio : name=EmailQueueWorker")
public class EmailQueueWorker {
    private EmailMensageDao emailMensageDao;
    private JavaMailSender javaMailSender;

    @ManagedOperation(description = "Runs the queue new")
    public  void  run(){
        List<EmailMessage> mensages = this.emailMensageDao.getUnset();
        for(EmailMessage message: mensages){
            sendOne(message);
        }
    }

    private void sendOne(EmailMessage message){
        MimeMessage mimeMessage =  javaMailSender.createMimeMessage();
        prepareMimeMessage(mimeMessage, message);
        javaMailSender.send(mimeMessage);

        // atualiza fila
        this.emailMensageDao.update(message.getId(), message.getSendAttempCount() +1, true, new Date());
    }

    private void prepareMimeMessage(MimeMessage mimeMessage, EmailMessage emailMessage){
        try{
           mimeMessage.setFrom(getAddress());
        }
    }
}
