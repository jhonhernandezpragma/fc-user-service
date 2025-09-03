package com.pragma.fc.user_service.domain.spi;

public interface IPasswordEncryptorPort {
    String encrypt(String plainPassword);
}
