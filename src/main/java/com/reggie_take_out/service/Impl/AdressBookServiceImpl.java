package com.reggie_take_out.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie_take_out.entity.AddressBook;
import com.reggie_take_out.mapper.AddressBookMapper;
import com.reggie_take_out.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AdressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
