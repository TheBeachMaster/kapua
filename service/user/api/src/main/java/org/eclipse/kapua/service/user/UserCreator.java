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
package org.eclipse.kapua.service.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.kapua.model.KapuaNamedEntityCreator;
import org.eclipse.kapua.model.xml.DateXmlAdapter;

import java.util.Date;

/**
 * UserCreator encapsulates all the information needed to create a new User in the system.
 *
 * @since 1.0
 */
@XmlRootElement(name = "userCreator")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "displayName",
        "email",
        "phoneNumber",
        "userType",
        "externalId",
        "expirationDate",
        "userStatus"},
        factoryClass = UserXmlRegistry.class,
        factoryMethod = "newUserCreator")
public interface UserCreator extends KapuaNamedEntityCreator<User> {

    /**
     * Return the display name (may be a friendly username to show in the UI)
     *
     * @return
     */
    @XmlElement(name = "displayName")
    public String getDisplayName();

    /**
     * Set the display name
     *
     * @param displayName
     */
    public void setDisplayName(String displayName);

    /**
     * Get the email
     *
     * @return
     */
    @XmlElement(name = "email")
    public String getEmail();

    /**
     * Set the email
     *
     * @param email
     */
    public void setEmail(String email);

    /**
     * Get the phone number
     *
     * @return
     */
    @XmlElement(name = "phoneNumber")
    public String getPhoneNumber();

    /**
     * Set the phone number
     *
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber);

    /**
     * Get the user type
     *
     * @return
     */
    @XmlElement(name = "userType")
    public UserType getUserType();

    /**
     * Set the user type
     *
     * @param userType
     */
    public void setUserType(UserType userType);

    /**
     * Get the external ID
     *
     * @return
     */
    @XmlElement(name = "externalId")
    public String getExternalId();

    /**
     * Set the external ID
     *
     * @param externalId
     */
    public void setExternalId(String externalId);

    @XmlElement(name = "expirationDate")
    @XmlJavaTypeAdapter(DateXmlAdapter.class)
    Date getExpirationDate();

    void setExpirationDate(Date expirationDate);

    @XmlElement(name="userStatus")
    UserStatus getUserStatus();

    void setUserStatus(UserStatus userStatus);
}
