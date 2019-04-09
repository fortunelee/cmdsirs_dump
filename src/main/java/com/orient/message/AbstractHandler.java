package com.orient.message;

import java.io.Serializable;

public abstract class AbstractHandler implements Serializable {


    public String[] getParams() {

        return new String[0];
    }
}
