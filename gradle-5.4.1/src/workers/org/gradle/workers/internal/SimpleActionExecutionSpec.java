/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.workers.internal;

public class SimpleActionExecutionSpec implements ActionExecutionSpec {
    private final Class<?> implementationClass;
    private final String displayName;
    private final Object[] params;

    public SimpleActionExecutionSpec(Class<?> implementationClass, String displayName, Object[] params) {
        this.implementationClass = implementationClass;
        this.displayName = displayName;
        this.params = params;
    }

    @Override
    public Class<?> getImplementationClass() {
        return implementationClass;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public Object[] getParams(ClassLoader classLoader) {
        return params;
    }
}
