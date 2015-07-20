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
package org.eclipse.che.tutorial.lifecycleevents.server;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.websocket.EncodeException;

import org.eclipse.che.api.core.notification.EventService;
import org.eclipse.che.api.core.notification.EventSubscriber;
import org.eclipse.che.api.project.server.ProjectCreatedEvent;
import org.everrest.websockets.WSConnectionContext;
import org.everrest.websockets.message.ChannelBroadcastMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class SampleProjectEventsListener {
    private static final Logger                        LOG = LoggerFactory.getLogger(SampleProjectEventsListener.class);

    private final EventService                         eventService;
    private final EventSubscriber<ProjectCreatedEvent> projectCreatedEventSubscriber;

    @Inject
    public SampleProjectEventsListener(EventService eventService) {
        this.eventService = eventService;

        projectCreatedEventSubscriber = new EventSubscriber<ProjectCreatedEvent>() {
            @Override
            public void onEvent(ProjectCreatedEvent event) {
                    try {
                        final ChannelBroadcastMessage bm = new ChannelBroadcastMessage();
                        final String workspace = event.getWorkspaceId();
                        final String projectPath = event.getProjectPath();
                        bm.setChannel(String.format("project:status:%d", workspace + "/" + projectPath));
                        bm.setBody(String.format("{\"message\":%s}", "Project " + workspace + projectPath + "successfully created"));
                        WSConnectionContext.sendMessage(bm);
                    } catch (EncodeException e) {
                        LOG.warn(e.getMessage(), e);
                    } catch (IOException e) {
                        LOG.warn(e.getMessage(), e);
                    }
            }
        };
    }

    @PostConstruct
    void start() {
        eventService.subscribe(projectCreatedEventSubscriber);
    }

    @PreDestroy
    void stop() {
        eventService.unsubscribe(projectCreatedEventSubscriber);
    }
}
