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
package org.eclipse.che.tutorial.addrestapi.server.inject;

import org.eclipse.che.inject.DynaModule;
import org.eclipse.che.tutorial.addrestapi.server.SampleService;

import com.google.inject.AbstractModule;

@DynaModule
public class SampleRestApiModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SampleService.class);
    }
}
