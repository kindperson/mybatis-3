package com.tianjh.mybatis.exceptions;

/**
 * @author: wb-tjh438466
 * @date: 2019/1/9
 * @since: 1 description:
 */
public class IbatisException extends RuntimeException{
    private static final long serialVersionUID = 3880206998166270511L;

    public IbatisException() {
        super();
    }

    public IbatisException(String message) {
        super(message);
    }

    public IbatisException(String message, Throwable cause) {
        super(message, cause);
    }

    public IbatisException(Throwable cause) {
        super(cause);
    }
}
