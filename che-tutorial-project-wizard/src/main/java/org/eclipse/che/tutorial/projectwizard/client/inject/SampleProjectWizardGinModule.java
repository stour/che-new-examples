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
package org.eclipse.che.tutorial.projectwizard.client.inject;

import org.eclipse.che.ide.api.project.type.wizard.ProjectWizardRegistrar;
import org.eclipse.che.inject.DynaModule;
import org.eclipse.che.tutorial.projectwizard.client.SampleProjectWizardRegistrar;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.multibindings.GinMultibinder;

@DynaModule
public class SampleProjectWizardGinModule extends AbstractGinModule {
    /** {@inheritDoc} */
    @Override
    protected void configure() {
	    GinMultibinder.newSetBinder(binder(), ProjectWizardRegistrar.class).addBinding().to(SampleProjectWizardRegistrar.class);
    }
}
