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
import org.eclipse.che.api.vfs.server.observation.VirtualFileEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class SampleFileEventsListener {

    private static final Logger                     LOG = LoggerFactory.getLogger(SampleFileEventsListener.class);

    private final EventService                      eventService;
    private final EventSubscriber<VirtualFileEvent> vfsSubscriber;

    @Inject
    public SampleFileEventsListener(EventService eventService) {
        this.eventService = eventService;

        vfsSubscriber = new EventSubscriber<VirtualFileEvent>() {
            @Override
            public void onEvent(VirtualFileEvent event) {
                final String workspace = event.getWorkspaceId();
                final String path = event.getPath();

                switch (event.getType()) {
                    case CONTENT_UPDATED:
                    case CREATED:
                    case DELETED:
                    case MOVED:
                    case RENAMED:
                    default:
                        try {
                            LOG.info("Handle event for path " + path + " in workspace " + workspace);
                        } catch (Exception e) {
                            LOG.error(e.getMessage(), e);
                        }
                        break;
                }
            }
        };
    }

    @PostConstruct
    void start() {
        eventService.subscribe(vfsSubscriber);
    }

    @PreDestroy
    void stop() {
        eventService.unsubscribe(vfsSubscriber);
    }
}
