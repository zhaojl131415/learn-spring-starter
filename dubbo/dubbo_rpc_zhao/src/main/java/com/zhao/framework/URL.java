package com.zhao.framework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zhao
 */
@Setter
@Getter
@AllArgsConstructor
public class URL implements Serializable{
    private String hostname;
    private Integer port;
}
