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
package org.eclipse.che.examples.lifecycleevents.client;

import javax.inject.Singleton;

import org.eclipse.che.ide.api.app.CurrentProject;
import org.eclipse.che.ide.api.event.ProjectActionEvent;
import org.eclipse.che.ide.api.event.ProjectActionHandler;
import org.eclipse.che.ide.api.extension.Extension;
import org.eclipse.che.ide.api.notification.NotificationManager;
import org.eclipse.che.ide.util.loging.Log;
import org.eclipse.che.ide.websocket.MessageBus;
import org.eclipse.che.ide.websocket.WebSocketException;
import org.eclipse.che.ide.websocket.rest.SubscriptionHandler;

import com.google.inject.Inject;

@Singleton
@Extension(title = "Sample Lifecycle Events Extension", version = "1.0.0")
public class SampleLifecycleEventsExtension implements ProjectActionHandler {

    public static final String PROJECT_STATUS_CHANNEL = "project:status:";
    public static final String FILE_STATUS_CHANNEL = "file:status:";
    
    private final MessageBus messageBus;
    private final CurrentProject project;
    private final SubscriptionHandler<String> messageHandler;
    
    @Inject
    public SampleLifecycleEventsExtension(final MessageBus messageBus, final NotificationManager notificationManager, final CurrentProject project) {
        this.messageBus = messageBus;
        this.project = project;

        final String projectPath =  project.getRootProject().getPath();
        final String workspace =  project.getRootProject().getWorkspaceName();
        messageHandler = new SubscriptionHandler<String>() {

            @Override
            protected void onMessageReceived(String result) {
                notificationManager.showInfo(result);
            }

            @Override
            protected void onErrorReceived(Throwable exception) {
                try {
                    messageBus.unsubscribe(FILE_STATUS_CHANNEL + workspace + "/" + projectPath, this);
                    Log.error(SampleLifecycleEventsExtension.class, exception);
                } catch (WebSocketException e) {
                    Log.error(SampleLifecycleEventsExtension.class, e);
                }
            }
        };
    }

    @Override
    public void onProjectOpened(ProjectActionEvent event) {
        initEventsListeners();
    }

    @Override
    public void onProjectClosing(ProjectActionEvent event) {}

    @Override
    public void onProjectClosed(ProjectActionEvent event) {
        cleanEventsListeners();
    }

    private void initEventsListeners() {
        final String workspace =  project.getRootProject().getWorkspaceName();
        final String projectPath =  project.getRootProject().getPath();
        try {
            messageBus.subscribe(FILE_STATUS_CHANNEL + workspace + "/" + projectPath, messageHandler);
        } catch (WebSocketException e) {
            Log.error(SampleLifecycleEventsExtension.class, e);
        }
    }

    private void cleanEventsListeners() {
        final String workspace =  project.getRootProject().getWorkspaceName();
        final String projectPath =  project.getRootProject().getPath();
        try {
            messageBus.unsubscribe(FILE_STATUS_CHANNEL + workspace + "/" + projectPath, messageHandler);
        } catch (WebSocketException e) {
            Log.error(SampleLifecycleEventsExtension.class, e);
        }
    }
}
