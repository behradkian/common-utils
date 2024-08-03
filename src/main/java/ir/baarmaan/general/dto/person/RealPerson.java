package ir.baarmaan.general.dto.person;

import ir.baarmaan.general.dto.location.City;
import ir.baarmaan.general.enumeration.GenderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class RealPerson extends Person {

    private String firstName;
    private String lastName;
    private Date birthdate;
    private String fatherName;
    private String mobileNumber;
    private String passportNumber;
    private GenderType gender;
    private City birthCity;

}
