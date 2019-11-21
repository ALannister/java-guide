package com.lannister.user.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lannister.ticket.service.TicketService;
import org.springframework.stereotype.Service;

/**
 * Created by Lannister on 2019/11/11.
 */
@Service
public class UserServiceImpl implements UserService{

  @Reference
  TicketService ticketService;

  @Override
  public void hello() {
    String ticket = ticketService.getTicket();
    System.out.println("买到票了：" + ticket);
  }
}
