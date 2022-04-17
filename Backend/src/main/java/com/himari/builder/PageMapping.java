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

package com.himari.builder;

import com.himari.builder.page.Page;
import com.himari.builder.page.PageFactory;
import com.himari.mapping.Mapping;
import com.himari.mapping.MappingService;
import com.himari.response.Response;
import org.jetbrains.annotations.NotNull;

public class PageMapping {

    private Page page;
    private String route;

    private Mapping<String> html;
    private Mapping<String> stylesheet;

    public PageMapping(@NotNull Page page, String route) {
        this.page = page;
        this.route = route;

        remap(page);
    }

    private void remap(Page page) {
        PageFactory factory = new PageFactory(page);

        this.html = () -> new Response<>(factory.createHtml());
        this.stylesheet = () -> new Response<>(factory.createStyleSheet());

        MappingService.getService().registerMapping(route, this.html);
        MappingService.getService().registerMapping(route + "/style.css", this.stylesheet);
    }

    public @NotNull Page getPage() {
        return page;
    }

    public PageMapping setPage(@NotNull Page page) {
        this.page = page;
        remap(page);

        return this;
    }

    public String getRoute() {
        return route;
    }

    public Mapping<String> getHtml() {
        return html;
    }

    public Mapping<String> getStylesheet() {
        return stylesheet;
    }
}
