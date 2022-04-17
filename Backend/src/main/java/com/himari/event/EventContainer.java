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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventContainer {

    private final Map<Class<? extends Event>, List<EventHandler>> container = new HashMap<>();

    public synchronized void registerEvent(Class<? extends Event> event, EventHandler handler) {
        List<EventHandler> handlers = container.putIfAbsent(event, new ArrayList<>());
        if (handlers == null) {
            throw new IllegalStateException("List of handler's shouldn't be null at this point but it is.");
        }

        handlers.add(handler);
    }

    public synchronized void unregisterEvent(Class<? extends Event> event) {
        container.remove(event);
    }

    public synchronized boolean isEvent(Class<? extends Event> event) {
        return container.containsKey(event);
    }

    public synchronized List<EventHandler> getHandlers(Class<? extends Event> event) {
        return container.get(event);
    }

    public Map<Class<? extends Event>, List<EventHandler>> getContainer() {
        return new HashMap<>(container);
    }
}
