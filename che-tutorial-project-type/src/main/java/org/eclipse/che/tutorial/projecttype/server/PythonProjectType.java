/*******************************************************************************
 * Copyright (c) 2012-2015 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.tutorial.projecttype.server;

import org.eclipse.che.api.project.server.type.ProjectType;
import org.eclipse.che.ide.Constants;
import org.eclipse.che.tutorial.projecttype.shared.ProjectAttributes;

import com.google.inject.Singleton;

@Singleton
public class PythonProjectType extends ProjectType {
 
    public PythonProjectType() {
        super(ProjectAttributes.PYTHON_ID, ProjectAttributes.PYTHON_NAME, true, false);
        addConstantDefinition(Constants.LANGUAGE, "language", ProjectAttributes.PROGRAMMING_LANGUAGE);
 
    }
}
