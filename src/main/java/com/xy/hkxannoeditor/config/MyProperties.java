package com.xy.hkxannoeditor.config;
/*
 * Copyright 2016-2017 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster project, see http://www.jhipster.tech/
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * Properties specific to JHipster.
 *
 * <p> Properties are configured in the application.yml file. </p>
 */
@Data
@Component(value = "myProperties")
@ConfigurationProperties(prefix = "anno", ignoreUnknownFields = false)
public class MyProperties {

    private Map<String, String> common;

    private Map<String, String> skysa;

    private Map<String, String> amr;

    private Map<String, String> mco;
}
