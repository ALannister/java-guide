package com.lannister.providerticket.controller;

import com.lannister.providerticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lannister on 2019/11/11.
 */
@RestController
public class TicketController {

  @Autowired
  TicketService ticketService;

  @GetMapping("/ticket")
  public String getTicket(){
    return ticketService.getTicket();
  }
}
