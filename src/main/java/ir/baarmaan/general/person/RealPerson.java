package ir.baarmaan.general.person;

import ir.baarmaan.general.enumeration.GenderType;
import ir.baarmaan.general.location.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealPerson extends Person {

    private String firstName;
    private String lastName;
    private Date birthdate;
    private String fatherName;
    private String mobileNumber;

    private City birthCity;
    private String bookNo;
    private String bookSeries;
    private String bookSerial;
    private String nationalCodeSerial;
    private GenderType gender;

}
