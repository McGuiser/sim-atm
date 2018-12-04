package com.corey.sim.atm.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PinService {

    public String encryptPin(String passwd) throws ServiceException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            return toString(digest);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private String toString(byte[] values) {
        StringBuilder sb = new StringBuilder();
        for (byte b : values) {
            String s = String.format("%02x", b);
            sb.append(s);
        }
        return sb.toString();
    }

    public void verifyPin(String encrypted, String plain) throws ServiceException {
        String pin = encryptPin(plain);
        if (!encrypted.equals(pin)) {
            throw new ServiceException("invalid pin");
        }
    }
}
