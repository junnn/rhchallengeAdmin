package org.rhc.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 5/8/13
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class InfoScreen extends Composite {
    interface InfoScreenUiBinder extends UiBinder<Widget, InfoScreen> {}

    private static InfoScreenUiBinder UiBinder = GWT.create(InfoScreenUiBinder.class);

    @UiField Hyperlink addUserLink;
    @UiField Hyperlink searchUserLink;

    public InfoScreen(){
        initWidget(UiBinder.createAndBindUi(this));
    }


    @UiHandler("addUserLink")
    public void handleaddUserLinkClick(ClickEvent event){
        ContentContainer.INSTANCE.setContent(new AdminScreen());
    }
    @UiHandler("searchUserLink")
    public void handlesearchUserLinkClick(ClickEvent event){
        ContentContainer.INSTANCE.setContent(new SearchScreen());
    }
}
