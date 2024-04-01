package com.panda.common.general.person;

import com.panda.common.general.enumeration.PersonType;
import com.panda.common.general.location.Address;
import com.panda.common.general.location.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Person {

    private PersonType personType;
    private City birthCity;
    private Date birthdate;
    private List<Address> addresses;

}
