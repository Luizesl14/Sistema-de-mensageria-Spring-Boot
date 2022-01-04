package com.correio.service.domain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailMenssageAttachment {
    private long id;
    private long version;
    private String mimeType;
    private byte[] content;
}
