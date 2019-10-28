/*
 * Copyright 2016 the original author or authors.
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
package org.gradle.api.plugins;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.compile.JavaCompile;

import javax.inject.Inject;

import static org.gradle.api.plugins.JavaPlugin.COMPILE_JAVA_TASK_NAME;
import static org.gradle.api.plugins.internal.JavaPluginsHelper.registerClassesDirVariant;

/**
 * <p>A {@link Plugin} which extends the capabilities of the {@link JavaPlugin Java plugin} by cleanly separating
 * the API and implementation dependencies of a library.</p>
 *
 * @since 3.4
 */
public class JavaLibraryPlugin implements Plugin<Project> {
    private final ObjectFactory objectFactory;

    @Inject
    public JavaLibraryPlugin(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public void apply(Project project) {
        project.getPluginManager().apply(JavaPlugin.class);

        SourceSetContainer sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
        ConfigurationContainer configurations = project.getConfigurations();
        addApiToMainSourceSet(project, sourceSets, configurations);
    }

    private void addApiToMainSourceSet(Project project, SourceSetContainer sourceSets, ConfigurationContainer configurations) {
        SourceSet sourceSet = sourceSets.getByName("main");

        Configuration apiConfiguration = configurations.maybeCreate(sourceSet.getApiConfigurationName());
        apiConfiguration.setVisible(false);
        apiConfiguration.setDescription("API dependencies for " + sourceSet + ".");
        apiConfiguration.setCanBeResolved(false);
        apiConfiguration.setCanBeConsumed(false);

        Configuration apiElementsConfiguration = configurations.getByName(sourceSet.getApiElementsConfigurationName());
        apiElementsConfiguration.extendsFrom(apiConfiguration);

        final Provider<JavaCompile> javaCompile = project.getTasks().named(COMPILE_JAVA_TASK_NAME, JavaCompile.class);
        registerClassesDirVariant(javaCompile, objectFactory, apiElementsConfiguration);

        Configuration implementationConfiguration = configurations.getByName(sourceSet.getImplementationConfigurationName());
        implementationConfiguration.extendsFrom(apiConfiguration);

        Configuration compileConfiguration = configurations.getByName(sourceSet.getCompileConfigurationName());
        apiConfiguration.extendsFrom(compileConfiguration);
    }

}
