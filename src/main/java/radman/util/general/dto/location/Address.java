package radman.util.general.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import radman.util.general.enumeration.AddressType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private City city;
    private AddressType addressType;
    private AddressDetails addressDetails;

}
