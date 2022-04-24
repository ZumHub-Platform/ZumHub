package com.server.response;

public enum ContentType {

    //Application content types
    APPLICATION_EDI_X12("application/EDI-X12"),
    APPLICATION_EDIFACT("application/EDIFACT"),
    APPLICATION_JAVASCRIPT("application/javascript"),
    APPLICATION_OCTET_STREAM("application/octet-stream"),
    APPLICATION_OGG("application/ogg"),
    APPLICATION_PDF("application/pdf"),
    APPLICATION_XHTML_XML("application/xhtml+xml"),
    APPLICATION_X_SHOCKWAVE_FLASH("application/x-shockwave-flash"),
    APPLICATION_JSON("application/json"),
    APPLICATION_LD_JSON("application/ld+json"),
    APPLICATION_XML("application/xml"),
    APPLICATION_ZIP("application/zip"),
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),

    //Audio content types
    AUDIO_MPEG("audio/mpeg"),
    AUDIO_X_MS_WMA("audio/x-ms-wma"),
    AUDIO_VND_RN_REALAUDIO("audio/vnd.rn-realaudio"),
    AUDIO_X_WAV("audio/x-wav"),

    //Image content types
    IMAGE_GIF("image/gif"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    IMAGE_TIFF("image/tiff"),
    IMAGE_VND_MICROSOFT_ICON("image/vnd.microsoft.icon"),
    IMAGE_X_ICON("image/x-icon"),
    IMAGE_VND_DJVU("image/vnd.djvu"),
    IMAGE_SVG_XML("image/svg+xml"),

    //Multipart (files) content types
    MULTIPART_MIXED("multipart/mixed"),
    MULTIPART_ALTERNATIVE("multipart/alternative"),
    MULTIPART_RELATED("multipart/related"),
    MULTIPART_FORM_DATA("multipart/form-data"),

    //Text content types
    TEXT_CSS("text/css"),
    TEXT_CSV("text/csv"),
    TEXT_HTML("text/html"),
    TEXT_JAVASCRIPT("text/javascript"),
    TEXT_PLAIN("text/plain"),
    TEXT_XML("text/xml"),

    //Video content types
    VIDEO_MPEG("video/mpeg"),
    VIDEO_MP4("video/mp4"),
    VIDEO_QUICKTIME("video/quicktime"),
    VIDEO_X_MS_WMV("video/x-ms-wmv"),
    VIDEO_X_MSVIDEO("video/x-msvideo"),
    VIDEO_X_FLV("video/x-flv"),
    VIDEO_WEBM("video/webm");

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }
}
