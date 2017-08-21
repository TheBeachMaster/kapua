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
package org.eclipse.kapua.commons.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.kapua.commons.configuration.metatype.Password;
import org.junit.Test;

public class StringUtilTest {

    @Test
    public void shouldConvertPasswordsArrayToString() {
        // Given
        Password[] passwords = new Password[] {new Password("foo") };

        // When
        String passwordsString = StringUtil.valueToString(passwords);

        // Then
        assertThat(passwordsString).isEqualTo("foo");
    }

}
