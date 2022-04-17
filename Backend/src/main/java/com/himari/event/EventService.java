/*
 * Copyright (c)  2021 Daniel Fiala
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.himari.event;

import com.himari.service.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class EventService extends Service {

    private static EventService singleton;

    private final Logger logger = Logger.getLogger("Event-Service");
    private final EventContainer container = new EventContainer();

    public void handleEvent(Event event) {
        if (!container.isEvent(event.getClass())) {
            logger.log(Level.WARNING, "Event can not be handle because there are no registered handler's.");
        }

        List<EventHandler> eventHandlers = container.getHandlers(event.getClass());
        if (event.isAsynchronous()) {

        }

        eventHandlers.forEach(handler -> handler.handle(event));
    }

    public void registerHandler(Class<? extends Event> event, EventHandler handler) {
        container.registerEvent(event, handler);
    }

    public void unregisterHandlers(Class<? extends Event> event) {
        container.unregisterEvent(event);
    }

    public EventContainer getContainer() {
        return container;
    }

    public static EventService getInstance() {
        if (singleton == null) {
            singleton = new EventService();
        }

        return singleton;
    }
}
