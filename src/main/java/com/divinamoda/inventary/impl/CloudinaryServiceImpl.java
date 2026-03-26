package com.divinamoda.inventary.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.divinamoda.inventary.exception.BadRequestException;
import com.divinamoda.inventary.service.CloudinaryService;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${cloudinary.cloud-name:}")
    private String cloudName;

    @Value("${cloudinary.api-key:}")
    private String apiKey;

    @Value("${cloudinary.api-secret:}")
    private String apiSecret;

    @Override
    public String uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Archivo de imagen vacío");
        }

        validateCloudinaryConfig();

        long timestamp = Instant.now().getEpochSecond();
        String signature = sha1Hex("timestamp=" + timestamp + apiSecret);
        String uploadUrl = "https://api.cloudinary.com/v1_1/" + cloudName + "/image/upload";

        try {
            ByteArrayResource imageResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", imageResource);
            body.add("api_key", apiKey);
            body.add("timestamp", String.valueOf(timestamp));
            body.add("signature", signature);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    uploadUrl,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            Map<?, ?> payload = response.getBody();
            if (payload == null || payload.get("secure_url") == null) {
                throw new RuntimeException("Cloudinary no devolvió URL segura de la imagen");
            }

            return payload.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer la imagen para subirla", e);
        } catch (Exception e) {
            throw new RuntimeException("Error al subir imagen a Cloudinary", e);
        }
    }

    private void validateCloudinaryConfig() {
        if (cloudName.isBlank() || apiKey.isBlank() || apiSecret.isBlank()) {
            throw new RuntimeException(
                    "Faltan variables de Cloudinary: cloudinary.cloud-name, cloudinary.api-key, cloudinary.api-secret");
        }
    }

    private String sha1Hex(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No se pudo generar firma SHA-1 para Cloudinary", e);
        }
    }
}
