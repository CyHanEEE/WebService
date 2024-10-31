package com.springboot.task.Tax;

import javax.xml.ws.Endpoint;

public class PersonalIncomeTaxPublisher {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/PersonalIncomeTaxService", new PersonalIncomeTaxServiceImpl());
        System.out.println("Web发布成功!");
    }
}
