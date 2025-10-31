package ir.radman.common.general.dto.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class LegalPerson extends Person {

    private String economicCode;


}
