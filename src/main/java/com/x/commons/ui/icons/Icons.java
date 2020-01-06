package com.x.commons.ui.icons;

import com.ax.commons.ui.icons.IconManager;

import javax.swing.*;

public final class Icons {

    private Icons(){}

    public static ImageIcon getAlarmIcon() {
        return new ImageIcon(Icons.class.getResource("/com/x/commons/ui/icons/res/alarm.png"));
    }

    public static ImageIcon getErrorIcon() {
        return new ImageIcon(Icons.class.getResource("/com/x/commons/ui/icons/src/error.png"));
    }

    public static ImageIcon getInfoIcon() {
        return new ImageIcon(Icons.class.getResource("/com/x/commons/ui/icons/src/info.png"));
    }

    public static ImageIcon getOkIcon() {
        return new ImageIcon(Icons.class.getResource("/com/x/commons/ui/icons/src/ok.png"));
    }

    public static ImageIcon getQuestionIcon() {
        return new ImageIcon(Icons.class.getResource("/com/x/commons/ui/icons/src/question.png"));
    }
}
