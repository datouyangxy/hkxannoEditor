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

import com.xy.hkxannoeditor.entity.enums.AnnoType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p> Properties are configured in the application.yml file. </p>
 */
@Data
@Component(value = "annoProperties")
@ConfigurationProperties(prefix = "anno", ignoreUnknownFields = false)
public class AnnoProperties {

    private List<String> common;

    private List<String> amr;

    private List<String> mco;

    private List<String> scar;

    private List<String> precision;

    private Map<AnnoType, List<String>> payload;

    public List<String> getPayloads(AnnoType annoType) {
        return payload.get(annoType);
    }
}
