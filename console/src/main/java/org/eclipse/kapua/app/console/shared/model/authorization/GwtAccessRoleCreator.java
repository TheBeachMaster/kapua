/*******************************************************************************
 * Copyright (c) 2011, 2016 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua.app.console.shared.model.authorization;

import org.eclipse.kapua.app.console.shared.model.GwtEntityCreator;

public class GwtAccessRoleCreator extends GwtEntityCreator {

    private static final long serialVersionUID = -1333808048669893906L;

    private String accessInfoId;

    private String roleId;

    public GwtAccessRoleCreator() {
        super();
    }

    public String getAccessInfoId() {
        return accessInfoId;
    }

    public void setAccessInfoId(String name) {
        this.accessInfoId = name;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}