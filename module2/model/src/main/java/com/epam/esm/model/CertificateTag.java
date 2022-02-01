package com.epam.esm.model;

public class CertificateTag {
    private Long id;
    private Long tagId;
    private Long certificateId;

    public CertificateTag() {}

    public CertificateTag(Long id, Long tagId, Long certificateId) {
        this.id = id;
        this.tagId = tagId;
        this.certificateId = certificateId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
    }
}



