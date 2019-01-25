package com.tianjh.mybatis.exceptions;

/**
 * @author: wb-tjh438466
 * @date: 2019/1/9
 * @since: 1 description:
 */
public class PersistenceException extends IbatisException{
    private static final long serialVersionUID = -7537395265357977271L;
    public PersistenceException(String message) {
        super(message);
    }
}
