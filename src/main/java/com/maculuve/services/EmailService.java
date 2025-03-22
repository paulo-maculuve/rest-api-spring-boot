package com.maculuve.services;

import java.io.File;
import java.io.IOException;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maculuve.config.EmailConfig;
import com.maculuve.data.dto.v1.request.EmailRequestDTO;
import com.maculuve.mail.EmailSender;

@Service
public class EmailService {

    @Autowired
    private EmailSender emailSender;
    @Autowired
    private EmailConfig emailConfig;

    public void sendEmail(EmailRequestDTO emailRequest) {
        emailSender.to(emailRequest.getTo())
                .withSubject(emailRequest.getSubject())
                .withMessage(emailRequest.getBody())
                .send(emailConfig);
    }

    public void sendEmailWithAttachment(String emailRequestJson, MultipartFile attachment) {
        File fileTemp = null;
        try {
            EmailRequestDTO emailRequest = new ObjectMapper().readValue(emailRequestJson, EmailRequestDTO.class);
            fileTemp = File.createTempFile("attachment", attachment.getOriginalFilename());
            attachment.transferTo(fileTemp);
            emailSender.to(emailRequest.getTo())
                    .withSubject(emailRequest.getSubject())
                    .withMessage(emailRequest.getBody())
                    .attach(fileTemp.getAbsolutePath())
                    .send(emailConfig);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing eamil request JSON", e);
        } catch (IOException e) {
            throw new RuntimeException("Error processing attachment", e);

        }finally{
            if(fileTemp != null && fileTemp.exists()) fileTemp.delete();
        }
    }
}
