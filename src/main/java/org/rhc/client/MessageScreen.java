package org.rhc.client;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:08 AM
 * To change this template use File | Settings | File Templates.
 */

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class MessageScreen extends Composite {
    interface MessageScreenUiBinder extends UiBinder<Widget, MessageScreen> {
    }

    private static MessageScreenUiBinder UiBinder = GWT.create(MessageScreenUiBinder.class);

    @UiField Label messageLabel;
    @UiField Hyperlink indexLink;

    public MessageScreen(String message) {
        initWidget(UiBinder.createAndBindUi(this));
        messageLabel.getElement().getStyle().setFontSize(4, Style.Unit.EM);
        messageLabel.setText(message);
    }

    @UiHandler("indexLink")
    public void handleIndexLinkClick(ClickEvent event) {
        ContentContainer.INSTANCE.setContent(new IndexScreen());
    }
}