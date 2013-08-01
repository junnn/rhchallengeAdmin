package org.rhc.client;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:07 AM
 * To change this template use File | Settings | File Templates.
 */

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class IndexScreen extends Composite {
    interface IndexScreenUiBinder extends UiBinder<Widget, IndexScreen> {

    }

    private static IndexScreenUiBinder UiBinder = GWT.create(IndexScreenUiBinder.class);

    @UiField Hyperlink createUserLink;

    public IndexScreen() {
        initWidget(UiBinder.createAndBindUi(this));
    }

    @UiHandler("createUserLink")
    public void handleLoginLinkClick(ClickEvent event) {
        ContentPage.INSTANCE.setContent(new AdminScreen());
    }

}
