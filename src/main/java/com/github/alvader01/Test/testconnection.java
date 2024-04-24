package com.github.alvader01.Test;

import com.github.alvader01.Model.connection.ConnectionProperties;
import com.github.alvader01.utils.XMLManager;

public class testconnection {
    public static void main(String[] args) {
        ConnectionProperties c = XMLManager.readXML(new ConnectionProperties(),"connection.xml");
        System.out.println(c);
    }
}
