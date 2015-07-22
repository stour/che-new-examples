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
package org.eclipse.che.examples.projectwizard.client;

import org.eclipse.che.api.project.shared.dto.ImportProject;
import org.eclipse.che.examples.projectwizard.shared.ProjectAttributes;
import org.eclipse.che.ide.api.project.type.wizard.ProjectWizardRegistrar;
import org.eclipse.che.ide.api.wizard.WizardPage;
import org.eclipse.che.ide.collections.Array;
import org.eclipse.che.ide.collections.Collections;

import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;

/**
 * Provides information for registering sample project type into project wizard.
 *
 */
public class SampleProjectWizardRegistrar implements ProjectWizardRegistrar {
    private final Array<Provider<? extends WizardPage<ImportProject>>> wizardPages;

    @Inject
    public SampleProjectWizardRegistrar() {
        wizardPages = Collections.createArray();
    }

    @Nonnull
    public String getProjectTypeId() {
        return ProjectAttributes.SAMPLE_PROJECT_TYPE_ID;
    }

    @Nonnull
    public String getCategory() {
        return ProjectAttributes.SAMPLE_PROJECT_TYPE_CATEGORY;
    }

    @Nonnull
    public Array<Provider<? extends WizardPage<ImportProject>>> getWizardPages() {
        return wizardPages;
    }
}
