/*
 * Copyright (c)  2022 Daniel Fiala
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

package com.server.mapping;

import com.server.request.Request;
import com.server.response.Response;

import java.util.ArrayList;
import java.util.List;

public abstract class Mapping<R> {

    private List<String> requiredHeaders = new ArrayList<>();
    private List<String> requiredParameters = new ArrayList<>();

    public abstract Response<R> handle(Request request);

    public Mapping<R> setRequiredHeaders(List<String> requiredHeaders) {
        this.requiredHeaders = requiredHeaders;
        return this;
    }

    public Mapping<R> addRequiredHeader(String requiredHeader) {
        this.requiredHeaders.add(requiredHeader);
        return this;
    }

    public Mapping<R> removeRequiredHeader(String requiredHeader) {
        this.requiredHeaders.remove(requiredHeader);
        return this;
    }

    public Mapping<R> setRequiredParameters(List<String> requiredParameters) {
        this.requiredParameters = requiredParameters;
        return this;
    }

    public Mapping<R> addRequiredParameter(String requiredParameter) {
        this.requiredParameters.add(requiredParameter);
        return this;
    }

    public Mapping<R> removeRequiredParameter(String requiredParameter) {
        this.requiredParameters.remove(requiredParameter);
        return this;
    }

    public List<String> getRequiredHeaders() {
        return requiredHeaders;
    }

    public List<String> getRequiredParameters() {
        return requiredParameters;
    }
}

