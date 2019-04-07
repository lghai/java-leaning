package com.solinx.demo.spi;

import com.solinx.demo.api.IOperation;

public class DivisionOperationImpl implements IOperation {
 
    @Override
    public int operation(int numberA, int numberB) {
        return numberA / numberB;
    }
}