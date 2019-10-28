/*
 * Copyright 2019 the original author or authors.
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

package org.gradle.internal.instantiation;

import org.gradle.api.reflect.ObjectInstantiationException;
import org.gradle.internal.state.Managed;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ManagedTypeFactory implements Managed.Factory {
    private final Constructor<?> constructor;

    // Used by generated code
    public ManagedTypeFactory(Class<?> type) throws NoSuchMethodException {
        constructor = type.getConstructor(Object[].class);
    }

    @Override
    public <T> T fromState(Class<T> type, Object state) {
        if (!type.isAssignableFrom(constructor.getDeclaringClass())) {
            return null;
        }
        try {
            return type.cast(constructor.newInstance(state));
        } catch (InvocationTargetException e) {
            throw new ObjectInstantiationException(type, e.getCause());
        } catch (Exception e) {
            throw new ObjectInstantiationException(type, e);
        }
    }
}
