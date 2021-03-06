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
package org.eclipse.kapua.app.console.setting;

import org.eclipse.kapua.commons.setting.AbstractKapuaSetting;

public class ConsoleSetting extends AbstractKapuaSetting<ConsoleSettingKeys> {

    private static final String CONSOLE_SETTING_RESOURCE = "console-setting.properties";

    private static final ConsoleSetting INSTANCE = new ConsoleSetting();

    private ConsoleSetting() {
        super(CONSOLE_SETTING_RESOURCE);
    }

    public static ConsoleSetting getInstance() {
        return INSTANCE;
    }
}
