package org.czx.spring.ioc.test.service.impl;

import org.czx.spring.ioc.annotation.*;
import org.czx.spring.ioc.test.service.IQueryService;

/**
 * Created by zhixuecai on 2018/11/26.
 */
@CCService
public class IQueryServiceImpl implements IQueryService {

    @Override
    public String query(String name) {
        return "hello";
    }
}
