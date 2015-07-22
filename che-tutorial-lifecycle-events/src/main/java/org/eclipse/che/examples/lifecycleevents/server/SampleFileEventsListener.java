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
package org.eclipse.che.examples.lifecycleevents.server;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.websocket.EncodeException;

import org.eclipse.che.api.core.notification.EventService;
import org.eclipse.che.api.core.notification.EventSubscriber;
import org.eclipse.che.api.vfs.server.observation.VirtualFileEvent;
import org.everrest.websockets.WSConnectionContext;
import org.everrest.websockets.message.ChannelBroadcastMessage;
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
                String eventType = "";

                switch (event.getType()) {
                    case CONTENT_UPDATED:
                        eventType = "content updated";
                        break;
                    case CREATED:
                        eventType = "created";
                        break;
                    case DELETED:
                        eventType = "deleted";
                        break;
                    case MOVED:
                        eventType = "moved";
                        break;
                    case RENAMED:
                        eventType = "renamed";
                        break;
                    default:
                        eventType = "default";
                        break;
                }

                try {
                    final ChannelBroadcastMessage bm = new ChannelBroadcastMessage();
                    String projectPath = path.split("/")[0];
                    bm.setChannel(String.format("file:status:%d", workspace + "/" + projectPath));
                    bm.setBody(String.format("{\"message\":%s}", "Operation >"  + eventType + "< on file " + workspace + path + " successful"));
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
        eventService.subscribe(vfsSubscriber);
    }

    @PreDestroy
    void stop() {
        eventService.unsubscribe(vfsSubscriber);
    }
}
