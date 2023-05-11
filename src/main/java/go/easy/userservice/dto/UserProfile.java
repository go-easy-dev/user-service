package go.easy.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "userprofile")
@Builder(toBuilder = true)
public class UserProfile {

	@Id
	@NotBlank
	private String id;

	@NotBlank
	private String firstName;

	private String secondName;

	private String middleName;

	private Gender gender;

	private LocalDate birthDate;

	@Email
	@NotBlank
	@Indexed(unique = true)
	private String email;

	private String phone;

}
