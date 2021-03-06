/*******************************************************************************
 * Copyright (c) 2017 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua.app.console.client.tag;

import org.eclipse.kapua.app.console.client.messages.ConsoleTagMessages;
import org.eclipse.kapua.app.console.client.ui.grid.EntityGrid;
import org.eclipse.kapua.app.console.client.ui.panel.EntityFilterPanel;
import org.eclipse.kapua.app.console.client.ui.view.EntityView;
import org.eclipse.kapua.app.console.shared.model.GwtSession;
import org.eclipse.kapua.app.console.shared.model.GwtTag;

import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;

public class TagFilterPanel extends EntityFilterPanel<GwtTag> {

    private static final int WIDTH = 200;
    private final EntityGrid<GwtTag> entityGrid;
    private final GwtSession currentSession;
    private final TextField<String> nameField;
    private static final ConsoleTagMessages MSGS = GWT.create(ConsoleTagMessages.class);

    public TagFilterPanel(EntityView<GwtTag> entityView, GwtSession currentSession) {
        super(entityView, currentSession);
        entityGrid = entityView.getEntityGrid(entityView, currentSession);
        this.currentSession = currentSession;

        setHeading(MSGS.filterHeader());

        VerticalPanel verticalPanel = getFieldsPanel();
        final Label nameLabel = new Label(MSGS.filterFieldTagNameLabel());
        nameLabel.setWidth(WIDTH);
        nameLabel.setStyleAttribute("margin", "5px");
        verticalPanel.add(nameLabel);
        nameField = new TextField<String>();
        nameField.setName("name");
        nameField.setWidth(WIDTH);
        nameField.setStyleAttribute("margin-top", "0px");
        nameField.setStyleAttribute("margin-left", "5px");
        nameField.setStyleAttribute("margin-right", "5px");
        nameField.setStyleAttribute("margin-bottom", "10px");
        verticalPanel.add(nameField);

    }

    @Override
    public void resetFields() {
        nameField.setValue(null);
        GwtTagQuery query = new GwtTagQuery();
        query.setScopeId(currentSession.getSelectedAccount().getId());
        entityGrid.refresh(query);

    }

    @Override
    public void doFilter() {
        GwtTagQuery query = new GwtTagQuery();
        query.setName(nameField.getValue());
        query.setScopeId(currentSession.getSelectedAccount().getId());
        entityGrid.refresh(query);

    }

}
