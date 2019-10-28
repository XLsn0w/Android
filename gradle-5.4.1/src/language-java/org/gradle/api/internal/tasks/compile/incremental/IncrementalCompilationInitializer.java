/*
 * Copyright 2013 the original author or authors.
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

package org.gradle.api.internal.tasks.compile.incremental;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.gradle.api.file.FileTree;
import org.gradle.api.internal.file.FileOperations;
import org.gradle.api.internal.tasks.compile.JavaCompileSpec;
import org.gradle.api.internal.tasks.compile.incremental.processing.GeneratedResource;
import org.gradle.api.internal.tasks.compile.incremental.recomp.RecompilationSpec;
import org.gradle.api.tasks.util.PatternSet;
import org.gradle.internal.Factory;
import org.gradle.language.base.internal.tasks.SimpleStaleClassCleaner;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class IncrementalCompilationInitializer {
    private final FileOperations fileOperations;
    private final FileTree sourceTree;

    public IncrementalCompilationInitializer(FileOperations fileOperations, FileTree sourceTree) {
        this.fileOperations = fileOperations;
        this.sourceTree = sourceTree;
    }

    public void initializeCompilation(JavaCompileSpec spec, RecompilationSpec recompilationSpec) {
        if (!recompilationSpec.isBuildNeeded()) {
            spec.setSourceFiles(ImmutableSet.<File>of());
            spec.setClasses(Collections.<String>emptySet());
            return;
        }
        Factory<PatternSet> patternSetFactory = fileOperations.getFileResolver().getPatternSetFactory();
        PatternSet classesToDelete = patternSetFactory.create();
        PatternSet sourceToCompile = patternSetFactory.create();

        prepareJavaPatterns(recompilationSpec.getClassesToCompile(), classesToDelete, sourceToCompile);
        spec.setSourceFiles(narrowDownSourcesToCompile(sourceTree, sourceToCompile));
        includePreviousCompilationOutputOnClasspath(spec);
        addClassesToProcess(spec, recompilationSpec);
        deleteStaleFilesIn(classesToDelete, spec.getDestinationDir());
        deleteStaleFilesIn(classesToDelete, spec.getCompileOptions().getAnnotationProcessorGeneratedSourcesDirectory());
        deleteStaleFilesIn(classesToDelete, spec.getCompileOptions().getHeaderOutputDirectory());

        Map<GeneratedResource.Location, PatternSet> resourcesToDelete = prepareResourcePatterns(recompilationSpec.getResourcesToGenerate(), patternSetFactory);
        deleteStaleFilesIn(resourcesToDelete.get(GeneratedResource.Location.CLASS_OUTPUT), spec.getDestinationDir());
        // If the client has not set a location for SOURCE_OUTPUT, javac outputs those files to the CLASS_OUTPUT directory, so clean that instead.
        deleteStaleFilesIn(resourcesToDelete.get(GeneratedResource.Location.SOURCE_OUTPUT), MoreObjects.firstNonNull(spec.getCompileOptions().getAnnotationProcessorGeneratedSourcesDirectory(), spec.getDestinationDir()));
        // In the same situation with NATIVE_HEADER_OUTPUT, javac just NPEs.  Don't bother.
        deleteStaleFilesIn(resourcesToDelete.get(GeneratedResource.Location.NATIVE_HEADER_OUTPUT), spec.getCompileOptions().getHeaderOutputDirectory());
    }

    private Iterable<File> narrowDownSourcesToCompile(FileTree sourceTree, PatternSet sourceToCompile) {
        return sourceTree.matching(sourceToCompile);
    }

    private void includePreviousCompilationOutputOnClasspath(JavaCompileSpec spec) {
        List<File> classpath = Lists.newArrayList(spec.getCompileClasspath());
        File destinationDir = spec.getDestinationDir();
        classpath.add(destinationDir);
        spec.setCompileClasspath(classpath);
    }

    private void addClassesToProcess(JavaCompileSpec spec, RecompilationSpec recompilationSpec) {
        Set<String> classesToProcess = Sets.newHashSet(recompilationSpec.getClassesToProcess());
        classesToProcess.removeAll(recompilationSpec.getClassesToCompile());
        spec.setClasses(classesToProcess);
    }

    private void deleteStaleFilesIn(PatternSet filesToDelete, final File destinationDir) {
        if (filesToDelete == null || filesToDelete.isEmpty() || destinationDir == null) {
            return;
        }
        Set<File> toDelete = fileOperations.fileTree(destinationDir).matching(filesToDelete).getFiles();
        SimpleStaleClassCleaner cleaner = new SimpleStaleClassCleaner(toDelete);
        cleaner.addDirToClean(destinationDir);
        cleaner.execute();
    }

    private void prepareJavaPatterns(Collection<String> staleClasses, PatternSet filesToDelete, PatternSet sourceToCompile) {
        for (String staleClass : staleClasses) {
            String path = staleClass.replaceAll("\\.", "/");
            filesToDelete.include(path.concat(".class"));
            filesToDelete.include(path.concat(".java"));
            filesToDelete.include(path.concat(".h"));
            filesToDelete.include(path.concat("$*.class"));
            filesToDelete.include(path.concat("$*.java"));
            filesToDelete.include(path.concat("$*.h"));

            sourceToCompile.include(path.concat(".java"));
            sourceToCompile.include(path.concat("$*.java"));
        }
    }

    private static Map<GeneratedResource.Location, PatternSet> prepareResourcePatterns(Collection<GeneratedResource> staleResources, Factory<PatternSet> patternSetFactory) {
        Map<GeneratedResource.Location, PatternSet> resourcesByLocation = new EnumMap<GeneratedResource.Location, PatternSet>(GeneratedResource.Location.class);
        for (GeneratedResource.Location location : GeneratedResource.Location.values()) {
            resourcesByLocation.put(location, patternSetFactory.create());
        }
        for (GeneratedResource resource : staleResources) {
            resourcesByLocation.get(resource.getLocation()).include(resource.getPath());
        }
        return resourcesByLocation;
    }
}
