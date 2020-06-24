
package com.analytics.UI;

import java.time.LocalDate;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Mo
 */
//@RestController
public class test {
    
    private RestTemplate restTemplate = new RestTemplate();
    
    private String URL="http://localhost:8080/database/test/";
    private String all="http://localhost:8080/database/all-country/";
    
    
    public test() {
    
    }
    
    @GetMapping("/database/")
    public Country readIt(){
        Country data = restTemplate.getForObject(URL, Country.class);
        System.out.println("++++++++++++++++GOTTTAAAAAAAAA+++++++++++++++++");
        System.out.println(data.getRealName());
        return data;
    }
    
    @GetMapping("/all/")
    public Data[] aha(){
        LocalDate date = LocalDate.of(2020, 3, 8);
        Request request=new Request(date);
        Data[] data = restTemplate.postForObject(URL,request, Data[].class);
        System.out.println("++++++++++++++++GOTTTAAAAAAAAA+++++++++++++++++");
        return data;
    }
    
}
