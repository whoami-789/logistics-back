package com.logistics.model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String telegram;
    private String country;
    private String whatsappAccount;
    private String companyName;
    private String password;
    private String registrationToken;
    private Long chatId;

    private boolean enabled;

    public User(Long id, String firstName, String lastName, String phoneNumber, String telegram, String country, String password, String registrationToken, Long chatId, boolean enabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.telegram = telegram;
        this.country = country;
        this.password = password;
        this.registrationToken = registrationToken;
        this.chatId = chatId;
        this.enabled = enabled;
    }

    public User() {
    }

    // Метод для возврата ролей и прав пользователя
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(phoneNumber)); // Возвращаем имя роли как GrantedAuthority
    }

    protected boolean canEqual(final Object other) {
        return other instanceof User;
    }

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getTelegram() {
        return this.telegram;
    }

    public String getCountry() {
        return this.country;
    }

    public String getWhatsappAccount() {
        return this.whatsappAccount;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRegistrationToken() {
        return this.registrationToken;
    }

    public Long getChatId() {
        return this.chatId;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setWhatsappAccount(String whatsappAccount) {
        this.whatsappAccount = whatsappAccount;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) return false;
        final User other = (User) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        final Object this$phoneNumber = this.getPhoneNumber();
        final Object other$phoneNumber = other.getPhoneNumber();
        if (this$phoneNumber == null ? other$phoneNumber != null : !this$phoneNumber.equals(other$phoneNumber))
            return false;
        final Object this$telegram = this.getTelegram();
        final Object other$telegram = other.getTelegram();
        if (this$telegram == null ? other$telegram != null : !this$telegram.equals(other$telegram)) return false;
        final Object this$country = this.getCountry();
        final Object other$country = other.getCountry();
        if (this$country == null ? other$country != null : !this$country.equals(other$country)) return false;
        final Object this$whatsappAccount = this.getWhatsappAccount();
        final Object other$whatsappAccount = other.getWhatsappAccount();
        if (this$whatsappAccount == null ? other$whatsappAccount != null : !this$whatsappAccount.equals(other$whatsappAccount))
            return false;
        final Object this$companyName = this.getCompanyName();
        final Object other$companyName = other.getCompanyName();
        if (this$companyName == null ? other$companyName != null : !this$companyName.equals(other$companyName))
            return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        final Object this$registrationToken = this.getRegistrationToken();
        final Object other$registrationToken = other.getRegistrationToken();
        if (this$registrationToken == null ? other$registrationToken != null : !this$registrationToken.equals(other$registrationToken))
            return false;
        final Object this$chatId = this.getChatId();
        final Object other$chatId = other.getChatId();
        if (this$chatId == null ? other$chatId != null : !this$chatId.equals(other$chatId)) return false;
        if (this.isEnabled() != other.isEnabled()) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        final Object $phoneNumber = this.getPhoneNumber();
        result = result * PRIME + ($phoneNumber == null ? 43 : $phoneNumber.hashCode());
        final Object $telegram = this.getTelegram();
        result = result * PRIME + ($telegram == null ? 43 : $telegram.hashCode());
        final Object $country = this.getCountry();
        result = result * PRIME + ($country == null ? 43 : $country.hashCode());
        final Object $whatsappAccount = this.getWhatsappAccount();
        result = result * PRIME + ($whatsappAccount == null ? 43 : $whatsappAccount.hashCode());
        final Object $companyName = this.getCompanyName();
        result = result * PRIME + ($companyName == null ? 43 : $companyName.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final Object $registrationToken = this.getRegistrationToken();
        result = result * PRIME + ($registrationToken == null ? 43 : $registrationToken.hashCode());
        final Object $chatId = this.getChatId();
        result = result * PRIME + ($chatId == null ? 43 : $chatId.hashCode());
        result = result * PRIME + (this.isEnabled() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "User(id=" + this.getId() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", phoneNumber=" + this.getPhoneNumber() + ", telegram=" + this.getTelegram() + ", country=" + this.getCountry() + ", whatsappAccount=" + this.getWhatsappAccount() + ", companyName=" + this.getCompanyName() + ", password=" + this.getPassword() + ", registrationToken=" + this.getRegistrationToken() + ", chatId=" + this.getChatId() + ", enabled=" + this.isEnabled() + ")";
    }
}
