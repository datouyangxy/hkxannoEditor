package com.xy.hkxannoeditor.entity.bo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xy.hkxannoeditor.config.AnnoProperties;
import com.xy.hkxannoeditor.config.AnnoProperty;
import com.xy.hkxannoeditor.entity.bo.annotations.AmrAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.ScarAnno;
import com.xy.hkxannoeditor.entity.bo.annotations.StandardAnno;
import com.xy.hkxannoeditor.entity.enums.AnnoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.xy.hkxannoeditor.Const.LINE_BREAK;
import static com.xy.hkxannoeditor.entity.enums.AnnoType.*;
import static com.xy.hkxannoeditor.utils.SpringUtil.getBean;

@Getter
@Setter
public class HkxFile {
    private final File hkx;
    private final File txt;
    private final List<String> metaList;
    private final ObservableList<StandardAnno> commonList;
    private final ObservableList<StandardAnno> mcoList;
    private final ObservableList<StandardAnno> precsisonList;
    private final ObservableList<AmrAnno> amrList;
    private final ObservableList<ScarAnno> scarList;
    private final ObservableList<String> customList;
    private final AnnoProperties annoProperties;
    private final ObjectMapper objectMapper;

    private String originAnno = null;
    private boolean edited = false;
    private boolean saved = true;

    public HkxFile(File hkx) {
        this.hkx = hkx;
        if (hkx != null && hkx.isFile()) {
            this.txt = new File(hkx.getPath().split("\\.")[0] + ".txt");
            metaList = new ArrayList<>();
            commonList = FXCollections.observableArrayList();
            mcoList = FXCollections.observableArrayList();
            precsisonList = FXCollections.observableArrayList();
            amrList = FXCollections.observableArrayList();
            scarList = FXCollections.observableArrayList();
            customList = FXCollections.observableArrayList();
            annoProperties = getBean(AnnoProperties.class);
            objectMapper = getBean(ObjectMapper.class);
        } else {
            this.txt = null;
            metaList = null;
            commonList = null;
            mcoList = null;
            precsisonList = null;
            amrList = null;
            scarList = null;
            customList = null;
            annoProperties = null;
            objectMapper = null;
        }
    }

    @Override
    public String toString() {
        return hkx.getName();
    }

    public void deserialization() {
        if (originAnno == null)
            return;

        clearList();
        String[] annotations = originAnno.split(LINE_BREAK);
        for (String anno : annotations) {
            if (isMeta(anno)) {
                metaList.add(anno);
            } else {
                String[] annoArray = anno.split(" ");
                Double timePoint = Double.parseDouble(annoArray[0]);
                String name = annoArray[1];

                if (isAmr(name)) {

                    if (annoArray.length > 3) {
                        amrList.add(new AmrAnno(timePoint, name, Float.valueOf(annoArray[2]), Float.valueOf(annoArray[3]), Float.valueOf(annoArray[4])));
                    } else {
                        amrList.add(new AmrAnno(timePoint, name, Float.valueOf(annoArray[2])));
                    }

                } else if (isScar(name)) {

                    int index = name.indexOf("{");
                    if (index == -1) {
                        scarList.add(new ScarAnno(timePoint, name));
                    } else {
                        String json = name.substring(index + 1);
                        try {
                            ScarJson scarJson = objectMapper.readValue(json, ScarJson.class);
                            scarList.add(new ScarAnno(timePoint, name, scarJson));
                        } catch (JsonProcessingException e) {
                            customList.add(anno);
                        }
                    }

                } else {
                    int index = name.indexOf(".");
                    if (index == -1) {
                        AnnoType type = getStandardAnnoType(name, null);
                        switch (type) {
                            case COMMON -> commonList.add(new StandardAnno(timePoint, name, type));
                            case MCO -> mcoList.add(new StandardAnno(timePoint, name, type));
                            case PRECISION -> precsisonList.add(new StandardAnno(timePoint, name, type));
                            default -> customList.add(anno);
                        }
                    } else {
                        String payload = name.substring(index + 1);
                        name = name.substring(0, index);
                        AnnoType type = getStandardAnnoType(name, payload);
                        switch (type) {
                            case COMMON -> commonList.add(new StandardAnno(timePoint, name, payload, type));
                            case MCO -> mcoList.add(new StandardAnno(timePoint, name, payload, type));
                            case PRECISION -> precsisonList.add(new StandardAnno(timePoint, name, payload, type));
                            default -> customList.add(anno);
                        }
                    }
                }
            }
        }
    }

    public String serialization() {
        StringBuilder sb = new StringBuilder();

        for (String meta : metaList)
            sb.append(meta).append(LINE_BREAK);

        for (StandardAnno standardAnno : commonList)
            sb.append(standardAnno).append(LINE_BREAK);

        for (StandardAnno standardAnno : mcoList)
            sb.append(standardAnno).append(LINE_BREAK);

        for (StandardAnno standardAnno : precsisonList)
            sb.append(standardAnno).append(LINE_BREAK);

        for (AmrAnno amrAnno : amrList)
            sb.append(amrAnno).append(LINE_BREAK);

        for (ScarAnno scarAnno : scarList)
            sb.append(scarAnno).append(LINE_BREAK);

        for (String customAnno : customList)
            sb.append(customAnno).append(LINE_BREAK);

        return sb.toString();
    }

    private AnnoType getStandardAnnoType(String annoName, String payload) {
        AnnoProperty annoProperty = new AnnoProperty(annoName);
        if (payload == null) {
            if (annoProperties.getAnnotations(MCO).contains(annoProperty))
                return MCO;
        } else {
            for (AnnoProperty p : annoProperties.getPayloads(MCO)) {
                if (payload.contains(p.getName()))
                    return MCO;
            }
        }
        if (annoProperties.getAnnotations(COMMON).contains(annoProperty))
            return COMMON;
        if (annoProperties.getAnnotations(PRECISION).contains(annoProperty))
            return PRECISION;
        return null;
    }

    private boolean isMeta(String anno) {
        return anno.indexOf("#") == 0;
    }

    private boolean isAmr(String annoName) {
        AnnoProperty annoProperty = new AnnoProperty(annoName);
        return annoProperties.getAnnotations(AMR).contains(annoProperty);
    }

    private boolean isScar(String annoName) {
        for (AnnoProperty scarAnno : annoProperties.getAnnotations(SCAR)) {
            if (annoName.contains(scarAnno.getName()))
                return true;
        }
        return false;
    }

    private void clearList() {
        metaList.clear();
        commonList.clear();
        mcoList.clear();
        precsisonList.clear();
        amrList.clear();
        scarList.clear();
        customList.clear();
    }
}
