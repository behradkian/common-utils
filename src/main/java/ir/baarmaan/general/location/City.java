package ir.baarmaan.general.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {

    private String id;
    private String code;
    private String name;
    private Country countryDetails;
}
