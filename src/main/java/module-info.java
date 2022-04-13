module com.xy.hkxannoeditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires lombok;
    requires org.apache.commons.lang3;
    requires org.yaml.snakeyaml;

    exports com.xy.hkxannoeditor;
    opens com.xy.hkxannoeditor to javafx.fxml;
    exports com.xy.hkxannoeditor.entity.bo;
    opens com.xy.hkxannoeditor.entity.bo to javafx.fxml;
    exports com.xy.hkxannoeditor.entity.enums;
    opens com.xy.hkxannoeditor.entity.enums to javafx.fxml;
    exports com.xy.hkxannoeditor.entity.bo.annotations;
    opens com.xy.hkxannoeditor.entity.bo.annotations to javafx.fxml;
    exports com.xy.hkxannoeditor.utils;
    opens com.xy.hkxannoeditor.utils to javafx.fxml;
    exports com.xy.hkxannoeditor.controller;
    opens com.xy.hkxannoeditor.controller to javafx.fxml;
}