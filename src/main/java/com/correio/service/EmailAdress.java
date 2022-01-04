package com.correio.service;
import com.correio.service.EmailAdressType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAdress {
    private long id;
    private long version;
    private String emailAdress;
    private String fullName;
    private EmailAdressType emailAdressType;
}
