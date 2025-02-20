package org.gokdemir.dms.exception;

import lombok.Getter;

@Getter
public enum MessageType {

    NO_RECORD_EXIST("1001","Kayıt bulunamadı"),
    GENERAL_EXCEPTION("9999","Genel bir hata oluştu"),
    REFRESH_TOKEN_EXPIRED("1002", "refresh token süresi dolmuştur"),
    REFRESH_TOKEN_NOT_FOUND("1003", "refresh token bulunamadı"),
    USERNAME_OR_PASSWORD_INVALID("1004","kullanıcı adı veya şifre hatalı"),
    COMPANY_ALREADY_EXISTS("1005", "Bu isimde bir firma zaten mevcut!"),
    FOLDER_CREATION_FAILED("1006", "Firma klasörü oluşturulamadı!"),
    FOLDER_RENAME_FAILED("1007", "Firma klasörü adı değiştirilemedi!"),
    FOLDER_NOT_FOUND("1008", "Firma klasörü bulunamadı!"),
    COMPANY_ALREADY_ACTIVE("1009", "Firma zaten aktif!"),
    COMPANY_ALREADY_INACTIVE("1010", "Firma zaten pasif!"),
    COMPANY_IS_ACTIVE("1011", "Aktif firma kalıcı olarak silinemez!"),
    FILE_NOT_FOUND("1012","dosya bulunamadı");

    private String code;

    private String message;

    private MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
