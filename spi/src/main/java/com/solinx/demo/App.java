package com.solinx.demo;

import com.solinx.demo.api.IOperation;
import com.solinx.demo.impl.PlusOperationImpl;

import java.util.Iterator;
import java.util.ServiceLoader;

public class App 
{
    public static void main(String[] args) {
        IOperation plus = new PlusOperationImpl();

        System.out.println("普通api调用："+plus.operation(6, 3));


        ServiceLoader<IOperation> operations = ServiceLoader.load(IOperation.class);

        Iterator<IOperation> operationIterator = operations.iterator();

        while (operationIterator.hasNext()) {
            IOperation operation = operationIterator.next();
            System.out.println("spi服务调用："+operation.operation(6, 3));
        }
    }
}
