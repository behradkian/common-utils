package ir.radman.common.general.enumeration.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
@Getter
@AllArgsConstructor
public enum ContentType implements Serializable {
    JSON("application/json"),
    XML("application/xml"),
    FORM_URLENCODED("application/x-www-form-urlencoded"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    TEXT_PLAIN("text/plain"),
    OCTET_STREAM("application/octet-stream");

    private final String mimeType;
}