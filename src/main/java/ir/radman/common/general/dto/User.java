package ir.radman.common.general.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;
    private String user;
    private String pass;
    private Date issueDate;
    private String emailAddress;

}
