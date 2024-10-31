package com.springboot.task.Tax;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface PersonalIncomeTaxService {
    @WebMethod
    double calculatePersonalIncomeTax(double income);
}