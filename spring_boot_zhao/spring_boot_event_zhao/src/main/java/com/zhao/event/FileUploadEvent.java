package com.zhao.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-29 14:49
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadEvent extends ZhaoApplicationEvent {
    private int fileSize;

    private int readSize;


}
