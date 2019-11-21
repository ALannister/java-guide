package com.lannister.ticket.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lannister.ticket.service.TicketService;
import org.springframework.stereotype.Component;

/**
 * Created by Lannister on 2019/11/11.
 */
@Component
@Service //将服务发布出去
public class TicketServiceImpl implements TicketService {
  @Override
  public String getTicket() {
    return "《厉害了，我的国》";
  }
}
