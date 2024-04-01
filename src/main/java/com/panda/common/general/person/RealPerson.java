package com.panda.common.general.person;

import com.panda.common.general.enumeration.GenderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealPerson extends Person {

    private String nationalCode;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String bookNo;
    private String bookSeries;
    private String bookSerial;
    private String nationalCodeSerial;
    private GenderType gender;

}
