package com.xy.hkxannoeditor.core.editManager;

import com.xy.hkxannoeditor.core.AnnoManager;
import com.xy.hkxannoeditor.core.Editor;

import java.io.File;

public class EditManager extends AnnoManager implements Editor {
    public EditManager(File root) {
        super(root);
    }
}
