package com.solinx.demo.impl;

import com.solinx.demo.api.IOperation;

public class PlusOperationImpl implements IOperation {
 

    public int operation(int numberA, int numberB) {
        return numberA + numberB;
    }
}