package com.demo.service.demo.impl;

import com.demo.dao.demo.DemoDao;
import com.demo.entity.demo.Demo;
import com.demo.service.base.impl.BaseServiceImpl;
import com.demo.service.demo.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl extends BaseServiceImpl<DemoDao,Demo> implements DemoService{

}

