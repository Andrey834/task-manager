package ru.em.taskmanager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "app.rsa")
public record RsaProperty(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
}
