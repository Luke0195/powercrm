package br.com.powercrm.app.factories;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.enums.UserStatus;
import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;

import java.util.UUID;

public class UserFactory {

    private UserFactory(){};


    public static UserRequestDto makeUserRequestDto(){
        return new UserRequestDto("any_name", "any_mail@mail.com", "any_phone",
                "640.290.140-70", "any_zipcode", "any_address",
                30, "any_complement", UserStatus.ACTIVE);
    };

    public static User makeUser(UserRequestDto userRequestDto){
        return User.builder()
                .id(UUID.randomUUID())
                .name(userRequestDto.name())
                .email(userRequestDto.email())
                .phone(userRequestDto.phone())
                .number(userRequestDto.number())
                .complement(userRequestDto.complement())
                .status(userRequestDto.status())
                .build();
    }

    public static UserResponseDto makeUserResponseDto(User user){
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getCpf(),
                user.getZipcode(), user.getAddress(), user.getNumber(),
                user.getComplement(), user.getStatus(), user.getCreatedAt());
    }
}
