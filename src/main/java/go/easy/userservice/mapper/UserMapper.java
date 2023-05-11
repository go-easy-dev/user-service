package go.easy.userservice.mapper;

import go.easy.userservice.dto.CreateUserRequest;
import go.easy.userservice.dto.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
	@Mapping(target = "secondName", ignore = true)
	@Mapping(target = "middleName", ignore = true)
	@Mapping(target = "birthDate", ignore = true)
	@Mapping(target = "phone", ignore = true)
	UserProfile map(CreateUserRequest request);

}
