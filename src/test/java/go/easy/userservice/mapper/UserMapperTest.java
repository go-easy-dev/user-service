package go.easy.userservice.mapper;


import go.easy.userservice.dto.CreateUserRequest;
import org.assertj.core.api.Assertions;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

public class UserMapperTest {

    private final UserMapper mapper = UserMapper.INSTANCE;
    private final EasyRandom random = new EasyRandom();

    @Test
    void should_map() {
        // given
        var given = random.nextObject(CreateUserRequest.class);

        // when
        var actual = mapper.map(given);

        // then
        Assertions.assertThat(actual.getId()).isNotBlank();
        Assertions.assertThat(actual)
                .hasFieldOrPropertyWithValue("email", given.getEmail().toLowerCase())
                .usingRecursiveComparison()
                .ignoringFields("id", "secondName", "secondName", "middleName", "birthDate", "phone", "email")
                .isEqualTo(given);
    }
}
