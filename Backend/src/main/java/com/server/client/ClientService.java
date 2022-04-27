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

package com.server.client;

import com.server.Server;
import com.server.service.Service;

public final class ClientService extends Service {

    public static ClientService singleton;

    private final Server server;

    public ClientService(Server server) {
        singleton = this;

        this.server = server;
    }

    public static ClientService getInstance() {
        return singleton;
    }

    public void handleIncomingClient() {

    }

    public Server getServer() {
        return server;
    }
}
