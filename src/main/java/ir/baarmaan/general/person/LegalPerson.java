package ir.baarmaan.general.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LegalPerson extends Person{

    private String economicCode;
    private String nationalId;

}
