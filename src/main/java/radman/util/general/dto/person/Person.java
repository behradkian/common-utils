package radman.util.general.dto.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import radman.util.general.dto.location.Address;
import radman.util.general.enumeration.PersonType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Person {

    private PersonType personType;
    private String code;
    private List<Address> addresses;

}
