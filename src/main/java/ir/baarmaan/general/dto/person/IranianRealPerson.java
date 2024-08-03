package ir.baarmaan.general.dto.person;

import ir.baarmaan.general.enumeration.IranianNationalCardType;
import ir.baarmaan.general.enumeration.Nationality;
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
