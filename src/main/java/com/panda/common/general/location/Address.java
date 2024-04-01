package com.panda.common.general.location;

import com.panda.common.general.enumeration.AddressType;
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
