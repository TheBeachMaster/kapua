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
package org.eclipse.kapua.service.authorization.domain.shiro;

import org.eclipse.kapua.commons.model.query.predicate.AbstractKapuaQuery;
import org.eclipse.kapua.model.id.KapuaId;
import org.eclipse.kapua.service.authorization.domain.Domain;
import org.eclipse.kapua.service.authorization.domain.DomainQuery;

/**
 * {@link DomainQuery} implementation.
 * 
 * @since 1.0.0
 */
public class DomainQueryImpl extends AbstractKapuaQuery<Domain> implements DomainQuery {

    /**
     * Constructor
     */
    public DomainQueryImpl() {
        super();
    }

    public DomainQueryImpl(KapuaId scopeId) {
        super(scopeId);
    }
}
