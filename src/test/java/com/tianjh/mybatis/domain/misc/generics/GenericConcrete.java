package com.tianjh.mybatis.domain.misc.generics;

import org.apache.ibatis.domain.misc.generics.GenericInterface;
import org.apache.ibatis.domain.misc.generics.GenericSubclass;

/**
 * @author: wb-tjh438466
 * @date: 2019/1/24
 * @since: 1 description:
 */
public class GenericConcrete extends GenericSubclass implements GenericInterface<Long> {

    private Long id;


    @Override
    public Long getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Long.valueOf(id);
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = (long) id;
    }
}
