package com.zhao.framework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhao
 */
@Setter
@Getter
@AllArgsConstructor
public class Invocation implements Serializable{

    private String interfaceName;
    private String methodName;
    private Class[] paramTypes;
    private Object[] params;

}
