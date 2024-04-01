package com.panda.common.bank.customer;

import com.panda.common.bank.enumeration.RelationType;
import com.panda.common.general.person.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatedPerson {

    private Person person;
    private RelationType relationType;
    private boolean isActive;
}
