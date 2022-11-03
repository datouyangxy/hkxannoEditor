package com.xy.hkxannoeditor.component.factory;

public class ComponentFactoryProducer {
    public enum factoryType{
        TABLE_COLUMN
    }

    public static AbstractComponentFactory getFactory(factoryType factoryType){
        switch (factoryType){
            case TABLE_COLUMN -> {
                return new TableColumnFactory();
            }
            default -> {
                return null;
            }
        }
    }
}
