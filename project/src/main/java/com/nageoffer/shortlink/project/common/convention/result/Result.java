package com.nageoffer.shortlink.project.common.convention.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

// T 类型定义在方法名中证明方法体内和参数传入内所有的T类型必须保持一致
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable{
    /**
     * 全局返回对象
     */
        @Serial
        private static final long serialVersionUID = 5679018624309023727L;

        /**
         * 正确返回码
         */
        public static final String SUCCESS_CODE = "0";

        /**
         * 返回码
         */
        private String code;

        /**
         * 返回消息
         */
        private String message;

        /**
         * 响应数据
         */
        private T data;

        /**
         * 请求ID
         */
        private String requestId;

        public boolean isSuccess() {
            return SUCCESS_CODE.equals(code);
        }

}
