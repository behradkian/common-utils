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
public enum SecurityProtocol implements Serializable {
    HTTP("http://"),
    HTTPS("https://");

    private final String prefix;
}