package ir.baarmaan.general.dto.location;

import ir.baarmaan.general.enumeration.AddressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private City city;
    private AddressType addressType;
    private AddressDetails addressDetails;

}
