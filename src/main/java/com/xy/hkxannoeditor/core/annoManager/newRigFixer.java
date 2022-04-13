package com.xy.hkxannoeditor.core.annoManager;

import com.xy.hkxannoeditor.core.ListExecutor.AnnoInserterRemover;
import com.xy.hkxannoeditor.entity.bo.FilePath;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import static com.xy.hkxannoeditor.Const.ATTACK_WIN_ANNO;
import static com.xy.hkxannoeditor.Const.COMBO_ANNO;

public class newRigFixer extends FixManager {
    public newRigFixer(File root, List<FilePath> _filePaths) {
        super(root, _filePaths);
    }

    @Override
    public void fixAnno() throws IOException {
        ListIterator<FilePath> li = getFilePaths().listIterator();
        while (li.hasNext()) {
            File txt = li.next().txt;
            AnnoInserterRemover fixer = new AnnoInserterRemover(txt, ATTACK_WIN_ANNO, COMBO_ANNO);
            if (fixer.removeRep()) {
                System.out.println("successfully fixed:" + txt.getName());
            } else {
                li.remove();
                System.out.println("failed to fix:" + txt.getName());
            }
        }
    }
}
