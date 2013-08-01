package org.rhc.client;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:07 AM
 * To change this template use File | Settings | File Templates.
 */

import com.google.gwt.core.client.EntryPoint;

public class EntryPage implements EntryPoint {

    @Override
    public void onModuleLoad(){

        ContentPage.INSTANCE.setContent(new SearchScreen());
        //ContentPage.INSTANCE.setContent(new AdminScreen());


    }
}