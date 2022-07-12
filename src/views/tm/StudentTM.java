package views.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentTM {
    private String studentId;
    private String studentName;
    private String email;
    private String contact;
    private String address;
    private String nic;
}
