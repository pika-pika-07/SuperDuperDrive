package com.example.SuperDuperDrive.services;


import com.example.SuperDuperDrive.mapper.CredentialMapper;
import com.example.SuperDuperDrive.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {


    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public static String getRandomEncodedStr() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    public CredentialService(CredentialMapper credentialMapper,
                             EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentials(Integer userid) {
        List<Credential> credentials = credentialMapper.getAllCredentials(userid);
        decryptPasswords(credentials);
        return credentials;
    }

    public void createOrEditCredential(Credential credential) {
        String encodedKey = getRandomEncodedStr();
        credential.setKey(encodedKey);
        String encryptedPwd = encryptionService.encryptValue(credential.getDecryptedPassword(), encodedKey);
        credential.setPassword(encryptedPwd);
        // credentialid is null for new credentials
        if (credential.getCredentialid() == null) {
            credentialMapper.insert(credential);
        } else {
            credentialMapper.update(credential);
        }
    }

    public void deleteCredential(Integer credentialid, Integer userid) {
        credentialMapper.delete(credentialid, userid);
    }

    private void decryptPasswords(List<Credential> credentials) {
        for (Credential credential : credentials) {
            String key = credential.getKey();
            String encryptedPwd = credential.getPassword();
            String decryptedPwd = encryptionService.decryptValue(encryptedPwd, key);
            credential.setDecryptedPassword(decryptedPwd);
        }
    }
}
