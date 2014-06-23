// Copyright 2014 Google Inc. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.devtools.build.lib.view;

import com.google.devtools.build.lib.actions.Artifact;
import com.google.devtools.build.lib.collect.nestedset.NestedSet;
import com.google.devtools.build.lib.syntax.Label;
import com.google.devtools.build.lib.syntax.SkylarkBuiltin;
import com.google.devtools.build.lib.syntax.SkylarkCallable;

/**
 * A representation of the concept "this transitive info provider builds these files".
 *
 * <p>Every transitive info collection contains at least this provider.
 */
@SkylarkBuiltin(name = "FileProvider", doc = "An interface for rules that provide files.")
public interface FileProvider extends TransitiveInfoProvider {
  /**
   * Returns the label that is associated with this piece of information.
   *
   * <p>This is usually the label of the target that provides the information.
   */
  @SkylarkCallable(doc = "")
  Label getLabel();

  /**
   * Returns the set of artifacts that are the "output" of this rule.
   *
   * <p>The term "output" is somewhat hazily defined; it is vaguely the set of files that are
   * passed on to dependent rules that list the rule in their {@code srcs} attribute and the
   * set of files that are built when a rule is mentioned on the command line. It does
   * <b>not</b> include the runfiles; that is the bailiwick of {@code FilesToRunProvider}.
   *
   * <p>Note that the above definition is somewhat imprecise; in particular, when a rule is
   * mentioned on the command line, some other files are also built
   * {@code TopLevelArtifactHelper} and dependent rules are free to filter this set of artifacts
   * e.g. based on their extension.
   *
   * <p>Also, some rules may generate artifacts that are not listed here by way of defining other
   * implicit targets, for example, deploy jars.
   */
  @SkylarkCallable(doc = "")
  NestedSet<Artifact> getFilesToBuild();
}