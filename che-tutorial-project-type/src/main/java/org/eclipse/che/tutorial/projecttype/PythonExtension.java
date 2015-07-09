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
package org.eclipse.che.tutorial.projecttype;

import org.eclipse.che.ide.api.extension.Extension;
import org.eclipse.che.ide.api.icon.Icon;
import org.eclipse.che.ide.api.icon.IconRegistry;
import org.eclipse.che.tutorial.projecttype.shared.ProjectAttributes;
import org.vectomatic.dom.svg.ui.SVGResource;

import com.google.gwt.resources.client.ClientBundle;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
@Extension(title = "Python", version = "3.0.0")
public class PythonExtension {
    @Inject
    public PythonExtension(ParserResource parserResource, IconRegistry iconRegistry) {
        iconRegistry.registerIcon(new Icon(ProjectAttributes.PYTHON_CATEGORY + ".samples.category.icon",
                                           parserResource.pythonCategoryIcon()));
    }
 
    public interface ParserResource extends ClientBundle {
        @Source("com/codenvy/ide/ext/python/client/image/python.svg")
        SVGResource pythonCategoryIcon();
    }
}
