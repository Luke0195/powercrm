package br.com.powercrm.app.domain.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    INACTIVE(0),
    ACTIVE(1);

    private int value;

    UserStatus(int value){
        this.value = value;
    }

    public static UserStatus fromValue(int value) {
        for (UserStatus status : UserStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Valor inválido para UserStatus: " + value);
    }
}
