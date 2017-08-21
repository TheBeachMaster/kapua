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

import org.eclipse.kapua.app.console.client.messages.ConsoleRoleMessages;
import org.eclipse.kapua.app.console.client.ui.dialog.entity.EntityAddEditDialog;
import org.eclipse.kapua.app.console.client.ui.panel.FormPanel;
import org.eclipse.kapua.app.console.client.util.DialogUtils;
import org.eclipse.kapua.app.console.client.util.FailureHandler;
import org.eclipse.kapua.app.console.shared.model.GwtSession;
import org.eclipse.kapua.app.console.shared.model.authorization.GwtRole;
import org.eclipse.kapua.app.console.shared.model.authorization.GwtRoleCreator;
import org.eclipse.kapua.app.console.shared.service.GwtRoleService;
import org.eclipse.kapua.app.console.shared.service.GwtRoleServiceAsync;

import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RoleAddDialog extends EntityAddEditDialog {

    private final static ConsoleRoleMessages MSGS = GWT.create(ConsoleRoleMessages.class);

    private final static GwtRoleServiceAsync GWT_ROLE_SERVICE = GWT.create(GwtRoleService.class);

    protected TextField<String> roleNameField;
    protected RolePermissionNewGridField rolePermissionsGrid;

    public RoleAddDialog(GwtSession currentSession) {
        super(currentSession);

        DialogUtils.resizeDialog(this, 400, 150);
    }

    @Override
    public void submit() {
        GwtRoleCreator gwtRoleCreator = new GwtRoleCreator();

        gwtRoleCreator.setScopeId(currentSession.getSelectedAccount().getId());
        gwtRoleCreator.setName(roleNameField.getValue());

        GWT_ROLE_SERVICE.create(xsrfToken, gwtRoleCreator, new AsyncCallback<GwtRole>() {

            @Override
            public void onSuccess(GwtRole arg0) {
                exitStatus = true;
                exitMessage = MSGS.dialogAddConfirmation();
                hide();
            }

            @Override
            public void onFailure(Throwable cause) {
                FailureHandler.handleFormException(formPanel, cause);
                status.hide();
                formPanel.getButtonBar().enable();
                unmask();
                submitButton.enable();
                cancelButton.enable();
            }
        });

    }

    @Override
    public String getHeaderMessage() {
        return MSGS.dialogAddHeader();
    }

    @Override
    public String getInfoMessage() {
        return MSGS.dialogAddInfo();
    }

    @Override
    public void createBody() {
        FormPanel roleFormPanel = new FormPanel(FORM_LABEL_WIDTH);

        //
        // Name
        roleNameField = new TextField<String>();
        roleNameField.setAllowBlank(false);
        roleNameField.setFieldLabel("* " + MSGS.dialogAddFieldName());
        roleNameField.setToolTip(MSGS.dialogAddFieldNameTooltip());
        roleFormPanel.add(roleNameField);

        bodyPanel.add(roleFormPanel);
    }

    protected RolePermissionNewGridField getRolePermissionNewGridField(GwtSession currentSession) {
        RolePermissionNewGridField rolePermissionsGrid = new RolePermissionNewGridField(currentSession);
        rolePermissionsGrid.setFieldLabel(MSGS.dialogAddFieldRolePermissions());
        rolePermissionsGrid.setToolTip(MSGS.dialogAddFieldRolePermissionsTooltip());
        return rolePermissionsGrid;
    }

}
