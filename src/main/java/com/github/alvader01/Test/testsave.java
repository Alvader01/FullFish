package com.github.alvader01.Test;

import com.github.alvader01.Model.connection.ConnectionProperties;
import com.github.alvader01.utils.XMLManager;

public class testsave {
    public static void main(String[] args) {
        ConnectionProperties c = new ConnectionProperties("localhost","3306","library","root","root");
        XMLManager.writeXML(c,"connection.xml");
    }
}
