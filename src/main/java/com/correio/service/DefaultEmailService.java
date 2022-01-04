package com.correio.service;
import com.correio.dataaccess.EmailMensageDao;
import com.correio.service.domain.EmailMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultEmailService implements EmailService{
    private EmailMensageDao emailMensageDao;


    public void enqueue(EmailMessage menssage) {
         try {
             menssage.validate();
         }catch (IllegalArgumentException e){
            // tratamento de msg
         }
         // a mensagem é válida; insira-a na fila
        this.emailMensageDao.insert(menssage);
    }

    public void setEmailMensageDao(final EmailMensageDao emailMensageDao){
        this.emailMensageDao =  emailMensageDao;
    }
}
