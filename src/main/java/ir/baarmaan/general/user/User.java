package ir.baarmaan.general.user;

import ir.baarmaan.general.exception.unchecked.ValidationException;
import ir.baarmaan.utility.validation.EmailValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NonNull
    private String id;
    private String user;
    private String pass;
    private Date issueDate;
    private String emailAddress;

    public void setEmailAddress(String emailAddress) {
        if (EmailValidator.isEmailAddressValid(emailAddress))
            this.emailAddress = emailAddress;
        else
            throw new ValidationException("email address is not valid");
    }
}
