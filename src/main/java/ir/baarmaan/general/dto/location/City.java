package ir.baarmaan.general.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {

    private String code;
    private String name;
    private String persian;
    private Country countryDetails;
}
