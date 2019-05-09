package com.adildemokrasi.adil.Enum;

public enum Status {
    SUCCESS(1L),
    ERROR(2L),
    NORESPONSE(3L);

    private final Long status;

    Status(final Long newStatus){
        status = newStatus;
    }

    public Long getStatus() {
        return status;
    }
}
