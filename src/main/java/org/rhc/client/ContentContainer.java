package org.rhc.client;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:05 AM
 * To change this template use File | Settings | File Templates.
 */

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;

public enum ContentContainer {

    INSTANCE;

    /**
     * Changes the content's of the "content" panel.
     *
     * @param content  Composite object to assign to the panel.
     */
    public void setContent(Composite content) {
        RootPanel.get("content").clear();
        RootPanel.get("content").add(content);
    }
}

