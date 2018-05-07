/*******************************************************************************
 * Copyright 2017 General Electric Company
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *******************************************************************************/

package com.ge.predix.acs.commons.policy.condition.groovy;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ConcurrentReferenceHashMap;

import com.ge.predix.acs.commons.policy.condition.ConditionScript;

@Component
public class GroovyConditionCache {
    @Value("${ENABLE_CONDITION_CACHING:true}")
    private boolean cacheEnabled;

    private final Map<String, ConditionScript> cache = new ConcurrentReferenceHashMap<>();

    public ConditionScript get(final String script) {
        return cacheEnabled ? this.cache.get(script) : null;
    }

    public void put(final String script, final ConditionScript compiledScript) {
        if (cacheEnabled) {
            this.cache.put(script, compiledScript);
        }
    }

    public void remove(final String script) {
        if (cacheEnabled) {
            this.cache.remove(script);
        }
    }

    public void setCacheEnabled(final boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }
}
