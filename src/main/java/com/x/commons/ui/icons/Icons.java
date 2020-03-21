package com.x.commons.ui.icons;

import javax.swing.*;

public final class Icons {

    private Icons(){}

    public static ImageIcon getAlarmIcon() {
        return new ImageIcon(Icons.class.getResource("/com/x/commons/ui/res/alarm.png"));
    }

    public static ImageIcon getErrorIcon() {
        return new ImageIcon(Icons.class.getResource("/com/x/commons/ui/icons/res/error.png"));
    }

    public static ImageIcon getInfoIcon() {
        return new ImageIcon(Icons.class.getResource("/com/x/commons/ui/icons/res/info.png"));
    }

    public static ImageIcon getOkIcon() {
        return new ImageIcon(Icons.class.getResource("/com/x/commons/ui/icons/res/ok.png"));
    }

    public static ImageIcon getQuestionIcon() {
        return new ImageIcon(Icons.class.getResource("/com/x/commons/ui/icons/res/question.png"));
    }
}
