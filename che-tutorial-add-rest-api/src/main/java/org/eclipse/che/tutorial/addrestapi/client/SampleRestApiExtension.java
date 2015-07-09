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
package org.eclipse.che.tutorial.addrestapi.client;

import javax.inject.Singleton;

import org.eclipse.che.ide.api.action.ActionManager;
import org.eclipse.che.ide.api.action.DefaultActionGroup;
import org.eclipse.che.ide.api.action.IdeActions;
import org.eclipse.che.ide.api.constraints.Constraints;
import org.eclipse.che.ide.api.extension.Extension;
import org.eclipse.che.tutorial.addrestapi.client.action.ConsumeApiAction;

@Singleton
@Extension(title = "Sample REST API Extension", version = "1.0.0")
public class SampleRestApiExtension {
    public SampleRestApiExtension(ActionManager actionManager, ConsumeApiAction action) {
        actionManager.registerAction("consumeApiID", action);

        DefaultActionGroup contextMenu = (DefaultActionGroup)actionManager.getAction(IdeActions.GROUP_MAIN_TOOLBAR);
        contextMenu.add(action, Constraints.LAST);
    }
}
