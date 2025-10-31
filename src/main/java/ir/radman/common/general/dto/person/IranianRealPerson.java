package ir.radman.common.general.dto.person;

import ir.radman.common.general.enumeration.IranianNationalCardType;
import ir.radman.common.general.enumeration.Nationality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IranianRealPerson extends RealPerson {

    private final Nationality nationality = Nationality.IRAN;
    private String nationalCardSerial;
    private IranianNationalCardType nationalCardType;

}
