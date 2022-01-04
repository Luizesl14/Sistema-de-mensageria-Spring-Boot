package com.correio.dataaccess;

import com.correio.service.domain.EmailMessage;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
public class HeapEmailMessageDao implements EmailMensageDao {
    private long lastId = 0L;
    private Map<Long, EmailMessage> mailQueue = new HashMap<>();

    // insere msg dentro da pilha
    public void insert(EmailMessage emailMessage) {
            this.lastId++;
            emailMessage.setId(this.lastId);
            this.mailQueue.put(emailMessage.getId(), emailMessage);

    }

    // procura dentro de lista key insere mensagem
    public List<EmailMessage> getUnset() {
        List<EmailMessage> message = new ArrayList<>();
        for(Long id : this.mailQueue.keySet()){
            if(mailQueue.get(id).isSucceeded()){
               message.add(mailQueue.get(id));
            }
        }
        return null;
    }

    // deleta uma mensagem da pilha
    public void delete(EmailMessage emailMessage) {
          this.mailQueue.remove(emailMessage.getId());
    }

    // faz update de uma mensagem na pilha
    public void update(long id, int sendAttemptCount, boolean success, Date date) {
           EmailMessage message = this.mailQueue.get(id);
           message.setSentDate(date);
           message.setSendAttempCount(sendAttemptCount);
           if(success){
               message.setSucceeded(true);
               this.mailQueue.put(id, message);
           }
    }

}
