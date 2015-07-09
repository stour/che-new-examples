/*******************************************************************************
 * Copyright (c) 2012-2015 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.tutorial.addrestapi.client.action;

import javax.inject.Inject;

import org.eclipse.che.ide.api.action.Action;
import org.eclipse.che.ide.api.action.ActionEvent;
import org.eclipse.che.ide.rest.AsyncRequestCallback;
import org.eclipse.che.ide.rest.AsyncRequestFactory;
import org.eclipse.che.ide.rest.AsyncRequestLoader;
import org.eclipse.che.ide.rest.RestContext;
import org.eclipse.che.ide.rest.StringUnmarshaller;

import com.google.gwt.user.client.Window;

public class ConsumeApiAction extends Action {

    /** REST service context. */
    private final String              baseUrl;
    /** Loader to be displayed. */
    private final AsyncRequestLoader  loader;
    private final AsyncRequestFactory asyncRequestFactory;

    @Inject
    public ConsumeApiAction(@RestContext String baseUrl,
                            AsyncRequestLoader loader,
                            AsyncRequestFactory asyncRequestFactory) {
        this.baseUrl = baseUrl + "/sample";
        this.loader = loader;
        this.asyncRequestFactory = asyncRequestFactory;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = Window.prompt("What's your name ?", "");
        String url = baseUrl + name;
        asyncRequestFactory.createGetRequest(url).loader(loader).send(new AsyncRequestCallback<String>(new StringUnmarshaller()) {
            protected void onSuccess(String answer) {
                Window.alert(answer);
            };

            protected void onFailure(Throwable arg0) {
            };
        });
    }
}
