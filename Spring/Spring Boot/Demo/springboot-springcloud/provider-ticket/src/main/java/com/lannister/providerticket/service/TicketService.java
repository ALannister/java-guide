package com.lannister.providerticket.service;

import org.springframework.stereotype.Service;

/**
 * Created by Lannister on 2019/11/11.
 */
@Service
public class TicketService {

  public String getTicket(){
    System.out.println("8001");
    return "《厉害了，我的国》";
  }
}
