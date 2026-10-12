package radman.util.general.dto.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import radman.util.general.enumeration.IranianNationalCardType;
import radman.util.general.enumeration.Nationality;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IranianRealPerson extends RealPerson {

    private final Nationality nationality = Nationality.IRAN;
    private String nationalCardSerial;
    private IranianNationalCardType nationalCardType;

}
