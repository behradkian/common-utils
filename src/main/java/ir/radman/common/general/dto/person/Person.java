package ir.radman.common.general.dto.person;

import ir.radman.common.general.dto.location.Address;
import ir.radman.common.general.enumeration.PersonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Person {

    private PersonType personType;
    private String code;
    private List<Address> addresses;

}
