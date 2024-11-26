package com.logistics.dto;

import lombok.Data;

@Data
public class UpdateUserDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String telegram;
    private String whatsappAccount;
    private String companyName;
}
