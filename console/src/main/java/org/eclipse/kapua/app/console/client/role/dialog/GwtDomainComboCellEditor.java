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
package org.eclipse.kapua.app.console.client.role.dialog;

import org.eclipse.kapua.app.console.client.ui.widget.ComboEnumCellEditor;
import org.eclipse.kapua.app.console.shared.model.GwtPermission.GwtDomain;

import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;

public class GwtDomainComboCellEditor extends ComboEnumCellEditor<GwtDomain> {

    public GwtDomainComboCellEditor(SimpleComboBox<GwtDomain> field) {
        super(field);
    }

    @Override
    protected GwtDomain convertStringValue(String value) {
        return GwtDomain.valueOf(value);
    }

}
