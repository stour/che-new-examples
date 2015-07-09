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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.che.api.core.notification.EventService;
import org.eclipse.che.api.core.notification.EventSubscriber;
import org.eclipse.che.api.project.server.ProjectCreatedEvent;
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
                final String workspace = event.getWorkspaceId();
                final String projectPath = event.getProjectPath();
                LOG.info("Handle event for project at path " + projectPath + " in workspace " + workspace);
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
