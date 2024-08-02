package ir.baarmaan.general.person;

import ir.baarmaan.general.location.Address;
import ir.baarmaan.general.enumeration.PersonType;
import ir.baarmaan.general.location.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Person {

    private PersonType personType;
    private String code;
    private List<Address> addresses;

}
